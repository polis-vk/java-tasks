package ru.mail.polis.homework.processor;

public class TrimProcessor implements TextProcessor {
    int maxLength;
    ProcessingStage stage;
    public TrimProcessor(int maxLength) {
        this.stage =  ProcessingStage.POSTPROCESSING;
        this.maxLength = maxLength;
    }

    @Override
    public String process(String text) {
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength);
    }

    @Override
    public int getStageIndex() {
        return stage.ordinal();
    }
}
