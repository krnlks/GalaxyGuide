package com.util;

import java.util.Map;
import java.util.function.Predicate;

public class AlienTermPredicate implements Predicate {
    Map<String, String> map;

    public AlienTermPredicate(Map<String, String> termsToNumerals) {
        map = termsToNumerals;
    }

    @Override
    public boolean test(String term) {
        return map.containsKey(term);
    }
}