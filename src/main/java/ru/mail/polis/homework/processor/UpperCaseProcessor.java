package ru.mail.polis.homework.processor;

import java.util.Locale;

public class UpperCaseProcessor implements TextProcessor{

    @Override
    public ProcessingStage getProcessingStage() {
        return ProcessingStage.POST_PROCESSING_STAGE;
    }

    @Override
    public String process(String text) {
        return text.toUpperCase(Locale.ROOT);
    }
}
