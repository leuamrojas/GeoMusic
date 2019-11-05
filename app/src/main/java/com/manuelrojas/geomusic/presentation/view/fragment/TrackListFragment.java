package com.manuelrojas.geomusic.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.manuelrojas.geomusic.R;
import com.manuelrojas.geomusic.data.utils.SharedPrefsUtil;
import com.manuelrojas.geomusic.presentation.di.component.TrackComponent;
import com.manuelrojas.geomusic.presentation.model.TrackModel;
import com.manuelrojas.geomusic.presentation.presenter.TrackListPresenter;
import com.manuelrojas.geomusic.presentation.view.TrackListView;
import com.manuelrojas.geomusic.presentation.view.adapter.PaginationListener;
import com.manuelrojas.geomusic.presentation.view.adapter.TrackListAdapter;
import com.manuelrojas.geomusic.data.utils.NetworkUtil;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TrackListFragment extends BaseFragment implements TrackListView,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "TrackListFragment";

    /**
     * Interface for listening track list events.
     */
    public interface TrackListListener {
        void onTrackClicked(final TrackModel trackModel);
        void onSearchViewClicked();
    }

    @Inject
    TrackListPresenter trackListPresenter;

    @Inject
    TrackListAdapter trackListAdapter;

    @BindView(R.id.rv_tracks)
    RecyclerView rvTracks;

    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;

    @BindView(R.id.rl_retry)
    RelativeLayout rlRetry;

    @BindView(R.id.bt_retry)
    Button btRetry;

    @Inject
    NetworkUtil networkUtil;

    private SharedPrefsUtil sharedPrefsUtil;

    private boolean searchViewOpen = false;
    private static final String PREFS_PAGE_TRACK = "prefsPageTrack";

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private TrackListListener trackListListener;
    private Unbinder unbinder;
    private boolean refreshing = false;

    private LinearLayoutManager layoutManager;

    // Index from which pagination should start (0 is 1st page in our case)
    private static final int PAGE_START = 1;

    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private boolean isLoading = false;

    // If current page is the last page (Pagination will stop after this page load)
    private boolean isLastPage = false;

    // total no. of pages to load. Initial load is page 0, after which 2 more pages will load.
    private int TOTAL_PAGES = 10000;

    // indicates the current page which Pagination is fetching.
    private int pageTrack = PAGE_START;

    public TrackListFragment() {
        setRetainInstance(true);
    }

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof TrackListListener) {
            this.trackListListener = (TrackListListener) activity;
        }
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(TrackComponent.class).inject(this);
        sharedPrefsUtil = new SharedPrefsUtil(getContext());
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_track_list, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.trackListPresenter.attachView(this);
        if (savedInstanceState == null) {
            pageTrack = sharedPrefsUtil.get(PREFS_PAGE_TRACK, 1);
            loadTrackList();
        } else {
            pageTrack = savedInstanceState.getInt(PREFS_PAGE_TRACK);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            filterAdapterView("");
            setSearchViewOpen(false);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PREFS_PAGE_TRACK, pageTrack);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        rvTracks.setAdapter(null);
        if (unbinder != null)
            unbinder.unbind();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        this.trackListPresenter.detachView();
    }

    @Override public void onDetach() {
        super.onDetach();
        this.trackListListener = null;
    }

    @Override public void showLoading() {
        if ( !refreshing ) {
            this.rlProgress.setVisibility(View.VISIBLE);
            this.getActivity().setProgressBarIndeterminateVisibility(true);
        }
    }

    @Override public void hideLoading() {
        if ( !refreshing ) {
            this.rlProgress.setVisibility(View.GONE);
            this.getActivity().setProgressBarIndeterminateVisibility(false);
        } else {
            swipeRefresh.setRefreshing(false);
            refreshing = false;
        }
    }

    @Override
    public void renderTrackList(Collection<TrackModel> trackModelCollection) {
        if (trackModelCollection != null) {
//            if (pageTrack ==1) {
//                renderFirstPage(trackModelCollection);
//            } else {
//                renderNextPage(trackModelCollection);
//            }
            renderPage(trackModelCollection);
        }
    }

    @Override public void showRetry() {
        this.rlRetry.setVisibility(View.VISIBLE);
    }

    @Override public void hideRetry() {
        this.rlRetry.setVisibility(View.GONE);
    }

    @Override public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override public Context context() {
        return this.getActivity().getApplicationContext();
    }

    private void setupRecyclerView() {
        setSwipeRefresh();
        this.trackListAdapter.setOnItemClickListener(onItemClickListener);
        this.rvTracks.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context());
        this.rvTracks.setLayoutManager(layoutManager);
        this.rvTracks.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(rvTracks.getContext(), layoutManager.getOrientation());
        rvTracks.addItemDecoration(dividerItemDecoration);
        this.rvTracks.setAdapter(trackListAdapter);
        setupOnScrollListener();
    }

    private void setSwipeRefresh() {
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeResources(R.color.colorAccent);
    }

    private void setupOnScrollListener() {
        rvTracks.addOnScrollListener(new PaginationListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                if ( !searchViewOpen && networkUtil.isNetworkConnected()) {
                    isLoading = true;
                    pageTrack += 1;
                    loadTrackList();
                }
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    private void renderPage(Collection<TrackModel> trackModels) {
        Log.d(TAG, "renderPage page: " + pageTrack);
        Log.d(TAG, "renderPage size: " + trackModels.size());

        //Manage progress view
        if (pageTrack != PAGE_START && trackListAdapter.getItemCount()>0){}
//            trackListAdapter.removeLoading();

        trackListAdapter.addItems(trackModels);
//        trackListAdapter.setTracksCollection(trackModels);
        sharedPrefsUtil.put(PREFS_PAGE_TRACK, pageTrack);

        // check weather is last page or not
        if (pageTrack < TOTAL_PAGES) {
//            trackListAdapter.addLoading();
        } else {
            isLastPage = true;
        }
        isLoading = false;
        if (swipeRefresh.isRefreshing())
            swipeRefresh.setRefreshing(false);
    }

    @Override
    public void hideLoadingRow() {
//        if (pageTrack != PAGE_START)
//            trackListAdapter.removeLoading();
    }

    @Override
    public void onRefresh() {
        pageTrack = PAGE_START;
        isLastPage = false;
        refreshing = true;
        trackListAdapter.clear();
        filterAdapterView("");
        trackListListener.onSearchViewClicked();
        loadTrackList();
    }

    /**
     * Loads next page of tracks.
     */
    private void loadTrackList() {
        this.trackListPresenter.loadTracks(pageTrack, trackListAdapter.getItemCount()>0);
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        loadTrackList();
    }

    private TrackListAdapter.OnItemClickListener onItemClickListener =
            new TrackListAdapter.OnItemClickListener() {
                @Override
                public void onTrackItemClicked(TrackModel trackModel) {
                    if (trackListListener != null && trackModel != null) {
                        trackListListener.onTrackClicked(trackModel);
                    }
                }
            };

    public void filterAdapterView(String query) {
        trackListAdapter.getFilter().filter(query);
    }

    public void setSearchViewOpen(boolean open) {
        searchViewOpen = open;
        if (searchViewOpen) {
//            if (trackListAdapter.getItemCount()>0)
//                trackListAdapter.removeLoading();
        } else {
//            trackListAdapter.addLoading();
        }
    }

}
