package com.ai.android.pagebar;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    private final LayoutInflater layoutInflater;
    private final Drawable itemMarker;
    private final Drawable itemMarkerSelected;

    private int total = 0;
    private int currentIdx = 0;

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
        return total;
    }

    public void setItemsCount(int value) {
        total = value;

        if (total < 0)
            total = 0;

        if (currentIdx >= total)
            currentIdx = 0;

        notifyItemRangeChanged(0, total - 1);
    }

    public int getCurrentIdx() {
        return currentIdx;
    }

    public void setCurrentIdx(int value) {
        var currentIdxPrev = currentIdx;

        if (total == 0) {
            value = 0;
        } else {
            if (value < 0)
                value = 0;

            if (value >= total)
                value = total - 1;
        }

        currentIdx = value;

        notifyItemRangeChanged(currentIdxPrev, 1);
        notifyItemRangeChanged(currentIdx, 1);
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
