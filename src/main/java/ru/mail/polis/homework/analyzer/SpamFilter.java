package ru.mail.polis.homework.analyzer;



public class SpamFilter implements TextAnalyzer {
    String[] SpamList;

    SpamFilter(String[] SpamArray) {
        if (SpamArray != null) {
            this.SpamList = SpamArray.clone();
        }
    }

    @Override
    public FilterType analyze(String str) {
        if (str == null || str.isEmpty()) {
            return FilterType.GOOD;
        }
        String subst = String.valueOf(str);
        subst.toLowerCase();
        for (String x : SpamList) {
            if (subst.contains(x)) {
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }
}
