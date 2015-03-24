package com.frogstackventures.codingchallenge.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.frogstackventures.codingchallenge.matching.RegexBuilder;

public class Product {
    private static final int MAXIMUM_DEPTH_OF_TITLE_MATCH = 20;
    
    // Using a non-standard variable name here makes Gson happy.
    private String product_name;
    private String manufacturer;
    private String family;
    private String model;

    private Pattern manufacturerPattern;
    private Pattern productPattern;

    public Product() {
    }

    protected Product(String manufacturer, String family, String model) {
        this.manufacturer = manufacturer;
        this.family = family;
        this.model = model;
    }

    public String getProductName() {
        return product_name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public boolean matches(Listing listing) {
        Matcher productMatcher = getProductPattern().matcher(listing.getTitle());
        boolean match = productMatcher.find();
        if (!match) {
            return false;
        }
        return productMatcher.start() < Math.max(manufacturer.length() * 3, MAXIMUM_DEPTH_OF_TITLE_MATCH);
    }

    public boolean manufacturerMatches(Listing listing) {
        Matcher manufacturerMatcher = getManufacturerPattern().matcher(listing.getManufacturer());
        if (manufacturerMatcher.find()) {
            return true;
        }
        Matcher titleMatcher = getManufacturerPattern().matcher(listing.getTitle());
        if (titleMatcher.find() && titleMatcher.start() < manufacturer.length()) {
            return true;
        }
        return false;
    }

    private Pattern getManufacturerPattern() {
        if (manufacturerPattern == null) {
            buildManufacturerPattern();
        }
        return manufacturerPattern;
    }

    private Pattern getProductPattern() {
        if (productPattern == null) {
            buildProductPattern();
        }
        return productPattern;
    }

    private void buildManufacturerPattern() {
        manufacturerPattern = new RegexBuilder(manufacturer)
                .removeTokenSeparators()
                .interleaveOptionalTokenSeparators()
                .surroundWithWordBoundaries()
                .buildPattern();
    }

    private void buildProductPattern() {
        String productRegex;
        String modelRegex = new RegexBuilder(model)
                .removeTokenSeparators()
                .interleaveOptionalTokenSeparators()
                .surroundWithWordBoundaries()
                .buildString();

        if (family != null) {
            String familyRegex = new RegexBuilder(family)
                    .removeTokenSeparators()
                    .interleaveOptionalTokenSeparators()
                    .surroundWithWordBoundaries()
                    .appendMultipleTokenSeparators()
                    .makeOptionalGroup()
                    .buildString();

            productRegex = familyRegex + modelRegex;
        }
        else {
            productRegex = modelRegex;
        }

        productPattern = new RegexBuilder(productRegex).buildPattern();
    }
}
