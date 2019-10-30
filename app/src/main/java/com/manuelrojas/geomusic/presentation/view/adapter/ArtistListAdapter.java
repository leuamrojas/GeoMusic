package com.manuelrojas.geomusic.presentation.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.manuelrojas.geomusic.R;
import com.manuelrojas.geomusic.presentation.model.ArtistModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArtistListAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {

    private static final String TAG = "ArtistListAdapter";
    private List<ArtistModel> artistModelList;
    private OnItemClickListener onItemClickListener;

    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoadingAdded = false;

    private List<ArtistModel> originalData;
    private ItemFilter filter = new ItemFilter();

    @Inject
    Picasso picasso;

    @Inject
    ArtistListAdapter() {
        artistModelList = Collections.emptyList();
        originalData = artistModelList;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ArtistViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.row_artist, parent, false));
            case VIEW_TYPE_LOADING:
                return new ProgressHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.view_progress, parent, false));
            default:
                return null;
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
        return artistModelList != null ? artistModelList.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateArtistModelCollection(Collection<ArtistModel> artistModelCollection) {
        if (artistModelCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    public interface OnItemClickListener {
        void onArtistItemClicked(ArtistModel artistModel);
    }

    /*
    Pagination helpers
     */
    public void addItems(Collection<ArtistModel> artistModelCollection) {
        this.validateArtistModelCollection(artistModelCollection);
        List<ArtistModel> newList = new ArrayList<>(artistModelList);
        this.artistModelList = new ArrayList<>(newList);
        this.artistModelList.addAll(artistModelCollection);
        this.originalData = artistModelList;
        this.notifyDataSetChanged();
    }

    class ArtistViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_artist_list_name)
        TextView tvArtistListName;
        @BindView(R.id.iv_artist_list)
        ImageView ivArtist;

        ArtistViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            final ArtistModel artistModel = artistModelList.get(position);
            tvArtistListName.setText(artistModel.getName());
            picasso.load(artistModel.getImageSmall())
                    .fit()
                    .centerCrop()
                    .into(ivArtist);
            itemView.setOnClickListener(view -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onArtistItemClicked(artistModel);
                }
            });
        }
    }

    static class ProgressHolder extends BaseViewHolder {
        ProgressHolder(View itemView) {
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
            final List<ArtistModel> list = originalData;
            int count = list.size();
            final ArrayList<ArtistModel> nlist = new ArrayList<>(count);
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
            artistModelList = (ArrayList<ArtistModel>) results.values;
            notifyDataSetChanged();
        }

    }

}