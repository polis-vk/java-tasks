package ru.mail.polis.homework.analyzer;

import com.sun.nio.sctp.SctpSocketOption;
import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SpamAnalyzer implements TextAnalyzer {
    private String[] spam;
    SpamAnalyzer(String[] spam)
    {
        this.spam = Arrays.copyOf(spam, spam.length);
    }
    @Override
    public FilterType getFilter() {
        return FilterType.SPAM;
    }

    @Override
    public boolean textAnalyzer(String text) {
        for(var a : spam)
        {
            if(a.equals(text)) return false;
        }
        return true;
    }
}
