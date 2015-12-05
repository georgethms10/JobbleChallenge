package com.app.jobblechallenge.tables;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by sics on 12/2/2015.
 */
@ParseClassName("CategoryTable")
public class CategoryTable extends ParseObject {

    public String categoryName;
    public String categoryId;
    public String email;

    public String getEmail() {
        return getString("email");
    }

    public void setEmail(String email) {
        put("email", email);
    }

    public CategoryTable() {
    }

    public CategoryTable(String categoryName,String categoryId,String email) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.email=email;

    }

    public String getCategoryName() {
        return getString("categoryName");
    }

    public void setCategoryName(String categoryName) {
        put("categoryName", categoryName);
    }

    public String getCategoryId() {
        return getString("categoryId");
    }

    public void setCategoryId(String categoryId) {
        put("categoryId",categoryId);
    }
}
