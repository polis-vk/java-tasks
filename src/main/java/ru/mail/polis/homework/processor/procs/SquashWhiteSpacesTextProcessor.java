package ru.mail.polis.homework.processor.procs;

import ru.mail.polis.homework.processor.ProcessingStage;
import ru.mail.polis.homework.processor.TextProcessor;

public final class SquashWhiteSpacesTextProcessor implements TextProcessor {

    public static final ProcessingStage STAGE = ProcessingStage.PREPROCESS;

    @Override
    public String process(String text) {
        if (text == null) {
            return text;
        }
        return text.replaceAll("\\s+", " ");
    }

    @Override
    public ProcessingStage getStage() {
        return STAGE;
    }

}
