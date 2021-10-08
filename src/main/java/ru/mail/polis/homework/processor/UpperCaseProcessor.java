package ru.mail.polis.homework.processor;

public class UpperCaseProcessor extends AbstractTextProcessor {
    public UpperCaseProcessor() {
        super(ProcessingStage.POSTPROCESSING);
    }

    @Override
    public String process(String text) {
        return text.toUpperCase();
    }
}
