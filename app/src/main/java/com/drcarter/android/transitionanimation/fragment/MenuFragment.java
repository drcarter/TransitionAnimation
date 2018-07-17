package com.drcarter.android.transitionanimation.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.drcarter.android.transitionanimation.ImageDetailActivity;
import com.drcarter.android.transitionanimation.R;
import com.drcarter.android.transitionanimation.data.ImageData;

import java.util.ArrayList;

public class MenuFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView mListView = null;

    private ArrayList<ImageData> mListData = null;

    private OnMenuFragmentListener mListener = null;

    public interface OnMenuFragmentListener {
        void onImageItemSelect(ImageData imageData, int left, int top, int width, int height);
    }

    public static Fragment newInstance() {
        return new MenuFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        this.mListView = (ListView) view.findViewById(R.id.list_sample);
        this.mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListData = new ArrayList<ImageData>();
        mListData.add(new ImageData(R.drawable.android_1, "android 1 image data"));
        mListData.add(new ImageData(R.drawable.android_2, "android 2 image data sample"));
        mListData.add(new ImageData(R.drawable.android_3, "android 3 image"));
        mListData.add(new ImageData(R.drawable.android_4, "android 4 data"));
        mListData.add(new ImageData(R.drawable.android_5, "android 5"));

        this.mListView.setAdapter(new ImageListAdapter());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int firstVisiblePosition = parent.getFirstVisiblePosition();
        View selectView = parent.getChildAt(position - firstVisiblePosition);
        ImageView imageThumb = (ImageView) selectView.findViewById(R.id.image_thumb);
        int[] screenLocation = new int[2];
        imageThumb.getLocationOnScreen(screenLocation);

        Intent intent = new Intent(getActivity(), ImageDetailActivity.class);
        intent.putExtra("data", mListData.get(position));
        intent.putExtra("left", screenLocation[0]);
        intent.putExtra("top", screenLocation[1]);
        intent.putExtra("width", imageThumb.getWidth());
        intent.putExtra("height", imageThumb.getHeight());
        startActivity(intent);
        getActivity().overridePendingTransition(0, 0);
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
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.image_list_item, null);
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
