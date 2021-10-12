package ru.mail.polis.homework.processor.impl;

import ru.mail.polis.homework.processor.ProcessingStage;
import ru.mail.polis.homework.processor.TextProcessor;

public class UpperCaseProcessor implements TextProcessor {
    @Override
    public String process(String text) {
        return text.toUpperCase();
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return ProcessingStage.POSTPROCESSING;
    }
}
