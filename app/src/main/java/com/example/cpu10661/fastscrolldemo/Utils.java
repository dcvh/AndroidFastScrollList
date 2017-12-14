package com.example.cpu10661.fastscrolldemo;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by cpu10661 on 12/8/17.
 */

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    public static int dpToPx(Resources res, float dp) {
        return (int) (dp * res.getDisplayMetrics().density);
    }

    public static int dpToPx(Context context, float dp) {
        return dpToPx(context.getResources(), dp);
    }

    public static int spToPx(Resources res, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, res.getDisplayMetrics());
    }

    public static int spToPx(Context context, float sp) {
        return spToPx(context.getResources(), sp);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isRtl(Resources res) {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) &&
                (res.getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL);
    }

    public static final String[] EVENT_PROJECTION = new String[] {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.HAS_PHONE_NUMBER,
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI
    };
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_NAME_INDEX = 1;
    private static final int PROJECTION_HAS_PHONE_INDEX = 2;
    private static final int PROJECTION_PHOTO_THUMBNAIL_URI_INDEX = 3;

    public static ArrayList<Contact> getContactsList(Context context) {
        ArrayList<Contact> contacts = new ArrayList<>();

        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                EVENT_PROJECTION, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                long id = cur.getLong(PROJECTION_ID_INDEX);
                String name = cur.getString(PROJECTION_NAME_INDEX);
                String photoThumbnailUri = cur.getString(PROJECTION_PHOTO_THUMBNAIL_URI_INDEX);

                if (cur.getInt(PROJECTION_HAS_PHONE_INDEX) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{String.valueOf(id)}, null);
                    while (pCur != null && pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        Log.i(TAG, "Name: " + name);
//                        Log.i(TAG, "Phone Number: " + phoneNo);

                        Contact contact = new Contact(id, name, phoneNo,
                                photoThumbnailUri != null ? Uri.parse(photoThumbnailUri) : null);
                        contacts.add(contact);
                        break;
                    }
                    if (pCur != null) {
                        pCur.close();
                    }
                }
            }
            Log.d(TAG, "getContactsList: " + contacts.size());
        }
        if (cur != null) {
            cur.close();
        }

        Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact lhs, Contact rhs) {
                return lhs.getName().compareToIgnoreCase(rhs.getName());
            }
        });
        return contacts;
    }

    public static ArrayList<Contact> generateContactsList(int size) {
        ArrayList<Contact> contacts = new ArrayList<>();

        String[] lastNames = new String[] {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor"};
        String[] firstNames = new String[] {"Sophia", "Jackson", "Olivia", "Liam", "Emma", "Noah", "Ava Aiden", "Isabella", "Lucas"};
        int[] photoNames = new int[] {R.drawable.avatar_1, R.drawable.avatar_2, R.drawable.avatar_3, R.drawable.avatar_4, R.drawable.avatar_5, R.drawable.avatar_6, R.drawable.avatar_7, R.drawable.avatar_8};

        Random seed = new Random();
        for (int i = 0; i < size; i++) {
            String name = firstNames[seed.nextInt(firstNames.length)]
                    + " " + lastNames[seed.nextInt(lastNames.length)];
            String phoneNumber = String.valueOf(Math.round(seed.nextDouble() * 1000000000));
            Contact contact = new Contact(i, name, phoneNumber, null);
            contact.setPhotoThumbnailRes(photoNames[seed.nextInt(photoNames.length)]);

            contacts.add(contact);
        }
        Log.d(TAG, "generateContactsList: " + contacts.size());

        Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact lhs, Contact rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        return contacts;
    }

    public static ArrayList<String> getContactNames() {
        ArrayList<String> contactNames = new ArrayList<>();
        for (char character = 'A'; character <= 'Z'; character++) {
            for (int digit = 0; digit <= 100; digit++) {
                contactNames.add(character + " - " + digit);
            }
        }
        return contactNames;
    }
}
