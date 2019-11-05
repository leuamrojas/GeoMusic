package com.manuelrojas.geomusic.presentation.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.manuelrojas.geomusic.R;
import com.manuelrojas.geomusic.presentation.model.TrackModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackListAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {

    private static final String TAG = "TrackListAdapter";
    private List<TrackModel> trackModelList;
    private OnItemClickListener onItemClickListener;

    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoadingAdded = false;

    private List<TrackModel> originalData;
    private ItemFilter filter = new ItemFilter();

    @Inject
    Picasso picasso;

    @Inject
    TrackListAdapter() {
        trackModelList = Collections.emptyList();
        originalData = trackModelList;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new TrackViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.row_track, parent, false));
            default:
                return new ProgressViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.row_progress, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoadingAdded) {
            return position == getItemCount() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return trackModelList != null ? trackModelList.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateTrackModelCollection(Collection<TrackModel> tracksCollection) {
        if (tracksCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    public interface OnItemClickListener {
        void onTrackItemClicked(TrackModel trackModel);
    }

    /*
    Pagination helpers
     */
//    public void addItems(Collection<TrackModel> trackModelCollection) {
//        this.validateTrackModelCollection(trackModelCollection);
//        List<TrackModel> newList = new ArrayList<>(trackModelList);
//        trackModelList = new ArrayList<>(newList);
//        trackModelList.addAll(trackModelCollection);
//        originalData = trackModelList;
//        this.notifyDataSetChanged();
//    }

//    public void addItems(Collection<TrackModel> trackModelCollection) {
//        this.validateTrackModelCollection(trackModelCollection);
//        trackModelList.addAll(trackModelCollection);
//        originalData = trackModelList;
//        notifyDataSetChanged();
//    }

//    public void setTracksCollection(Collection<TrackModel> trackModelCollection) {
//        this.validateTrackModelCollection(trackModelCollection);
//        this.trackModelList = (List<TrackModel>) trackModelCollection;
////        setRowHeaders();
//        originalData = trackModelList;
////        this.fixtureType = fixtureType;
//        this.notifyDataSetChanged();
//    }

    public void addItems(Collection<TrackModel> trackModelCollection) {
        this.validateTrackModelCollection(trackModelCollection);
        List<TrackModel> newList = new ArrayList<>(trackModelList);
        trackModelList = new ArrayList<>(newList);
        trackModelList.addAll(trackModelCollection);
        originalData = trackModelList;
        Log.d(TAG, "trackModelList: " + trackModelList.size());
        this.notifyDataSetChanged();
    }

//    public void addLoading() {
//        isLoadingAdded = true;
//        trackModelList.add(new TrackModel());
//        notifyItemInserted(trackModelList.size() - 1);
//    }
//
//    public void removeLoading() {
//        isLoadingAdded = false;
//        int position = trackModelList.size() - 1;
////        Log.d(TAG, "trackModelList.size(): " + trackModelList.size());
//        TrackModel item = getItem(position);
//        if (item != null && item.getName()==null) {
//            Log.d(TAG, "TrackModel Loading: " + item.getName());
//            trackModelList.remove(position);
//            notifyItemRemoved(position);
//        }
//    }

    public void clear() {
        Log.d(TAG, "clear before: " + trackModelList.size());
        trackModelList.clear();
        originalData = trackModelList;
        Log.d(TAG, "clear after: " + trackModelList.size());
        notifyDataSetChanged();
    }

    TrackModel getItem(int position) {
        return trackModelList.get(position);
    }

    class TrackViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_track_details_name)
        TextView tvTrackName;
        @BindView(R.id.tv_track_artist)
        TextView tvTrackArtist;
        @BindView(R.id.iv_track)
        ImageView ivTrack;

        TrackViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            final TrackModel trackModel = trackModelList.get(position);
            tvTrackName.setText(trackModel.getName());
            tvTrackArtist.setText(trackModel.getArtistName());
            picasso.load(trackModel.getImageSmall())
                    .fit()
                    .centerCrop()
                    .into(ivTrack);
            itemView.setOnClickListener(view -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onTrackItemClicked(trackModel);
                }
            });
        }
    }

    static class ProgressViewHolder extends BaseViewHolder {
        ProgressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase(Locale.ENGLISH);
            final List<TrackModel> list = originalData;
            int count = list.size();
            final ArrayList<TrackModel> nlist = new ArrayList<>(count);
            String filterableStringName;
            for (int i = 0; i < count; i++) {
                filterableStringName = list.get(i).getName();
                if (filterableStringName!=null) {
                    if (filterableStringName.trim().toLowerCase(Locale.ENGLISH)
                            .contains(filterString.trim().toLowerCase(Locale.ENGLISH))) {
                        nlist.add(list.get(i));
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            trackModelList = (ArrayList<TrackModel>) results.values;
            notifyDataSetChanged();
        }

    }
}
