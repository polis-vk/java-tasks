package ru.mail.polis.homework.analyzer;

public class SpaceLengthAnalyzer implements TextAnalyzer {

    @Override
    public FilterType analyze(String text) {
        int countRepeatSpace = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                countRepeatSpace++;
            } else {
                countRepeatSpace = 0;
            }

            if (countRepeatSpace == 2) {
                return FilterType.SPACE_LENGTH;
            }
        }

        return FilterType.GOOD;
    }
}
