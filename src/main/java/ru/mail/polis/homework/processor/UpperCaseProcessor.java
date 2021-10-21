package ru.mail.polis.homework.processor;

import java.util.Locale;

public class UpperCaseProcessor implements TextProcessor {
    private static final ProcessingStage stage = ProcessingStage.POSTPROCESSING_STAGE;

    @Override
    public String processText(String txt) {
        return txt.toUpperCase();
    }

    @Override
    public ProcessingStage getStage() {
        return stage;
    }
}
