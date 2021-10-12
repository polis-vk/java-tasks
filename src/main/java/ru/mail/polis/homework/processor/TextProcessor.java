package ru.mail.polis.homework.processor;

abstract public class TextProcessor implements StringProcessable, Stageble {

    @Override
    abstract public ProcessingStage getStage();

    @Override
    public abstract String process(String text);
}
