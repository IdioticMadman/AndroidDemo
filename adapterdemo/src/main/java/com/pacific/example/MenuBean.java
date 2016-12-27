package com.pacific.example;

import java.util.List;

public class MenuBean {
    private int iconResId;
    private String description;
    private List<ExploreBean> exploreBeanList;

    public MenuBean(int iconResId, String description) {
        this.iconResId = iconResId;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getIconResId() {
        return iconResId;
    }

    public List<ExploreBean> getExploreBeanList() {
        return exploreBeanList;
    }

    public void setExploreBeanList(List<ExploreBean> exploreBeanList) {
        this.exploreBeanList = exploreBeanList;
    }
}
