package ru.mail.polis.homework.processor;


public interface TextProcessor {

    public String process(String text);

    public ProcessingStage getStage();

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
     * Находит первую подстроку, которая удовлетворяет регулярному выражению regex, и заменяет ее на подстроку replacement
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
