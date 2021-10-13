package ru.mail.polis.homework.processor;

import java.util.Locale;

public class CapsProcessor implements TextProcessor {
    private final ProcessingStage stage = ProcessingStage.POSTPROCESSING;

    @Override
    public String execute(String text) {
        return text.toUpperCase(Locale.ROOT);
    }

    @Override
    public ProcessingStage getStage() {
        return stage;
    }
}
