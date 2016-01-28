package edu.rosehulman.lujasaa.comicviewer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lujasaa on 1/13/2016.
 * Holds the issue number and background color for the fragment containing the comic.
 * Needs to be passed to the comic fragment, by the pager adapter
 */
public class ComicWrapper implements Parcelable{

    private int xkcdIssue;
    //the background to display in the pager adapter
    private int color;
    //will use asyc to load the comic later
    private Comic comic;

    public ComicWrapper(int randomCleanIssue, int color) {
        this.xkcdIssue = randomCleanIssue;
        this.color = color;
    }

    protected ComicWrapper(Parcel in) {
        xkcdIssue = in.readInt();
        color = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(xkcdIssue);
        dest.writeInt(color);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ComicWrapper> CREATOR = new Creator<ComicWrapper>() {
        @Override
        public ComicWrapper createFromParcel(Parcel in) {
            return new ComicWrapper(in);
        }

        @Override
        public ComicWrapper[] newArray(int size) {
            return new ComicWrapper[size];
        }
    };

    public int getXkcdIssue() {
        return xkcdIssue;
    }

    public int getColor() {
        return color;
    }


    public Comic getComic() {
        return comic;
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }
}
