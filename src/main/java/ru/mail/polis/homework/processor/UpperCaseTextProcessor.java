package ru.mail.polis.homework.processor;

import java.util.Locale;

// Stage:POSTPROCESSING
public class UpperCaseTextProcessor implements TextProcessor {

    @Override
    public int ordinal() {
        return 2;
    }

    @Override
    public String processText(String text) {
        return text.toUpperCase(Locale.ROOT);
    }

}
