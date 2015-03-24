package com.frogstackventures.codingchallenge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.frogstackventures.codingchallenge.data.Listing;
import com.frogstackventures.codingchallenge.data.Product;
import com.frogstackventures.codingchallenge.data.Result;

public class Matcher {

    public Collection<Result> match(List<Product> products, List<Listing> listings) {
        Map<String, List<Product>> productsByManufacturer = bucketizeProductsByManufacturer(products);

        return matchListings(productsByManufacturer, listings);
    }

    private Map<String, List<Product>> bucketizeProductsByManufacturer(List<Product> products) {
        Map<String, List<Product>> productsByManufacturer = new HashMap<String, List<Product>>();
        for (Product product : products) {
            String manufacturer = product.getManufacturer();
            if (!productsByManufacturer.containsKey(manufacturer)) {
                productsByManufacturer.put(manufacturer, new ArrayList<Product>());
            }
            productsByManufacturer.get(manufacturer).add(product);
        }
        return productsByManufacturer;
    }

    private Collection<Result> matchListings(Map<String, List<Product>> productsByManufacturer, List<Listing> listings) {
        Map<String, Result> results = new HashMap<String, Result>();

        for (Listing listing : listings) {
            List<Product> matchingProducts = matchListingToProducts(productsByManufacturer, listing);
            if (matchingProducts.size() == 1) {
                String productName = matchingProducts.get(0).getProductName();
                if (!results.containsKey(productName)) {
                    results.put(productName, new Result(productName));
                }
                results.get(productName).addListing(listing);
            }
        }
        return results.values();
    }

    private List<Product> matchListingToProducts(Map<String, List<Product>> productsByManufacturer, Listing listing) {
        List<Product> matchingProducts = new ArrayList<Product>();
        for (List<Product> productList : productsByManufacturer.values()) {
            if (listingMatchesManufacturersInBucket(listing, productList)) {
                for (Product product : productList) {
                    if (product.matches(listing)) {
                        matchingProducts.add(product);
                    }
                }
            }
        }
        return matchingProducts;
    }

    private boolean listingMatchesManufacturersInBucket(Listing listing, List<Product> productList) {
        return productList.get(0).manufacturerMatches(listing);
    }

}
