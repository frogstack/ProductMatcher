package com.frogstackventures.codingchallenge.data;

import java.util.ArrayList;
import java.util.List;

public class Result {
    // Using a non-standard variable name here makes the result serialize correctly with Gson.
    private String product_name;
    private List<Listing> listings;

    public Result(String productName) {
        this.product_name = productName;
        this.listings = new ArrayList<Listing>();
    }

    public void addListing(Listing listing) {
        listings.add(listing);
    }
}
