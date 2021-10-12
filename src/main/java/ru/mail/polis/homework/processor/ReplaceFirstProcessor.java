package ru.mail.polis.homework.processor;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ReplaceFirstProcessor implements TextProcessor {

    private static final ProcessingStage STAGE = ProcessingStage.PROCESSING;
    private final String regex;
    private final String replacement;

    public ReplaceFirstProcessor(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    public String processText(String text) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.replaceFirst(replacement);
    }

    public ProcessingStage getStage() {
        return STAGE;
    }
}
