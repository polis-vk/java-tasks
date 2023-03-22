package ru.mail.polis.homework.analyzer;

public class AdvertisementAnalyzer implements TextAnalyzer {
    String[] links = new String[]{"http:", "https:", "www.", ".com", ".ru"};
    private static final byte priority = 4;

    @Override
    public boolean isTextCorrect(String text) {
        for (String link : links) {
            if (text.contains(link)) {
                return false;
            }
        }
        return true;
    }
    @Override
    public FilterType getFilterType() {
        return FilterType.ADVERTISEMENT;
    }
    @Override
    public byte getPriority() {
        return priority;
    }
}
