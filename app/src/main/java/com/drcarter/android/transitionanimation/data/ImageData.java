package com.drcarter.android.transitionanimation.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageData implements Parcelable {

    private int mImageID = 0;
    private String mMessage = null;

    public ImageData(int resID, String message) {
        this.mImageID = resID;
        this.mMessage = message;
    }

    public ImageData(Parcel in) {
        readFromParcel(in);
    }

    public int getImageID() {
        return mImageID;
    }

    public void setImageID(int mImageID) {
        this.mImageID = mImageID;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mImageID);
        dest.writeString(this.mMessage);
    }

    private void readFromParcel(Parcel in) {
        this.mImageID = in.readInt();
        this.mMessage = in.readString();
    }

    public static final Creator CREATOR = new Creator() {
        @Override
        public ImageData createFromParcel(Parcel source) {
            return new ImageData(source);
        }

        @Override
        public ImageData[] newArray(int size) {
            return new ImageData[size];
        }
    };
}
