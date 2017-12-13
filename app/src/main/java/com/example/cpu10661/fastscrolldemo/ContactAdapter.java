package com.example.cpu10661.fastscrolldemo;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cpu10661 on 12/11/17.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {

    protected ArrayList<Contact> mContacts;
    private Context mContext;
    private RequestOptions mRequestOptions;

    public ContactAdapter(ArrayList<Contact> contactsList) {
        mContacts = contactsList;

        mRequestOptions = new RequestOptions();
        mRequestOptions.placeholder(R.drawable.profile_picture_placeholder);
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row_item, parent, false);
        mContext = itemView.getContext();
        return new ContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        Contact contact = mContacts.get(position);
        holder.mNameTextView.setText(contact.getName());
        holder.mPhoneTextView.setText(contact.getPhoneNumber());

        if (contact.getPhotoThumbnailUri() != null) {
            Glide.with(mContext)
                    .load(contact.getPhotoThumbnailUri())
                    .apply(mRequestOptions)
                    .into(holder.mProfilePicImageView);
//            holder.mProfilePicImageView.setImageURI(Uri.parse(contact.getPhotoThumbnailUri()));
        } else {
            Glide.with(mContext)
                    .load(contact.getPhotoThumbnailRes())
                    .apply(mRequestOptions)
                    .into(holder.mProfilePicImageView);
//            holder.mProfilePicImageView.setImageResource(contact.getPhotoThumbnailRes());
        }
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    class ContactHolder extends RecyclerView.ViewHolder {

        private TextView mNameTextView;
        private TextView mPhoneTextView;
        private CircleImageView mProfilePicImageView;
//        private ImageView mCallImageButton;
//        private ImageView mVideoCallImageButton;

        ContactHolder(View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.tv_name);
            mPhoneTextView = itemView.findViewById(R.id.tv_phone_number);
            mProfilePicImageView = itemView.findViewById(R.id.iv_profile_picture);
//            mCallImageButton = itemView.findViewById(R.id.ib_call);
//            mVideoCallImageButton = itemView.findViewById(R.id.ib_video_call);
        }
    }
}

