package com.example.cpu10661.fastscrolldemo.FastScroller;

import com.example.cpu10661.fastscrolldemo.Contact;
import com.example.cpu10661.fastscrolldemo.ContactAdapter;

import java.util.ArrayList;

/**
 * Created by cpu10661 on 12/4/17.
 */

public class FastScrollerAdapter extends ContactAdapter
        implements FastScroller.BubbleTextGetter {

    public FastScrollerAdapter(ArrayList<Contact> contactsList) {
        super(contactsList);
    }

    @Override
    public String getTextToShowInBubble(final int position) {
        return Character.toString(mContacts.get(position).getName().charAt(0));
    }
}
