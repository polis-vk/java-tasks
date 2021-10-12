package ru.mail.polis.homework.processor;

public class TrimTextProcessor implements TextProcessor {
    private int maxLength;

    public TrimTextProcessor(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return ProcessingStage.POSTPROCESSING;
    }

    @Override
    public String processText(String text) {
        if (text == null) {
            return null;
        }
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength);
    }
}
