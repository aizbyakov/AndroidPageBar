package com.ai.android.pagebar.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.ai.android.pagebar.PageBar;

import java.util.Locale;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getName();

    private TextView lastSelectedIdx;
    private Button btnResetSelection;

    private PageBar lastUsedPageBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        createUi();
    }

    private void createUi() {
        var pageBar = this.<PageBar>findViewById(R.id.activity_main__page_bar_1);
        pageBar.setOnChangeListener(this::OnChangeIndex);

        pageBar = this.findViewById(R.id.activity_main__page_bar_2);
        pageBar.setOnChangeListener(this::OnChangeIndex);

        pageBar = this.findViewById(R.id.activity_main__page_bar_3);
        pageBar.setTotalItems(7);
        pageBar.setSelectedItemIdx(2);
        pageBar.setOnChangeListener(this::OnChangeIndex);

        pageBar = this.findViewById(R.id.activity_main__page_bar_4);
        pageBar.setOnChangeListener(this::OnChangeIndex);

        pageBar = this.findViewById(R.id.activity_main__page_bar_5);
        pageBar.setOnChangeListener(this::OnChangeIndex);

        pageBar = this.findViewById(R.id.activity_main__page_bar_6);
        pageBar.setOnChangeListener(this::OnChangeIndex);

        pageBar = this.findViewById(R.id.activity_main__page_bar_7);
        pageBar.setOnChangeListener(this::OnChangeIndex);

        lastSelectedIdx = findViewById(R.id.activity_main__last_selected_idx);

        btnResetSelection = findViewById(R.id.activity_main__btn__reset_selection);
        btnResetSelection.setOnClickListener(v -> doResetSelection());
    }

    private void OnChangeIndex(PageBar view, int idxSelected) {
        lastUsedPageBar = view;

        lastSelectedIdx.setText(String.format(Locale.US, "Bar ID: %d; Index: %d; Has Selection: %s", view.getId(), idxSelected, view.hasSelection()));

        refreshUi();
    }

    private void doResetSelection() {
        var v = lastUsedPageBar;
        lastUsedPageBar = null;

        if (v != null)
            v.resetSelection();

        refreshUi();
    }

    private void refreshUi() {
        btnResetSelection.setEnabled(lastUsedPageBar != null);
    }
}
