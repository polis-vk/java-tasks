package ru.mail.polis.homework.processor;

import java.util.Locale;

public class UpperCaseProcessor implements TextProcessor {
    private final ProcessingStage STAGE = ProcessingStage.POSTPROCESSING;
    @Override
    public String processText(String text) {
        return text.toUpperCase();
    }

    @Override
    public ProcessingStage getStage() {
        return STAGE;
    }
}
