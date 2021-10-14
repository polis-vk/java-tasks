package ru.mail.polis.homework.processor;

/**
 * Базовый интерфейс обработчика текста, наследники этого интерефейса должны инкапсулировать в себе всю логику
 * обработки текста.
 * Ниже надо реализовать методы, которые создают обработчики заданного типа (то что они возвращают интерфейс,
 * это как раз прием ООП, где нам не важна конкретная реализация, а важен только контракт,
 * что результат статических методов умеет как-то обрабатывать текст).
 * <p>
 * Сами статические методы мне нужны для создания тестов, что бы без реальных классов (которые вы напишите)
 * я смог "сэмулировать" их создание.
 * <p>
 * Каждый обработчик 2 балла. Итого 8
 */
public interface TextProcessor {

    ProcessingStage getStage();

    String process(String text);

    /**
     * Схлопывает все пустые символы в один пробел.
     * Более формально, заменить каждую подстроку, удовлетворяющую регулярному выражению \s+ на 1 пробел.
     * <p>
     * Стадия: препроцессинг
     */
    static TextProcessor squashWhiteSpacesProcessor() {
        return new SquashWhiteSpacesProcessor();
    }

    /**
     * Находит первую подстроку, которая удовлетвроряет регулярному выражению regex, и заменяет ее на подстроку replacement
     * Предполагаем, что параметры корректны
     * <p>
     * Стадия: процессинг
     */
    static TextProcessor replaceFirstProcessor(String regex, String replacement) {
        return new ReplaceFirstProcessor(regex, replacement);
    }

    /**
     * Данный обработчик должен оставить первые maxLength символов исходного текста.
     * Если текст короче, то ничего не делать
     * <p>
     * Стадия: постпроцессинг
     *
     * @param maxLength неотрицательное число
     */
    static TextProcessor trimProcessor(int maxLength) {
        return new TrimProcessor(maxLength);
    }

    /**
     * Обработчик заменяет все символы на заглавные
     * <p>
     * Стадия: постпроцессинг
     */
    static TextProcessor upperCaseProcessor() {
        return new UpperCaseProcessor();
    }
}
