package com.frogstackventures.codingchallenge.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProductTest {

    private Product product;

    @Test
    public void testManufacturerMatchesDifferentCapitalization() {
        product = new Product("Olympus", null, "PEN E-PL2");
        assertTrue(product.manufacturerMatches(new Listing("OLYMPUS", "")));
    }

    @Test
    public void testMatchesMisspelledManufacturerIfPresentEarlyInListingTitle() {
        product = new Product("Canon", null, "");
        assertTrue(product.manufacturerMatches(new Listing("Canoon", "Canon Rebel XS Digital Camera")));
    }

    @Test
    public void testDoesNotMatchManufacturerLateInTitle() {
        product = new Product("Leica", "D-LUX", "5");
        assertFalse(product.manufacturerMatches(new Listing("Minox", "Minox DCC-Leica M 3 5,0 MP.")));
    }

    @Test
    public void testDifferentManufacturersDoNotMatch() {
        product = new Product("Fujifilm", null, "");
        assertFalse(product.manufacturerMatches(new Listing("Olympus", "PEN E-PL2")));
    }

    @Test
    public void testManufacturerIsMatchedWithDifferentSpacing() {
        product = new Product("Fujifilm", null, "");
        assertTrue(product.manufacturerMatches(new Listing("Fuji film", "FINEPIX F50FD")));
    }

    @Test
    public void testModelWithNoFamilyMatches() {
        product = new Product("Olympus", null, "PEN E-PL2");
        assertTrue(product.matches(new Listing("OLYMPUS", "PEN E-PL2")));
    }

    @Test
    public void testWrongModelDoesNotMatch() {
        product = new Product("Olympus", null, "C-2000");
        assertFalse(product.matches(new Listing("OLYMPUS", "PEN E-PL2")));
    }

    @Test
    public void testTitleWithMissingNonWordModelCharactersMatches() {
        product = new Product("Olympus", null, "PEN E-PL2");
        assertTrue(product.matches(new Listing("OLYMPUS", "PEN EPL2")));
    }

    @Test
    public void testTitleWithExtraNonWordModelCharactersMatches() {
        product = new Product("Samsung", null, "TL240");
        assertTrue(product.matches(new Listing("Samsung", "TL-240")));
    }

    @Test
    public void testTitleWithoutFamilyInformationMatches() {
        product = new Product("Nikon", "Coolpix", "S640");
        assertTrue(product.matches(new Listing("Nikon", "Nikon - S640")));
    }

    @Test
    public void testTitleWithFamilyInformationMatches() {
        product = new Product("Nikon", "Coolpix", "S640");
        assertTrue(product.matches(new Listing("Nikon", "Nikon Coolpix S640")));
    }

    @Test
    public void testTitleWithExtraSpacingInFamilyMatches() {
        product = new Product("Nikon", "Coolpix", "S640");
        assertTrue(product.matches(new Listing("Nikon", "Cool-pix S640")));
    }

    @Test
    public void testTitleWithMissingSpacingInFamilyMatches() {
        product = new Product("Nikon", "Cool-pix", "S640");
        assertTrue(product.matches(new Listing("Nikon", "Coolpix S640")));
    }

    @Test
    public void testSpacesInFamilyAreRemoved() {
        product = new Product("Sony", "Cybershot ", "WX10");
        assertTrue(product.matches(new Listing("Sony", "Sony Cyber-shot WX10")));
    }

    @Test
    public void testSemanticDataBetweenFamilyAndModelBreaksMatch() {
        product = new Product("Sony", "Cyber shot", "WX7");
        assertFalse(product.matches(new Listing("Sony", "SONY Cyber-shot DSC-WX7")));
    }

    @Test
    public void testFamilyAndModelMentionsDeepInTitleAreNotMatched() {
        product = new Product("Canon", "EOS", "7D");
        assertFalse(product
                .matches(new Listing(
                        "Canon",
                        "32GB Supreme Prime Time Acessory Package For The Canon EOS 5D EOS 7D Digital Slr Kit Includes 32Gb High Speed Memory Card, 2 Extended Life Batteries, Rapid AC/DC Charger, Digital Flash, 0.50x Wide Angle Lens, 2X Telephoto Lens, Filter Kit, 4 Piece Close Up Lens Kit, Flower Lens Hood, Deluxe Carrying Case, 72 Inch Professional tripod, Professional SLR Hand Strap, Flash Diffuser, + More All Lenses Will Fit The Following Canon EF 85mm, EF-S 18-200mm, EF 28-135mm, EF-S 15-85mm")));
    }

    @Test
    public void testAmbigiousModelName() {
        product = new Product("Leica", "D-LUX", "5");
        assertFalse(product.matches(new Listing("Panasonic",
                "Panasonic DMC-L10 10.1MP Digital SLR Camera with Leica D Vario-Elmar 14-50mm f/3.8-5.6 Mega OIS Lens")));
    }

    @Test
    public void testAmbiguiousModelNameInShortListing() {
        product = new Product("Leica", "D-LUX", "5");
        assertFalse(product.matches(new Listing("Minox", "Minox DCC-Leica M 3 5,0 MP.")));
    }

}
