package com.example.cpu10661.fastscrolldemo.AlphabetIndexRV;

import android.widget.SectionIndexer;

import com.example.cpu10661.fastscrolldemo.Contact;
import com.example.cpu10661.fastscrolldemo.ContactAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cpu10661 on 12/14/17.
 */

public class AlphabetIndexRVAdapter extends ContactAdapter implements SectionIndexer {

    public AlphabetIndexRVAdapter(ArrayList<Contact> contactsList) {
        super(contactsList);
    }

    private ArrayList<Integer> mSectionPositions;

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
        for (int i = 0, size = mContacts.size(); i < size; i++) {
            String section = String.valueOf(mContacts.get(i).getName().charAt(0)).toUpperCase();
            if (!sections.contains(section)) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        }
        return sections.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mSectionPositions.get(sectionIndex);
    }
}
