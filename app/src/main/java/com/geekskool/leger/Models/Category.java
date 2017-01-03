package com.geekskool.leger.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {
    private String name;
    private int resourceId;

    public Category(String name, int resourceId) {
        this.name = name;
        this.resourceId = resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Category(Parcel in){
        this.name = in.readString();
        this.resourceId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(resourceId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {

        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;

        Category category = (Category) o;

        if (resourceId != category.resourceId) return false;
        return name.equals(category.name);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + resourceId;
        return result;
    }

    @Override
    public String toString() {
        return "category : '" + name + "\'";
    }
}
