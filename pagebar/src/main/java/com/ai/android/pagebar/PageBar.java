package com.ai.android.pagebar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class PageBar extends RelativeLayout {
    private static final int DEFAULT_TOTAL_ITEMS = 3;
    private static final int DEFAULT_SELECTED_ITEM_IDX = 0;
    private static final boolean DEFAULT_BTN_NEXT_VISIBLE = true;
    private static final boolean DEFAULT_BTN_PREV_VISIBLE = true;
    private static final boolean DEFAULT_LOOPED = true;

    private ItemsAdapter itemsAdapter;
    private ImageView btnPrevView;
    private ImageView btnNextView;
    private OnChangeListener onChangeListener;

    public PageBar(Context context) {
        super(context);

        init(context, null);
    }

    public PageBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public PageBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    public PageBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context, attrs);
    }

    protected void init(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        var totalItems = DEFAULT_TOTAL_ITEMS;
        var selectedItemIdx = DEFAULT_SELECTED_ITEM_IDX;
        var item = R.drawable.indicator_dot;
        var itemSelected = R.drawable.indicator_dot_selected;
        var btnPrevVisible = DEFAULT_BTN_PREV_VISIBLE;
        var btnPrev = R.drawable.play_left;
        var btnNextVisible = DEFAULT_BTN_NEXT_VISIBLE;
        var btnNext = R.drawable.play_right;
        var looped = DEFAULT_LOOPED;

        var theme = getContext().getTheme();

        if (attributeSet != null) {
            var attrs = theme.obtainStyledAttributes(attributeSet, R.styleable.PageBar, 0, 0);
            try {
                totalItems = attrs.getInt(R.styleable.PageBar_key_total, DEFAULT_TOTAL_ITEMS);
                selectedItemIdx = attrs.getInt(R.styleable.PageBar_key_selected, DEFAULT_SELECTED_ITEM_IDX);
                item = attrs.getResourceId(R.styleable.PageBar_key_item, R.drawable.indicator_dot);
                itemSelected = attrs.getResourceId(R.styleable.PageBar_key_item_selected, R.drawable.indicator_dot_selected);
                btnPrevVisible = attrs.getBoolean(R.styleable.PageBar_key_show_prev, DEFAULT_BTN_PREV_VISIBLE);
                btnPrev = attrs.getResourceId(R.styleable.PageBar_key_prev, R.drawable.play_left);
                btnNextVisible = attrs.getBoolean(R.styleable.PageBar_key_show_next, DEFAULT_BTN_NEXT_VISIBLE);
                btnNext = attrs.getResourceId(R.styleable.PageBar_key_next, R.drawable.play_right);
                looped = attrs.getBoolean(R.styleable.PageBar_key_looped, DEFAULT_LOOPED);
            } finally {
                attrs.recycle();
            }
        }

        var layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.layout_pagebar_view, this, true);

        itemsAdapter = new ItemsAdapter(layoutInflater, getResources().getDrawable(item, theme), getResources().getDrawable(itemSelected, theme));
        itemsAdapter.setItemsCount(totalItems);
        itemsAdapter.setCurrentIdx(selectedItemIdx);
        setLooped(looped);

        this.<RecyclerView>findViewById(R.id.layout_pagebar_view__items_list).setAdapter(itemsAdapter);

        btnPrevView = findViewById(R.id.layout_pagebar_view__prev_button);
        btnPrevView.setImageDrawable(getResources().getDrawable(btnPrev, theme));
        btnPrevView.setOnClickListener(v -> selectTablePrev());
        btnPrevView.setVisibility(btnPrevVisible ? VISIBLE : GONE);
        setBtnPrevVisibility(btnPrevVisible);

        btnNextView = findViewById(R.id.layout_pagebar_view__next_button);
        btnNextView.setImageDrawable(getResources().getDrawable(btnNext, theme));
        btnNextView.setOnClickListener(v -> selectTableNext());
        btnNextView.setVisibility(btnNextVisible ? VISIBLE : GONE);
        setBtnNextVisibility(btnNextVisible);
    }

    private void selectTableNext() {
        var nextIdx =  itemsAdapter.getCurrentIdx() + 1;

        if (isLooped())
            nextIdx = (nextIdx % itemsAdapter.getItemCount());
        else {
            if (nextIdx >= itemsAdapter.getItemCount())
                nextIdx = itemsAdapter.getItemCount();
        }

        itemsAdapter.setCurrentIdx(nextIdx);

        fireOnChangeSafe();
    }

    private void fireOnChangeSafe() {
        var l = onChangeListener;
        if (l != null)
            l.OnChange(this, itemsAdapter.getCurrentIdx());
    }

    private void selectTablePrev() {
        var prevIdx =  itemsAdapter.getCurrentIdx() - 1;

        if (isLooped())
            prevIdx = (prevIdx % itemsAdapter.getItemCount());
        else {
            if (prevIdx < 0)
                prevIdx = 0;
        }

        itemsAdapter.setCurrentIdx(prevIdx < 0 ? itemsAdapter.getItemCount() - prevIdx : prevIdx);

        fireOnChangeSafe();
    }

    public int getTotalItems() {
        return itemsAdapter.getItemCount();
    }

    public void setTotalItems(int totalItems) {
        itemsAdapter.setItemsCount(totalItems);
    }

    public int getSelectedItemIdx() {
        return itemsAdapter.getCurrentIdx();
    }

    public void setSelectedItemIdx(int value) {
        itemsAdapter.setCurrentIdx(value);
    }

    public void setBtnPrevVisibility(boolean value) {
        btnPrevView.setVisibility(value ? VISIBLE : GONE);
    }

    public boolean getBtnPrevVisibility() {
        return btnPrevView.getVisibility() == VISIBLE;
    }

    public void setBtnNextVisibility(boolean value) {
        btnNextView.setVisibility(value ? VISIBLE : GONE);
    }

    public boolean getBtnNextVisibility() {
        return btnNextView.getVisibility() == VISIBLE;
    }

    public boolean isLooped() {
        return itemsAdapter.isLooped();
    }

    public void setLooped(boolean value) {
        itemsAdapter.setLooped(value);
    }

    public OnChangeListener getOnChangeListener() {
        return onChangeListener;
    }

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    public interface OnChangeListener {
        void OnChange(PageBar view, int idxSelected);
    }
}
