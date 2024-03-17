package com.ai.android.pagebar.app;

import android.app.Activity;
import android.os.Bundle;

import com.ai.android.pagebar.PageBar;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        createUi();
    }

    private void createUi() {
        var pageBar = this.<PageBar>findViewById(R.id.activity_main__page_bar_3);
        pageBar.setTotalItems(7);
        pageBar.setSelectedItemIdx(2);
    }
}
