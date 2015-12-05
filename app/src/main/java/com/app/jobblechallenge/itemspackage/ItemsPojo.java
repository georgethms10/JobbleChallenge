package com.app.jobblechallenge.itemspackage;

/**
 * Created by sics on 12/2/2015.
 */
public class ItemsPojo {


    private String CategoryId;
    private String name;
    private String image;
    private String description;

    public ItemsPojo()
    {

    }

    public ItemsPojo(String categoryId, String name, String image, String description) {
        CategoryId = categoryId;
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
