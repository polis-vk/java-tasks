package ru.mail.polis.homework.processor;

import java.util.Locale;

public class UpperCaseProcessor implements TextProcessor {
    ProcessingStage stage;
    public UpperCaseProcessor() {
        this.stage = ProcessingStage.POSTPROCESSING;
    }

    @Override
    public String process(String text) {
        return text.toUpperCase();
    }

    @Override
    public int getStageIndex() {
        return stage.ordinal();
    }
}
