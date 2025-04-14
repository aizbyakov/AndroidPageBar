package com.ai.android.pagebar;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    public static final int DEFAULT_SELECTED_ITEM_IDX__NOT_SELECTED = -1;

    private final LayoutInflater layoutInflater;
    private final Drawable itemMarker;
    private final Drawable itemMarkerSelected;

    private int count = 0;
    private int currentIdx = DEFAULT_SELECTED_ITEM_IDX__NOT_SELECTED;

    private boolean looped = false;

    public ItemsAdapter(@NonNull LayoutInflater layoutInflater, @NonNull Drawable itemMarker, @NonNull Drawable itemMarkerSelected) {
        this.layoutInflater = layoutInflater;
        this.itemMarker = itemMarker;
        this.itemMarkerSelected = itemMarkerSelected;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.items_bar_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageDrawable((currentIdx == position) ? itemMarkerSelected : itemMarker);
    }

    @Override
    public int getItemCount() {
        return count;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItemsCount(int value) {
        count = Math.max(value, 0);

        currentIdx = getFixedCurrentIdx(currentIdx, count);

        notifyDataSetChanged();
    }

    private static int getFixedCurrentIdx(int idx, int count) {
        if (idx >= count)
            idx = count - 1;

        return idx < 0 ? DEFAULT_SELECTED_ITEM_IDX__NOT_SELECTED : idx;
    }

    public int getCurrentIdx() {
        return currentIdx;
    }

    public void setCurrentIdx(int value) {
        var currentIdxPrev = currentIdx;

        currentIdx = getFixedCurrentIdx(value, count);

        if (count > 0) {
            if (currentIdxPrev >= 0 && currentIdxPrev < count)
                notifyItemRangeChanged(currentIdxPrev, 1);

            if (currentIdx >= 0 && currentIdx < count)
                notifyItemRangeChanged(currentIdx, 1);
        }
    }

    public boolean isLooped() {
        return looped;
    }

    public void setLooped(boolean looped) {
        this.looped = looped;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.imageView = itemView.findViewById(R.id.items_count_bar_item__img);
        }
    }
}
