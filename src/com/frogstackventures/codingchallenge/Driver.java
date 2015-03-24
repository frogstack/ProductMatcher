package com.frogstackventures.codingchallenge;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.frogstackventures.codingchallenge.data.Listing;
import com.frogstackventures.codingchallenge.data.Product;
import com.frogstackventures.codingchallenge.data.Result;
import com.google.gson.Gson;

public class Driver {

    public static void main(String[] args) throws IOException {
        String productFileName = args.length > 0 ? args[0] : "products.txt";
        String listingFileName = args.length > 1 ? args[1] : "listings.txt";
        String resultsFileName = args.length > 2 ? args[2] : "results.txt";
        
        List<Product> products = readProductsFromFile(productFileName);
        List<Listing> listings = readListingsFromFile(listingFileName);

        Collection<Result> results = new Matcher().match(products, listings);

        outputResults(resultsFileName, results);

        System.out.println("Done. Results are in " + resultsFileName);
    }

    private static List<Product> readProductsFromFile(String filename) throws IOException {
        Gson gson = new Gson();
        List<Product> products = new ArrayList<Product>();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            while ((line = reader.readLine()) != null) {
                products.add(gson.fromJson(line, Product.class));
            }
        }
        catch (IOException e) {
            System.out.println("Aborting because there was a problem reading the file " + filename + ": " + e.getMessage());
            throw e;
        }
        return products;
    }

    private static List<Listing> readListingsFromFile(String filename) throws IOException {
        Gson gson = new Gson();
        List<Listing> listings = new ArrayList<Listing>();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            while ((line = reader.readLine()) != null) {
                listings.add(gson.fromJson(line, Listing.class));
            }
        }
        catch (IOException e) {
            System.out.println("Aborting because there was a problem reading the file " + filename + ": " + e.getMessage());
            throw e;
        }
        return listings;
    }

    private static void outputResults(String filename, Collection<Result> results) {
        Gson gson = new Gson();
        try {
            PrintWriter writer = new PrintWriter(filename, "utf-8");
            for (Result result : results) {
                writer.println(gson.toJson(result));
            }
            writer.close();
        }
        catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
