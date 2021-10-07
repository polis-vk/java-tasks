package ru.mail.polis.homework.processor;

import java.util.Locale;

public class UpperCaseProcessor implements TextProcessor {

    private final ProcessingStage STAGE = ProcessingStage.POSTPROCESSING;

    public String processText(String text) {
        return text.toUpperCase(Locale.ROOT);
    }

    public ProcessingStage getStage() {
        return STAGE;
    }
}
