package ru.mail.polis.homework.processor.procs;

import ru.mail.polis.homework.processor.ProcessingStage;
import ru.mail.polis.homework.processor.TextProcessor;

public final class UpperCaseTextProcessor implements TextProcessor {

    public static final ProcessingStage STAGE = ProcessingStage.POSTPROCESS;

    @Override
    public String process(String text) {
        if(text == null) {
            return text;
        }
        return text.toUpperCase();
    }
    
    @Override
    public ProcessingStage getStage() {
        return STAGE;
    }

}
