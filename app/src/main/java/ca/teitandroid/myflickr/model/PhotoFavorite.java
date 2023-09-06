package ca.teitandroid.myflickr.model;

import java.io.Serializable;

public class PhotoFavorite implements Serializable {
    private int photoID;
    private String authorPhoto;
    private String titlePhoto;
    private String descriptionPhoto;
    private String viewsPhoto;
    private String dateTakenPhoto;
    private String urlPhoto;

    public PhotoFavorite(int photoID, String authorPhoto, String titlePhoto, String descriptionPhoto, String viewsPhoto, String dateTakenPhoto, String urlPhoto) {
        this.photoID = photoID;
        this.authorPhoto = authorPhoto;
        this.titlePhoto = titlePhoto;
        this.descriptionPhoto = descriptionPhoto;
        this.viewsPhoto = viewsPhoto;
        this.dateTakenPhoto = dateTakenPhoto;
        this.urlPhoto = urlPhoto;
    }
    public PhotoFavorite(){

    }

    public int getPhotoID() {
        return photoID;
    }

    public void setPhotoID(int photoID) {
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
        return "PhotoFavorite{" +
                "photoID='" + photoID + '\'' +
                ", authorPhoto='" + authorPhoto + '\'' +
                ", titlePhoto='" + titlePhoto + '\'' +
                ", descriptionPhoto='" + descriptionPhoto + '\'' +
                ", viewsPhoto='" + viewsPhoto + '\'' +
                ", dateTakenPhoto='" + dateTakenPhoto + '\'' +
                ", urlPhoto='" + urlPhoto + '\'' +
                '}';
    }
}
