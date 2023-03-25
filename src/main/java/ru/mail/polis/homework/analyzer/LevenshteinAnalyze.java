package ru.mail.polis.homework.analyzer;

public class LevenshteinAnalyze implements TextAnalyzer {


    String equalString;

    LevenshteinAnalyze(String str) {
        this.equalString = str;
    }

    @Override
    public FilterType analyze(String str) {

        int dynamicLenght = str.length() + 1;
        int dynamicWidth = this.equalString.length() + 1;
        long[][] D = new long[dynamicLenght][dynamicWidth];
        for (int i = 0; i < dynamicWidth; i++) {
            D[0][i] = i;
        }
        for (int i = 0; i < dynamicLenght; i++) {
            D[i][0] = i;
        }
        for (int i = 1; i < dynamicLenght; i++) {
            for (int j = 1; j < dynamicWidth; j++) {
                if (this.equalString.charAt(j - 1) == str.charAt(i - 1)) {
                    D[i][j] = D[i - 1][j - 1];
                } else {
                    D[i][j] = Math.min(D[i - 1][j], Math.min(D[i][j - 1], D[i - 1][j - 1])) + 1;
                }
            }
        }
        if ((double) D[dynamicLenght - 1][dynamicWidth - 1] / Math.max(this.equalString.length(), str.length()) > 0.2) {
            return FilterType.NO_SIMILAR_STRING;
        }
        return FilterType.GOOD;
    }


}
