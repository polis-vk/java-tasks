package ru.mail.polis.homework.processor.procs;

import ru.mail.polis.homework.processor.ProcessingStage;
import ru.mail.polis.homework.processor.TextProcessor;

public final class ReplaceFirstTextProcessor implements TextProcessor {
    
    public static final ProcessingStage STAGE = ProcessingStage.PROCESS;
    
    private final String regex;
    private final String replacement;

    public ReplaceFirstTextProcessor(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public String process(String text) {
        if(text == null) {
            return text;
        }
        return text.replaceFirst(regex, replacement);
    }

    @Override
    public ProcessingStage getStage() {
        return STAGE;
    }

}
