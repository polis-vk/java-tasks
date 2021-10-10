package ru.mail.polis.homework.processor.procs;

import ru.mail.polis.homework.processor.ProcessingStage;
import ru.mail.polis.homework.processor.TextProcessor;

public final class TrimTextProcessor implements TextProcessor {

    public static final ProcessingStage STAGE = ProcessingStage.POSTPROCESS;
    
    private final int maxLength;
    
    public TrimTextProcessor(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public String process(String text) {
        if(text == null || text.length() <= maxLength) {
            return text;
        }        
        return text.substring(0, this.maxLength);
    }

    @Override
    public ProcessingStage getStage() {
        return STAGE;
    }

}
