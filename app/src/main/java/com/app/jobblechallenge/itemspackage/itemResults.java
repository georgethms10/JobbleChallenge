package com.app.jobblechallenge.itemspackage;

import java.util.List;

/**
 * Created by sics on 12/2/2015.
 */
public interface itemResults {
    public void setList(List<ItemsPojo> items);
    public void error(String message);
}
