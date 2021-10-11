package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesProcessorImpl implements TextProcessor {

    private static final ProcessingStage stage = ProcessingStage.PREPROCESS;

    @Override
    public String processText(String text) {
        String regex = "\\s+";
        return text.replaceAll(regex, " ");
    }

    @Override
    public ProcessingStage getStage() {
        return stage;
    }
}
