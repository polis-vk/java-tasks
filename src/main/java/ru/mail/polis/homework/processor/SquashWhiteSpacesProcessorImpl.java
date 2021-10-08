package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesProcessorImpl implements TextProcessor {

    private final ProcessingStage stage = ProcessingStage.PREPROCESS;

    @Override
    public String execute(String text) {
        String regex = "\\s+";
        return text.replaceAll(regex, " ");
    }

    @Override
    public ProcessingStage getStage() {
        return stage;
    }
}
