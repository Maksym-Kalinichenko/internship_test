package com.example.level_3_middle.classes;
public class GetData {
    private String urlImg;
    private boolean likeCheck;

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public void setLikeCheck(boolean likeCheck) {
        this.likeCheck = likeCheck;
    }

    public GetData(String urlImg, boolean likeCheck) {
        this.urlImg = urlImg;
        this.likeCheck = likeCheck;
    }

    public String getImageUrl() {
        return urlImg;
    }

    public boolean getLike() {
        return likeCheck;
    }

}

