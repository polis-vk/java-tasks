package ru.mail.polis.homework.analyzer;

public class AdvertisementAnalyzer implements TextAnalyzer {
    String[] links = new String[]{"http:", "https:", "www.", ".com", ".ru"};
    private static final FilterType filterType = FilterType.ADVERTISEMENT;

    @Override
    public boolean isTextCorrect(String text) {
        return TextAnalyzer.find(text, links);
    }

    @Override
    public FilterType getFilterType() {
        return filterType;
    }
}
