package com.yangcao.simpleresume.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class NewSection implements Parcelable{

    public String id;

    public String name;

    public NewSection() {
        id = UUID.randomUUID().toString();
    }

    protected NewSection(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<NewSection> CREATOR = new Creator<NewSection>() {
        @Override
        public NewSection createFromParcel(Parcel in) {
            return new NewSection(in);
        }

        @Override
        public NewSection[] newArray(int size) {
            return new NewSection[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }
}
