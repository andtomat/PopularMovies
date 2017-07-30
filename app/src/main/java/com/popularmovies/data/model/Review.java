
package com.popularmovies.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Immutable model class for a Videos.
 */
public final class Review implements Parcelable {

    public String id;
    public String author;
    public String content;
    public String url;

    Review(Parcel in) {
        this.id = in.readString();
        this.author = in.readString();
        this.content = in.readString();
        this.url = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(author);
        parcel.writeString(content);
        parcel.writeString(url);
    }

    public static final Creator CREATOR
            = new Creator() {
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}
