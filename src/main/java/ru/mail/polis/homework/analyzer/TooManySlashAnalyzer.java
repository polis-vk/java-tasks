package ru.mail.polis.homework.analyzer;

public class TooManySlashAnalyzer extends TooLongAnalyzer implements TextAnalyzer {

    final private long maxCountSlash;

    public TooManySlashAnalyzer(long length, long count) {
        super(length);
        this.maxCountSlash = count;
    }

    @Override
    public boolean analyzer(String text) {
        long count = 0;
        for (char itText : text.toCharArray()) {
            if (itText == '/') {
                count++;
            }
        }
        return count > maxCountSlash && text.length() < maxLength;
    }

    public FilterType getType() {
        return FilterType.CUSTOM;
    }
}
