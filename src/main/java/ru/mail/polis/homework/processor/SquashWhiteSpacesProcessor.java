package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesProcessor extends Processor implements TextProcessor {

    public SquashWhiteSpacesProcessor() {
        super(ProcessingStage.PRE_PROCESSING);
    }

    @Override
    public String process(String data) {
        return data.replaceAll("\\s+", " ");
    }
}
