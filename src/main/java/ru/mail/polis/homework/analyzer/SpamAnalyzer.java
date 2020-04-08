package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] spam;

    public SpamAnalyzer(String[] spam) {
        this.spam = spam.clone();
    }

    @Override
    public boolean problemDetected(String text) {
        for (String s : spam) {
            if (text.contains(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }

    //понятно, что такой метод срежет копипасту, но куда его вставить?
//    public boolean textContainsSthFromArray(String text, String[] array){
//        for (String s : array) {
//            if (text.contains(s)) {
//                return true;
//            }
//        }
//        return false;
//    }
}
