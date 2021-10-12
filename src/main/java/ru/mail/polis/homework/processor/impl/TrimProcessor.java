package ru.mail.polis.homework.processor.impl;

import ru.mail.polis.homework.processor.ProcessingStage;
import ru.mail.polis.homework.processor.TextProcessor;

public class TrimProcessor implements TextProcessor {
    private final int maxLength;

    public TrimProcessor(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public String process(String text) {
        if (maxLength > text.length()) {
            return text;
        }
        return text.substring(0, maxLength);
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return ProcessingStage.POSTPROCESSING;
    }
}
