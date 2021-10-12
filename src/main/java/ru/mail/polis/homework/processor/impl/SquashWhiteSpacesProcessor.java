package ru.mail.polis.homework.processor.impl;

import ru.mail.polis.homework.processor.ProcessingStage;
import ru.mail.polis.homework.processor.TextProcessor;

public class SquashWhiteSpacesProcessor implements TextProcessor {

    @Override
    public String process(String text) {
        return text.replaceAll("\\s+", " ");
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return ProcessingStage.PREPROCESSING;
    }
}
