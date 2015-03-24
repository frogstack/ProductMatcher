package com.frogstackventures.codingchallenge.matching;

import java.util.regex.Pattern;

public class RegexBuilder {
    private static final String WORD_BOUNDARY = "\\b";
    private static final String TOKEN_SEPARATOR = "\\W";
    private static final String OPTIONAL_TOKEN_SEPARATOR = TOKEN_SEPARATOR + "?";
    private static final String MULTIPLE_TOKEN_SEPARATORS = TOKEN_SEPARATOR + "+";

    private String regex;

    public RegexBuilder(String baseString) {
        regex = baseString;
    }

    public Pattern buildPattern() {
        return Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    public String buildString() {
        return regex;
    }

    public RegexBuilder surroundWithWordBoundaries() {
        regex = WORD_BOUNDARY + regex + WORD_BOUNDARY;
        return this;
    }

    public RegexBuilder removeTokenSeparators() {
        regex = regex.replaceAll(TOKEN_SEPARATOR, "");
        return this;
    }

    public RegexBuilder interleaveOptionalTokenSeparators() {
        StringBuilder regexBuilder = new StringBuilder();
        if (regex.length() > 0) {
            regexBuilder.append(regex.charAt(0));
        }
        for (int i = 1; i < regex.length(); i++) {
            regexBuilder.append(OPTIONAL_TOKEN_SEPARATOR);
            regexBuilder.append(regex.charAt(i));
        }
        regex = regexBuilder.toString();
        return this;
    }

    public RegexBuilder makeOptionalGroup() {
        regex = "(" + regex + ")?";
        return this;
    }
    
    public RegexBuilder appendMultipleTokenSeparators() {
        regex = regex + MULTIPLE_TOKEN_SEPARATORS;
        return this;
    }
}
