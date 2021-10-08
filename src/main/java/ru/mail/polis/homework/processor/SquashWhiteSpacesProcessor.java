package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesProcessor extends Processor implements TextProcessor {

    {
        processingStage = ProcessingStage.PRE_PROCESSING;
    }

    @Override
    public String handle(String data) {
        return data.replaceAll("\\s+", " ");
    }
}
