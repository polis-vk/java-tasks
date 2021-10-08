package ru.mail.polis.homework.processor;

public class TrimProcessor implements TextProcessor {

    private int maxLength;

    public TrimProcessor(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return ProcessingStage.POSTPROCESSING;
    }

    @Override
    public void execute(String str) {
        if (str.length() > maxLength) {
            str = str.substring(0, maxLength);
        }
    }
}
