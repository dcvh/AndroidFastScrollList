package com.example.cpu10661.fastscrolldemo.FastScrollRecyclerView;

import com.example.cpu10661.fastscrolldemo.Contact;
import com.example.cpu10661.fastscrolldemo.ContactAdapter;

import java.util.ArrayList;

/**
 * Created by cpu10661 on 12/4/17.
 */

public class FastScrollRVAdapter extends ContactAdapter
        implements FastScroller.PopupTextGetter {

    public FastScrollRVAdapter(ArrayList<Contact> contactsList) {
        super(contactsList);
    }

    @Override
    public String getTextPopup(final int position) {
        return Character.toString(mContacts.get(position).getName().charAt(0));
    }

    @Override
    public long getItemId(int position) {
        return mContacts.get(position).getId();
    }
}
