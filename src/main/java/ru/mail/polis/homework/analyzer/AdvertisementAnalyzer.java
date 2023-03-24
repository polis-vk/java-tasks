package ru.mail.polis.homework.analyzer;

public class AdvertisementAnalyzer implements TextAnalyzer {
    private static final String[] LINKS = new String[]{"http:", "https:", "www.", ".com", ".ru"};
    private static final FilterType FILTER_TYPE = FilterType.ADVERTISEMENT;

    @Override
    public boolean isTextCorrect(String text) {
        return TextAnalyzer.find(text, LINKS);
    }

    @Override
    public FilterType getFilterType() {
        return FILTER_TYPE;
    }
}
