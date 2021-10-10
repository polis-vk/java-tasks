package ru.mail.polis.homework.processor;

public class SquashWhiteSpacesProcessor extends ReplaceProcessor{

    private static final String REGEX = "\\s+";
    private static final String WHITESPACE = " ";

    public SquashWhiteSpacesProcessor() {
        super(REGEX, WHITESPACE, true);
    }

    @Override
    public ProcessingStage getProcessingStage() {
        return ProcessingStage.PRE_PROCESSING_STAGE;
    }
}
