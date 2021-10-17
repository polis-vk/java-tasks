package ru.mail.polis.homework.processor;

import java.util.function.Function;
import java.util.function.UnaryOperator;

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

    private static final TextProcessorManager EMPTY = new TextProcessorManager(UnaryOperator.identity());
    private final Function<String, String> processor;

    private TextProcessorManager(Function<String, String> processor) {
        this.processor = processor;
    }

    public String processText(String text) {
        return (text == null) ? null : processor.apply(text);
    }

    public static TextProcessorManager construct(TextProcessor[] processors) {
        if (!isValidSequence(processors) || processors.length == 0) {
            return EMPTY;
        }
        Function<String, String> result = processors[0];
        for (int i = 1; i < processors.length; i++) {
            result = result.andThen(processors[i]);
        }
        return new TextProcessorManager(result);
    }

    // visible for tests
    static boolean isValidSequence(TextProcessor[] processors) {
        ProcessingStage stage = ProcessingStage.PRE_PROCESSING;
        for (TextProcessor processor : processors) {
            if (processor.stage().precedes(stage)) {
                return false;
            }
            stage = processor.stage();
        }
        return true;
    }
}
