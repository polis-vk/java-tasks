package ru.mail.polis.homework.processor;

import java.util.Locale;

public class UpperCaseProcessor implements TextProcessor {
    private final ProcessingStage stage;

    public UpperCaseProcessor(ProcessingStage stage) {
        this.stage = stage;
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return stage;
    }

    @Override
    public String process(String text) {
        return text.toUpperCase(Locale.ROOT);
    }
}
