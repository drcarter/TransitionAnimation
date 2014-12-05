package com.drcarter.android.transitionanimation;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.drcarter.android.transitionanimation.data.ImageData;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private ListView mListView = null;

    private ArrayList<ImageData> mListData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListData = new ArrayList<ImageData>();
        mListData.add(new ImageData(R.drawable.android_1, "android 1 image data"));
        mListData.add(new ImageData(R.drawable.android_2, "android 2 image data sample"));
        mListData.add(new ImageData(R.drawable.android_3, "android 3 image"));
        mListData.add(new ImageData(R.drawable.android_4, "android 4 data"));
        mListData.add(new ImageData(R.drawable.android_5, "android 5"));

        this.mListView = (ListView) findViewById(R.id.list_sample);
        this.mListView.setOnItemClickListener(this);
        this.mListView.setAdapter(new ImageListAdapter());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int firstVisiblePosition = parent.getFirstVisiblePosition();
        View selectView = parent.getChildAt(position - firstVisiblePosition);
        ImageView imageThumb = (ImageView) selectView.findViewById(R.id.image_thumb);
        int[] screenLocation = new int[2];
        imageThumb.getLocationOnScreen(screenLocation);

        Intent intent = new Intent(this, ImageDetailActivity.class);
        intent.putExtra("data", mListData.get(position));
        intent.putExtra("left", screenLocation[0]);
        intent.putExtra("top", screenLocation[1]);
        intent.putExtra("width", imageThumb.getWidth());
        intent.putExtra("height", imageThumb.getHeight());
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private class ImageListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mListData != null) {
                return mListData.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mListData != null) {
                return mListData.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.image_list_item, null);
                holder = new ViewHolder();
                holder.mImageThumb = (ImageView) convertView.findViewById(R.id.image_thumb);
                holder.mTextMessage = (TextView) convertView.findViewById(R.id.text_message);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ImageData data = (ImageData) getItem(position);
            if (data != null) {
                holder.mImageThumb.setImageResource(data.getImageID());
                holder.mTextMessage.setText(data.getMessage());
            }

            return convertView;
        }

        private class ViewHolder {
            ImageView mImageThumb = null;
            TextView mTextMessage = null;
        }
    }

}
