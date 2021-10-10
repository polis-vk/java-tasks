package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesProcessor extends NullSafeTextProcessor implements TextProcessor {
    @Override
    public String processNotNullText(String text) {
        return text.replaceAll("\\s+", " ");
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return ProcessingStage.PRE_PROCESSING;
    }
}
