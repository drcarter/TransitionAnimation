package com.drcarter.android.transitionanimation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private ListView mListView = null;

    private String[] mMenus = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        this.mMenus = getResources().getStringArray(R.array.menu_arr);

        this.mListView = (ListView) findViewById(R.id.listview);
        this.mListView.setOnItemClickListener(this);
        this.mListView.setAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.mMenus));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String menu = this.mMenus[position];
        if (menu.equals(getString(R.string.animation_activity))) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (menu.equals(getString(R.string.animation_fragment))) {
            Intent intent = new Intent(this, FragmentAnimationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }
}
