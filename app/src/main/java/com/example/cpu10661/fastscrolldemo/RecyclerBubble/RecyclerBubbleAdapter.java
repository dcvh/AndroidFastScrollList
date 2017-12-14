package com.example.cpu10661.fastscrolldemo.RecyclerBubble;

import com.example.cpu10661.fastscrolldemo.Contact;
import com.example.cpu10661.fastscrolldemo.ContactAdapter;
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;

import java.util.ArrayList;

/**
 * Created by cpu10661 on 12/14/17.
 */

public class RecyclerBubbleAdapter extends ContactAdapter implements SectionTitleProvider {

    public RecyclerBubbleAdapter(ArrayList<Contact> contactsList) {
        super(contactsList);
    }

    @Override
    public String getSectionTitle(int position) {
        return Character.toString(mContacts.get(position).getName().charAt(0));
    }
}
