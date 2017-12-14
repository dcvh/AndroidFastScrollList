package com.example.cpu10661.fastscrolldemo.RVFastScrollLib;

import android.support.annotation.NonNull;

import com.example.cpu10661.fastscrolldemo.Contact;
import com.example.cpu10661.fastscrolldemo.ContactAdapter;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.ArrayList;

/**
 * Created by cpu10661 on 12/14/17.
 */

public class RVFastScrollLibAdapter extends ContactAdapter
        implements FastScrollRecyclerView.SectionedAdapter {

    public RVFastScrollLibAdapter(ArrayList<Contact> contactsList) {
        super(contactsList);
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        return Character.toString(mContacts.get(position).getName().charAt(0));
    }
}
