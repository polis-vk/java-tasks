package ru.mail.polis.homework.processor.impl;

import ru.mail.polis.homework.processor.ProcessingStage;
import ru.mail.polis.homework.processor.TextProcessor;

public class ReplaceFirstProcessor implements TextProcessor {
    private final String regexp;
    private final String replacement;

    public ReplaceFirstProcessor(String regexp, String replacement) {
        this.regexp = regexp;
        this.replacement = replacement;
    }

    @Override
    public String process(String text) {
        return text.replaceFirst(regexp, replacement);
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return ProcessingStage.PROCESSING;
    }
}
