package ru.mail.polis.homework.analyzer;

public class AdvertisementAnalyzer extends SpamAnalyzer implements TextAnalyzer {
    private static final String[] LINKS = new String[]{"http:", "https:", "www.", ".com", ".ru"};

    public AdvertisementAnalyzer() {
        super(LINKS);
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.ADVERTISEMENT;
    }
}
