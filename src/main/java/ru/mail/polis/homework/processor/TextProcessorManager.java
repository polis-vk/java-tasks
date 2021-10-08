package ru.mail.polis.homework.processor;

import java.util.Comparator;
import java.util.stream.IntStream;

public class TextProcessorManager {

    private static final TextProcessorManager EMPTY = new TextProcessorManager(new TextProcessor[0]);
    private final TextProcessor[] processors;

    private TextProcessorManager(TextProcessor[] processors) {
        this.processors = processors;
    }

    public String processText(String text) {
        if (text == null) {
            return null;
        } else {
            String res = text;
            for (TextProcessor proc: processors) {
                res = proc.handle(res);
            }
            return res;
        }
    }

    public static TextProcessorManager construct(TextProcessor[] processors) {
        if (!isValidSequence(processors)) {
            return EMPTY;
        }
        return new TextProcessorManager(processors);
    }

    private static final Comparator<TextProcessor> comparator =
            Comparator.comparing(obj -> obj.getStage().ordinal());

    // visible for tests
    static boolean isValidSequence(TextProcessor[] processors) {
        return processors != null && IntStream.range(0, processors.length - 1).noneMatch(i -> comparator.compare(processors[i],
                processors[i + 1]) > 0);
    }
}
