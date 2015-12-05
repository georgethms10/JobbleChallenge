package com.app.jobblechallenge.homepackage;

import com.app.jobblechallenge.tables.CategoryTable;

import java.util.List;

/**
 * Created by sics on 12/2/2015.
 */
public interface categoryResults {

    public void categoryList(boolean result,List<CategoryTable> category);
    public void error(boolean result,String message);
}
