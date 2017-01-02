package com.geekskool.leger.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by manisharana on 27/12/16.
 */
public enum StateOptions implements Parcelable {
    verified, unverified, fraud;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ordinal());
    }

    public static final Parcelable.Creator<StateOptions> CREATOR = new Parcelable.Creator<StateOptions>() {

        public StateOptions createFromParcel(Parcel in) {
            return StateOptions.values()[in.readInt()];
        }
        public StateOptions[] newArray(int size) {
            return new StateOptions[size];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }
}
