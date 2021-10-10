package ru.mail.polis.homework.processor;

public class TrimProcessor extends NullSafeTextProcessor implements TextProcessor{
    final int maxLength;

    public TrimProcessor(int maxLength) {
        this.maxLength = maxLength;
    }
    @Override
    public String processNotNullText(String text) {
        return text.substring(0, Math.min(maxLength, text.length()));
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return ProcessingStage.POST_PROCESSING;
    }
}
