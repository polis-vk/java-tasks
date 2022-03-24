package ru.mail.polis.homework.analyzer;

import java.io.UnsupportedEncodingException;

public class TooLongAnalyzer implements TextAnalyzer {

    private long maxLength;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean examine(String text){
        String str = null;
        try {
            str = new String(text.getBytes(),"utf-8");

            if (str.length() > maxLength) {
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_LONG;
    }

}
