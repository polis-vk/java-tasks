package ru.mail.polis.homework.processor;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SquashWhiteSpacesProcessor implements TextProcessor {

    private final ProcessingStage STAGE = ProcessingStage.PREPROCESSING;

    public String processText(String text) {
        Pattern pattern = Pattern.compile("[ \t\n\\x0B\f\r]+");
        Matcher matcher = pattern.matcher(text);
        return matcher.replaceAll(" ");
    }

    public ProcessingStage getStage() {
        return STAGE;
    }
}
