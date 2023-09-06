package ca.teitandroid.myflickr.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Photo implements  Parcelable {
    private int photoID;
    private String authorPhoto;
    private String titlePhoto;
    private String descriptionPhoto;
    private String viewsPhoto;
    private String dateTakenPhoto;
    private String urlPhoto;
    private Description description;

    public Photo(){
    }

    public Photo(int photoID, String authorPhoto, String titlePhoto, String descriptionPhoto, String viewsPhoto, String dateTakenPhoto, String urlPhoto) {
        this.photoID = photoID;
        this.authorPhoto = authorPhoto;
        this.titlePhoto = titlePhoto;
        this.descriptionPhoto = descriptionPhoto;
        this.viewsPhoto = viewsPhoto;
        this.dateTakenPhoto = dateTakenPhoto;
        this.urlPhoto = urlPhoto;
    }

    public int getPhotoID() {
        return photoID;
    }

    public void setPhotoID(int  photoID) {
        this.photoID = photoID;
    }

    public String getAuthorPhoto() {
        return authorPhoto;
    }

    public void setAuthorPhoto(String authorPhoto) {
        this.authorPhoto = authorPhoto;
    }

    public String getTitlePhoto() {
        return titlePhoto;
    }

    public void setTitlePhoto(String titlePhoto) {
        this.titlePhoto = titlePhoto;
    }

    public String getDescriptionPhoto() {
        return descriptionPhoto;
    }

    public void setDescriptionPhoto(String descriptionPhoto) {
        this.descriptionPhoto = descriptionPhoto;
    }

    public String getViewsPhoto() {
        return viewsPhoto;
    }

    public void setViewsPhoto(String viewsPhoto) {
        this.viewsPhoto = viewsPhoto;
    }

    public String getDateTakenPhoto() {
        return dateTakenPhoto;
    }

    public void setDateTakenPhoto(String dateTakenPhoto) {
        this.dateTakenPhoto = dateTakenPhoto;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "photoID=" + photoID +
                ", authorPhoto='" + authorPhoto + '\'' +
                ", titlePhoto='" + titlePhoto + '\'' +
                ", descriptionPhoto='" + descriptionPhoto + '\'' +
                ", viewsPhoto='" + viewsPhoto + '\'' +
                ", dateTakenPhoto='" + dateTakenPhoto + '\'' +
                ", urlPhoto='" + urlPhoto + '\'' +
                '}';
    }

    protected Photo(Parcel in) {
        photoID = in.readInt();
        authorPhoto = in.readString();
        titlePhoto = in.readString();
        descriptionPhoto = in.readString();
        viewsPhoto = in.readString();
        dateTakenPhoto = in.readString();
        urlPhoto = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(photoID);
        dest.writeString(authorPhoto);
        dest.writeString(titlePhoto);
        dest.writeString(descriptionPhoto);
        dest.writeString(viewsPhoto);
        dest.writeString(dateTakenPhoto);
        dest.writeString(urlPhoto);
    }
    public static class Description {
        private String _content;

        public String getContent() {
            return _content;
        }
    }
    public String getDescription() {
        return description != null ? description.getContent() : "";
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

}
