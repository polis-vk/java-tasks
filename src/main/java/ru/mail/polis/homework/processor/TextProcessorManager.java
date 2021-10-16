package ru.mail.polis.homework.processor;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Задание: написать систему обработки текста.
 * Надо реализовать 4 обработчика текста
 * 1) Схлопываение всех подстрок соответствующих регулярному выражению \s+ в один пробел
 * 2) Замена первой подстроки, соответствующей заданному регулярному выражению на заданную подстроку
 * 3) Обрезание текста до заданной длины
 * 4) Замена всех символов на заглавные
 * Для реализации обработчиков рекомендуется использовать уже имеющиеся методы класса String
 * <p>
 * Класс TextProcessorManager содержит последовательность обработчиков. Обработка текста состоит в последовательном
 * применении всех обработчиков к тексту. Если вместо текста на вход подается null, то всегда возвращается null.
 * <p>
 * Каждый обработчик относится к некоторой стадии обработки: препроцессинг, процессинг, постпроцессинг
 * (перечислить их в ProcessingStage). Обработчик с более ранней стадии не может применяться после обработчика
 * с более поздней стадии (порядок стадий дан выше).
 * Обработчики с одной стадии могут применяться друг после друга, без ограничений.
 * Если последовательность обработчиков, не удовлетворяет этому свойству, то TextProcessorManager не должен ничего
 * делать с исходным текстом при обработке.
 * <p>
 * Чтобы не усложнять логику TextProcessorManager, предлагается инициализировать экземпляр TextProcessorManager во
 * вспомогательном статическом методе construct. Здесь будет проверяться корректность заданной последовательности,
 * и если она некорректна, то вернем заглушку, которая ничего не делает. Иначе создадим экземпляр с помощью приватного
 * конструктора. Таким образом мы гарантируем, что экземпляр класса всегда проинициализирован с корректной
 * последовательностью обработчиков. Этот шаблон уже частично реализован, достаточно только реализовать
 * проверку на корректность (статический метод isValidSequence) и экземпляр заглушки (статическое поле EMPTY).
 * Обратите внимание, что метод isValidSequence не имеет модификатора доступа, и таким образом, он доступен в коде
 * юнит-тестов (т.к. они относятся к тому же пакету).
 * <p>
 * Базовая обвязка класса 2 балла + 3 балла за валидацию. Итого 5
 * Суммарно, по всему заданию 15 баллов
 */
public class TextProcessorManager {
    private final TextProcessor[] processors;

    private TextProcessorManager(TextProcessor[] processors) {
        this.processors = processors;
    }

    public String processText(String text) {
        if (text == null) {
            return null;
        }
        String result = text;
        for (TextProcessor processor : processors) {
            result = processor.process(result);
        }
        return result;
    }

    public static TextProcessorManager construct(TextProcessor[] processors) {
        if (!isValidSequence(processors)) {
            return new TextProcessorManager(new TextProcessor[]{});
        }

        return new TextProcessorManager(processors);
    }

    // visible for tests
    static boolean isValidSequence(TextProcessor[] processors) {
        int index = 0;
        int currentIndex;
        for (TextProcessor processor : processors) {
            currentIndex = processor.getProcessingStage().getIndex();
            if (currentIndex < index) {
                return false;
            } else {
                index = currentIndex;
            }
        }
        return true;
    }
}
