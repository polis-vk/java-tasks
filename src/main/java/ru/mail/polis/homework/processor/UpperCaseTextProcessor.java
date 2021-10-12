package ru.mail.polis.homework.processor;

import java.util.Locale;

public class UpperCaseTextProcessor implements TextProcessor {

    @Override
    public ProcessingStage getProcessingStage() {
        return ProcessingStage.POSTPROCESSING;
    }

    @Override
    public String processText(String text) {
        return text == null ? null : text.toUpperCase(Locale.ROOT);
    }

}
