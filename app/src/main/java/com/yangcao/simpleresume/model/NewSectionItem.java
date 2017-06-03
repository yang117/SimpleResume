package com.yangcao.simpleresume.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.UUID;


public class NewSectionItem implements Parcelable {

    public String id;

    public String sectionNameId;

    public String title;

    public Date date;

    public String content;

    public NewSectionItem() {
        id = UUID.randomUUID().toString();
        //加一个section id??
    }

    protected NewSectionItem(Parcel in) {
        id = in.readString();
        sectionNameId = in.readString(); //待改？？？
        title = in.readString();
        date = new Date(in.readLong());
        content = in.readString();
    }

    public static final Creator<NewSectionItem> CREATOR = new Creator<NewSectionItem>() {
        @Override
        public NewSectionItem createFromParcel(Parcel in) {
            return new NewSectionItem(in);
        }

        @Override
        public NewSectionItem[] newArray(int size) {
            return new NewSectionItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(sectionNameId);
        dest.writeString(title);
        dest.writeLong(date.getTime());
        dest.writeString(content);
    }
}
