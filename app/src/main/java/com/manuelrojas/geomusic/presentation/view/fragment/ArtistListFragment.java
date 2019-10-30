package com.manuelrojas.geomusic.presentation.view.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.manuelrojas.geomusic.R;
import com.manuelrojas.geomusic.data.utils.NetworkUtil;
import com.manuelrojas.geomusic.data.utils.SharedPrefsUtil;
import com.manuelrojas.geomusic.presentation.di.component.TrackComponent;
import com.manuelrojas.geomusic.presentation.model.ArtistModel;
import com.manuelrojas.geomusic.presentation.view.ArtistListView;
import com.manuelrojas.geomusic.presentation.view.adapter.PaginationListener;
import com.manuelrojas.geomusic.presentation.presenter.ArtistListPresenter;
import com.manuelrojas.geomusic.presentation.view.adapter.ArtistListAdapter;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ArtistListFragment extends BaseFragment implements ArtistListView {


    private static final String TAG = "ArtistListFragment";

    /**
     * Interface for listening artist list events.
     */
    public interface ArtistListListener {
        void onArtistClicked(final ArtistModel artistModel);
    }

    @Inject
    ArtistListPresenter artistListPresenter;

    @Inject
    ArtistListAdapter artistListAdapter;

    @BindView(R.id.rv_artists)
    RecyclerView rvArtists;

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

    private static final String PREFS_PAGE_ARTIST = "prefsPageArtist";

//    @BindView(R.id.swipeRefresh)
//    SwipeRefreshLayout swipeRefresh;

    private ArtistListListener artistListListener;
    private Unbinder unbinder;

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
    private int pageArtist = PAGE_START;

    public ArtistListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ArtistListListener) {
            this.artistListListener = (ArtistListListener) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(TrackComponent.class).inject(this);
        sharedPrefsUtil = new SharedPrefsUtil(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_artist_list, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.artistListPresenter.attachView(this);
        if (savedInstanceState == null) {
            pageArtist = sharedPrefsUtil.get(PREFS_PAGE_ARTIST, 1);
            loadArtistList();
        } else {
            pageArtist = savedInstanceState.getInt(PREFS_PAGE_ARTIST);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PREFS_PAGE_ARTIST, pageArtist);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rvArtists.setAdapter(null);
        if (unbinder != null)
            unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.artistListPresenter.detachView();
    }

    @Override public void onDetach() {
        super.onDetach();
        this.artistListListener = null;
    }

    @Override public void showLoading() {
        this.rlProgress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override public void hideLoading() {
        this.rlProgress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void renderArtistList(Collection<ArtistModel> artistModelCollection) {
        if (artistModelCollection != null) {
            if (pageArtist ==1) {
                renderFirstPage(artistModelCollection);
            } else {
                renderNextPage(artistModelCollection);
            }
        }
    }

    @Override
    public void showRetry() {
        this.rlRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        this.rlRetry.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    private void setupRecyclerView() {
//        swipeRefresh.setOnRefreshListener(this);
        this.artistListAdapter.setOnItemClickListener(onItemClickListener);
        this.rvArtists.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context());
        this.rvArtists.setLayoutManager(layoutManager);
        this.rvArtists.setItemAnimator(new DefaultItemAnimator());
        this.rvArtists.setAdapter(artistListAdapter);
        setupOnScrollListener();
    }

    private void setupOnScrollListener() {
        rvArtists.addOnScrollListener(new PaginationListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                if ( !searchViewOpen && networkUtil.isNetworkConnected()) {
                    isLoading = true;
                    pageArtist += 1;
                    loadArtistList();
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

    private void renderFirstPage(Collection<ArtistModel> artistModels) {
        artistListAdapter.addItems(artistModels);
        sharedPrefsUtil.put(PREFS_PAGE_ARTIST, pageArtist);
//        if (pageArtist <= TOTAL_PAGES)
//            artistListAdapter.addLoading();
//        else
//            isLastPage = true;
        if (pageArtist == TOTAL_PAGES)
            isLastPage = true;
    }

    private void renderNextPage(Collection<ArtistModel> artistModels) {
        isLoading = false;
        artistListAdapter.addItems(artistModels);
        sharedPrefsUtil.put(PREFS_PAGE_ARTIST, pageArtist);
//        if (pageArtist != TOTAL_PAGES)
//            artistListAdapter.addLoading();
//        else
//            isLastPage = true;
        if (pageArtist == TOTAL_PAGES)
            isLastPage = true;
    }

    /**
     * Loads next page of artists.
     */
    private void loadArtistList() {
        this.artistListPresenter.loadArtists(pageArtist, artistListAdapter.getItemCount()>0);
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        loadArtistList();
    }

    private ArtistListAdapter.OnItemClickListener onItemClickListener =
            new ArtistListAdapter.OnItemClickListener() {
                @Override
                public void onArtistItemClicked(ArtistModel artistModel) {
                    if (artistListListener != null && artistModel != null) {
                        artistListListener.onArtistClicked(artistModel);
                    }
                }
            };

    public void filterAdapterView(String query) {
        artistListAdapter.getFilter().filter(query);
    }

    public void setSearchViewOpen(boolean open) {
        searchViewOpen = open;
    }

}
