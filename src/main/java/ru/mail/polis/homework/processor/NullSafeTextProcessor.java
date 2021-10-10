package ru.mail.polis.homework.processor;

abstract public class NullSafeTextProcessor implements TextProcessor {
    @Override
    final public String process(String text) {
        if (text == null) {
            return null;
        }
        return processNotNullText(text);
    }
    abstract String processNotNullText(String text);
}
