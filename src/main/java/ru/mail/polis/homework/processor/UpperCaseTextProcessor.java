package ru.mail.polis.homework.processor;

import java.util.Locale;

public class UpperCaseTextProcessor implements TextProcessor {

    private static final ProcessingStage PROCESSING_STAGE = ProcessingStage.POSTPROCESSING;

    @Override
    public String process(String text) {
        return text.toUpperCase(Locale.ROOT);
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return PROCESSING_STAGE;
    }
}
