package com.example.lab4v4.dummy;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ContactsContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<ContactItem> ITEMS = new ArrayList<ContactItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, ContactItem> ITEM_MAP = new HashMap<String, ContactItem>();

    private static final int COUNT = 0;

    static {
        // Add some sample items.
        for(int i = 1; i <= COUNT; i++) {
            addItem(createContactItem(i));
        }
    }

    public static void addItem(ContactItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
    public static ContactItem getItem(int position){
        return ITEMS.get(position);
    }
    private static ContactItem createContactItem(int position) {
        return new ContactItem(String.valueOf(position), "Task " + position, makeDetails(position));
    }
    public static void clearList()
    {
        ITEMS.clear();
        ITEM_MAP.clear();
    }
    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for(int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    public static void removeItem(int currentItemPosition) {
        String id = ITEMS.get(currentItemPosition).id;
        ITEMS.remove(currentItemPosition);
        ITEM_MAP.remove(id);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class ContactItem implements Parcelable {
        public final String id;
        public final String name;
        public final String surname;
        public final String phoneNumber;
        public final String ringtone;
        public final String picPath;

        public ContactItem(String id, String name, String surname) {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.picPath = "";
            this.ringtone = "";
            this.phoneNumber = "";
        }
        public ContactItem(String id, String name, String surname, String pic) {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.picPath = pic;
            this.ringtone = "";
            this.phoneNumber = "";
        }
        public ContactItem(String id, String name, String surname, String pic, String ringtone, String phoneNumber) {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.picPath = pic;
            this.ringtone = ringtone;
            this.phoneNumber = phoneNumber;
        }

        protected ContactItem(Parcel in) {
            id = in.readString();
            name = in.readString();
            surname = in.readString();
            phoneNumber = in.readString();
            ringtone = in.readString();
            picPath = in.readString();
        }

        public static final Creator<ContactItem> CREATOR = new Creator<ContactItem>() {
            @Override
            public ContactItem createFromParcel(Parcel in) {
                return new ContactItem(in);
            }

            @Override
            public ContactItem[] newArray(int size) {
                return new ContactItem[size];
            }
        };

        @Override
        public String toString() {
            return name + " " + surname;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(id);
            parcel.writeString(name);
            parcel.writeString(surname);
            parcel.writeString(phoneNumber);
            parcel.writeString(ringtone);
            parcel.writeString(picPath);
        }
    }
}