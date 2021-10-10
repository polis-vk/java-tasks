package ru.mail.polis.homework.processor;

public class UpperCaseProcessor extends Processor implements TextProcessor {

    public UpperCaseProcessor() {
        super(ProcessingStage.POST_PROCESSING);
    }

    @Override
    public String process(String data) {
        return data.toUpperCase();
    }
}
