package ru.mail.polis.homework.processor;

public class UpperCaseProcessor extends NullSafeTextProcessor implements TextProcessor {
    @Override
    public String processNotNullText(String text) {
        return text.toUpperCase();
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return ProcessingStage.PROCESSING;
    }
}
