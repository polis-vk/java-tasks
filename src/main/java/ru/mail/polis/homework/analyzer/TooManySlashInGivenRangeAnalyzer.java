package ru.mail.polis.homework.analyzer;

public class TooManySlashInGivenRangeAnalyzer extends TooLongAnalyzer implements TextAnalyzer {

    private final long maxCountSlash;

    public TooManySlashInGivenRangeAnalyzer(long length, long count) {
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
        return FilterType.TOO_MANY_SLASH;
    }
}
