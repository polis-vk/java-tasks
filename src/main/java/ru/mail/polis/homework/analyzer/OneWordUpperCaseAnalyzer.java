package ru.mail.polis.homework.analyzer;

import java.util.Objects;

public class OneWordUpperCaseAnalyzer implements TextAnalyzer {

    @Override
    public boolean check(String text) {
        if (Objects.equals(text, "")) {
            return false;
        }
        String[] words = text.split(" ");
        int upperCaseCounter = 0;
        for (String word : words) {
            for (int j = 0; j < word.length(); j++) {
                if (Character.isUpperCase(word.charAt(j))) {
                    upperCaseCounter++;
                }
            }
            if (upperCaseCounter == word.length()) {
                return true;
            }
            upperCaseCounter = 0;
        }
        return false;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.ONE_WORD_UPPER_CASE;
    }
}
