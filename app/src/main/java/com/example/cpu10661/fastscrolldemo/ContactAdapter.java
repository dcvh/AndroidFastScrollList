package com.example.cpu10661.fastscrolldemo;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cpu10661 on 12/11/17.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {

    private static final int DEFAULT_DELAYED_LOADING_PICTURE_MILLIS = 16;

    protected ArrayList<Contact> mContacts;
    private RequestManager mGlide;
    private RequestOptions mRequestOptions;

    private Handler mHandler = new Handler();

    public ContactAdapter(ArrayList<Contact> contactsList) {
        mContacts = contactsList;

        mRequestOptions = new RequestOptions()
                .placeholder(R.drawable.profile_picture_placeholder)
                .dontAnimate(); //CircleImageView limitation
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row_item, parent, false);
        mGlide = Glide.with(itemView);
        return new ContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ContactHolder holder, int position) {
        final Contact contact = mContacts.get(position);
        holder.mNameTextView.setText(contact.getName());
        holder.mPhoneTextView.setText(contact.getPhoneNumber());

        // we delayed the loading profile picture task by 50ms and check if we've bypassed it or not
        // this is an attempt to avoid loading pictures without showing them
        holder.setShowing(true);
        mHandler.postDelayed(new DelayedLoadingPictureTask(holder, contact),
                DEFAULT_DELAYED_LOADING_PICTURE_MILLIS);
    }

    @Override
    public void onViewRecycled(ContactHolder holder) {
        super.onViewRecycled(holder);

        // if this contact is off the screen, we don't need to load its profile picture anymore
        holder.setShowing(false);
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    class ContactHolder extends RecyclerView.ViewHolder {

        private boolean mIsShowing = false;

        private TextView mNameTextView;
        private TextView mPhoneTextView;
        private CircleImageView mProfilePicImageView;
        private ImageView mCallImageButton;
        private ImageView mVideoCallImageButton;

        ContactHolder(View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.tv_name);
            mPhoneTextView = itemView.findViewById(R.id.tv_phone_number);
            mProfilePicImageView = itemView.findViewById(R.id.iv_profile_picture);
            mCallImageButton = itemView.findViewById(R.id.ib_call);
            mVideoCallImageButton = itemView.findViewById(R.id.ib_video_call);
        }

        boolean isShowing() {
            return mIsShowing;
        }

        void setShowing(boolean isShowing) {
            mIsShowing = isShowing;
        }
    }

    class DelayedLoadingPictureTask implements Runnable {

        private ContactHolder mHolder;
        private Contact mContact;

        DelayedLoadingPictureTask(ContactHolder holder, Contact contact) {
            mHolder = holder;
            mContact = contact;
        }

        @Override
        public void run() {
            if (mHolder.isShowing()) {
                if (mContact.getPhotoThumbnailUri() != null) {
                    mGlide.load(mContact.getPhotoThumbnailUri())
                            .apply(mRequestOptions)
                            .into(mHolder.mProfilePicImageView);
                } else {
                    mGlide.load(mContact.getPhotoThumbnailRes())
                            .apply(mRequestOptions)
                            .into(mHolder.mProfilePicImageView);
                }
            }
        }
    }
}

