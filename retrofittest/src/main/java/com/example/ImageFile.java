package com.example;

import java.io.Serializable;

/**
 * Created by robert on 2016/11/28.
 */
public class ImageFile implements Serializable {
    private int tag = 0;
    private String thumbnail;
    private String picture;
    private boolean ischecked;

    public ImageFile(int tag, String thumbnail, String picture) {
        super();
        this.tag = tag;
        this.thumbnail = thumbnail;
        this.picture = picture;
    }

    public boolean isIschecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }

    public ImageFile() {
        super();
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

