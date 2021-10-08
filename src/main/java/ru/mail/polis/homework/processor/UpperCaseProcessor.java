package ru.mail.polis.homework.processor;

public class UpperCaseProcessor extends Processor implements TextProcessor {

    {
        processingStage = ProcessingStage.POST_PROCESSING;
    }

    @Override
    public String handle(String data) {
        return data.toUpperCase();
    }
}
