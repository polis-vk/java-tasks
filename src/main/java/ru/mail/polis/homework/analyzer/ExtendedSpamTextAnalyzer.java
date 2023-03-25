package ru.mail.polis.homework.analyzer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Этот анализатор может определять "плохие" слова несмотря на наличие посторонних символов,
 * например знаков препинания
 */

public class ExtendedSpamTextAnalyzer implements TextAnalyzer {

    private final Set<String> badWords = new HashSet<>();
    private final byte priority = 3;

    public ExtendedSpamTextAnalyzer(String[] badWords) {
        this.badWords.addAll(Arrays.asList(badWords));
    }

    @Override
    public FilterType analyze(String text) {
        String[] wordsInText = text.split(" ");
        String[] cleanWordsInText = beautify(wordsInText);
        for (String wordInText : cleanWordsInText) {
            if (badWords.contains(wordInText)) {
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public byte getPriority() {
        return priority;
    }

    private String[] beautify(String[] wordsInText) {
        String[] result = new String[wordsInText.length];

        for (int i = 0; i < wordsInText.length; i++) {
            String lowerCasedWord = wordsInText[i].toLowerCase();
            String resultString = trimSpecialSymbolsRight(trimSpecialSymbolsLeft(lowerCasedWord));
            result[i] = resultString;
        }
        return result;
    }

    private String trimSpecialSymbolsRight(String string) {
        for (int i = string.length() - 1; i >= 0; i--) {
            if (Character.isAlphabetic(string.charAt(i))) {
                return string.substring(0, i + 1);
            }
        }
        return string;
    }

    private String trimSpecialSymbolsLeft(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (Character.isAlphabetic(string.charAt(i))) {
                return string.substring(i);
            }
        }
        return string;
    }

}
