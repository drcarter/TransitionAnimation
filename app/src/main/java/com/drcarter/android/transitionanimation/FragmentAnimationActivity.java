package com.drcarter.android.transitionanimation;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.drcarter.android.transitionanimation.fragment.MenuFragment;

public class FragmentAnimationActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_animation);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, MenuFragment.newInstance(), "menu_fragment")
                .commit();
    }
}
