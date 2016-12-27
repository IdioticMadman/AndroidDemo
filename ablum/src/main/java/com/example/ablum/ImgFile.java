package com.example.ablum;

/**
 * Created by robert on 2016/11/15.
 */

public class ImgFile {
    private int tag = 0;
    private String thumbnail;
    private String picture;
    private boolean isChecked;

    public ImgFile() {
        super();
    }

    public ImgFile(int tag, String thumbnail, String picture) {
        super();
        this.tag = tag;
        this.thumbnail = thumbnail;
        this.picture = picture;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
