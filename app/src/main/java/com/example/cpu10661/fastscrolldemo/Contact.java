package com.example.cpu10661.fastscrolldemo;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cpu10661 on 12/11/17.
 */

public class Contact implements Parcelable {

    private long mId;
    private String mName;
    private String mPhoneNumber;
    private Uri mPhotoThumbnailUri;
    private int mPhotoThumbnailRes;

    public Contact(long id, String name, String phoneNumber, Uri photoThumbnailUri) {
        this.mId = id;
        this.mName = name;
        this.mPhoneNumber = phoneNumber;
        this.mPhotoThumbnailUri = photoThumbnailUri;
    }

    protected Contact(Parcel in) {
        mId = in.readLong();
        mName = in.readString();
        mPhoneNumber = in.readString();
        mPhotoThumbnailUri = in.readParcelable(Uri.class.getClassLoader());
        mPhotoThumbnailRes = in.readInt();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public void setPhotoThumbnailRes(int resId) {
        mPhotoThumbnailRes = resId;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public Uri getPhotoThumbnailUri() {
        return mPhotoThumbnailUri;
    }

    public int getPhotoThumbnailRes() {
        return mPhotoThumbnailRes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mId);
        parcel.writeString(mName);
        parcel.writeString(mPhoneNumber);
        parcel.writeParcelable(mPhotoThumbnailUri, i);
        parcel.writeInt(mPhotoThumbnailRes);
    }
}
