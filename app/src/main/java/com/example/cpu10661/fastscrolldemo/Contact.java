package com.example.cpu10661.fastscrolldemo;

/**
 * Created by cpu10661 on 12/11/17.
 */

public class Contact {

    private long mId;
    private String mName;
    private String mPhoneNumber;
    private String mPhotoThumbnailUri;
    private int mPhotoThumbnailRes;

    public Contact(long id, String name, String phoneNumber, String photoThumbnailUri) {
        this.mId = id;
        this.mName = name;
        this.mPhoneNumber = phoneNumber;
        this.mPhotoThumbnailUri = photoThumbnailUri;
    }

    public void setPhotoThumbnailRes(int resId) {
        mPhotoThumbnailRes = resId;
    }

    public String getName() {
        return mName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getPhotoThumbnailUri() {
        return mPhotoThumbnailUri;
    }

    public int getPhotoThumbnailRes() {
        return mPhotoThumbnailRes;
    }
}
