package ru.mail.polis.homework.processor;

public class UpperCaseProcessor implements TextProcessor{
    private static final ProcessingStage stage = ProcessingStage.POST_PROCESSING;

    @Override
    public String process(String text) {
        return text.toUpperCase();
    }

    @Override
    public int getStage() {
        return stage.getStage();
    }
}
