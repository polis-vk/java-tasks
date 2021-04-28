package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class FrequentWordRepetitionAnalyzer implements TextAnalyzer {
    private final String repeatingWord;
    private final int maxFrequency;
    private final static char[] punctuationMarks = {'.', ',', ';', ':', '?', '!', ')', '(', '-'};

    FrequentWordRepetitionAnalyzer(String repeatingWord, int frequency) {
        this.repeatingWord = repeatingWord.toLowerCase();
        this.maxFrequency = frequency;
    }

    @Override
    public FilterType getAnalyzerType() {
        return FilterType.CUSTOM;
    }

    @Override
    public boolean successfullyAnalyzed(String text) {

        long frequency = Arrays.stream(text.toLowerCase().split(" "))
                .map(word -> removePunctuationMarks(word))
                .filter(word -> word.equals(repeatingWord))
                .count();

        return frequency > this.maxFrequency;
    }

    private String removePunctuationMarks(String text) {
        String resultString = text;
        for (char mark : punctuationMarks) {
            resultString = resultString.replace(String.valueOf(mark), "");
        }
        return resultString;
    }
}
