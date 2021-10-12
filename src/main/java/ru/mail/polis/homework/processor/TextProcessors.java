package ru.mail.polis.homework.processor;


/**
 * Ниже надо реализовать методы, которые создают обработчики заданного типа.
 * <p>
 * Каждый обработчик 2 балла. Итого 8
 */
public interface TextProcessors {
    /**
     * Схлопывает все пустые символы в один пробел.
     * Более формально, заменить каждую подстроку, удовлетворяющую регулярному выражению \s+ на 1 пробел.
     * <p>
     * Стадия: препроцессинг
     *
     * @return TextProcessor
     */
    static TextProcessor squashWhiteSpacesProcessor() {
        return new SquashWhiteSpacesProcessor();
    }

    /**
     * Находит первую подстроку, которая удовлетвроряет регулярному выражению regex, и заменяет ее на подстроку replacement
     * Предполагаем, что параметры корректны
     * <p>
     * Стадия: процессинг
     *
     * @return TextProcessor
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
     * @return TextProcessor
     */
    static TextProcessor trimProcessor(int maxLength) {
        return new TrimProcessor(maxLength);
    }

    /**
     * Обработчик заменяет все символы на заглавные
     * <p>
     * Стадия: постпроцессинг
     *
     * @return TextProcessor
     */
    static TextProcessor upperCaseProcessor() {
        return new UpperCaseProcessor();
    }
}
