package ru.mail.polis.homework.analyzer;

public class TooManySlashAnalyzer extends TooLongAnalyzer implements TextAnalyzer {

    private final long maxCountSlash;

    public TooManySlashAnalyzer(long length, long count) {
        super(length);
        this.maxCountSlash = count;
    }

    @Override
    public boolean analyzer(String text) {
        long count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '/') {
                count++;
            }
        }
        return count > maxCountSlash && text.length() < maxLength;
    }

    public FilterType getType() {
        return FilterType.CUSTOM;
    }
}
