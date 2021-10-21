package ru.mail.polis.homework.processor;

import java.util.Locale;

public class TrimProcessor implements TextProcessor {
    private static final ProcessingStage stage = ProcessingStage.POSTPROCESSING_STAGE;
    private final int maxLength;

    TrimProcessor(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public String processText(String txt) {
        if (txt.length() > maxLength)
            return txt.substring(0, maxLength);
        return txt;
    }

    @Override
    public ProcessingStage getStage() {
        return stage;
    }
}
