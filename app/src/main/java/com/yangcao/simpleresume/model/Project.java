package com.yangcao.simpleresume.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Project implements Parcelable {
    public String id;
    public String name;
    public Date startDate;
    public Date endDate;
    public List<String> details;

    public Project() {
        id = UUID.randomUUID().toString();
    }

    protected Project(Parcel in) {
        id = in.readString();
        name = in.readString();
        startDate = new Date(in.readLong());
        endDate = new Date(in.readLong());
        details = in.createStringArrayList();
    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeLong(startDate.getTime());
        parcel.writeLong(endDate.getTime());
        parcel.writeStringList(details);
    }
}
