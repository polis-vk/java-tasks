package ru.mail.polis.homework.processor;

import java.util.Locale;

/**
 * Базовый интерфейс обработчика текста, наследники этого интерефейса должны инкапсулировать в себе всю логику
 * обработки текста.
 * Ниже надо реализовать методы, которые создают обработчики заданного типа (то что они возвращают интерфейс,
 * это как раз прием ООП, где нам не важна конкретная реализация, а важен только контракт,
 * что результат статических методов умеет как-то обрабатывать текст).
 *
 * Сами статические методы мне нужны для создания тестов, что бы без реальных классов (которые вы напишите)
 * я смог "сэмулировать" их создание.
 *
 * Каждый обработчик 2 балла. Итого 8
 */
public interface TextProcessor {
    String process(String text);
    ProcessingStage stage();

    /**
     * Схлопывает все пустые символы в один пробел.
     * Более формально, заменить каждую подстроку, удовлетворяющую регулярному выражению \s+ на 1 пробел.
     *
     * Стадия: препроцессинг
     */
    static TextProcessor squashWhiteSpacesProcessor() {
        return new SquashProcessor();
    }

    class SquashProcessor implements TextProcessor {
        @Override
        public String process(String text) {
            return text.replaceAll("\\s+", " ");
        }

        @Override
        public ProcessingStage stage() {
            return ProcessingStage.PREPROCESS;
        }
    }

    /**
     * Находит первую подстроку, которая удовлетвроряет регулярному выражению regex, и заменяет ее на подстроку replacement
     * Предполагаем, что параметры корректны
     *
     * Стадия: процессинг
     */
    static TextProcessor replaceFirstProcessor(String regex, String replacement) {
        return new ReplaceFirstProcessor(regex, replacement);
    }

    class ReplaceFirstProcessor implements TextProcessor {
        private String regex;
        private String replacement;

        ReplaceFirstProcessor(String regex, String replacement) {
            this.regex = regex;
            this.replacement = replacement;
        }

        @Override
        public String process(String text) {
            return text.replaceFirst(regex, replacement);
        }

        @Override
        public ProcessingStage stage() {
            return ProcessingStage.PROCESS;
        }
    }

    /**
     * Данный обработчик должен оставить первые maxLength символов исходного текста.
     * Если текст короче, то ничего не делать
     *
     * Стадия: постпроцессинг
     *
     * @param maxLength неотрицательное число
     */
    static TextProcessor trimProcessor(int maxLength) {
        return new TrimProcessor(maxLength);
    }

    class TrimProcessor implements TextProcessor {
        private int maxLenght;

        TrimProcessor(int maxLength) {
            this.maxLenght = maxLength;
        }

        @Override
        public String process(String text) {
            return text.substring(0, Math.min(maxLenght, text.length()));
        }

        @Override
        public ProcessingStage stage() {
            return ProcessingStage.POSTPROCESS;
        }
    }

    /**
     * Обработчик заменяет все символы на заглавные
     *
     * Стадия: постпроцессинг
     */
    static TextProcessor upperCaseProcessor() {
        return new UpperCaseProcessor();
    }

    class UpperCaseProcessor implements TextProcessor {
        @Override
        public String process(String text) {
            return text.toUpperCase(Locale.ROOT);
        }

        @Override
        public ProcessingStage stage() {
            return ProcessingStage.POSTPROCESS;
        }
    }
}
