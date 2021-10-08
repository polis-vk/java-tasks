package ru.mail.polis.homework.processor;

import java.util.function.UnaryOperator;

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
public interface TextProcessor extends UnaryOperator<String> {
    ProcessingStage stage();

    /**
     * Схлопывает все пустые символы в один пробел.
     * Более формально, заменить каждую подстроку, удовлетворяющую регулярному выражению \s+ на 1 пробел.
     * <p>
     * Стадия: препроцессинг
     */
    static TextProcessor squashWhiteSpacesProcessor() {
        return SQUASH_WHITE_SPACES_PROCESSOR;
    }

    TextProcessor SQUASH_WHITE_SPACES_PROCESSOR = new TextProcessor() {
        @Override
        public String apply(String s) {
            return (s == null) ? null : s.replaceAll("\\s+", " ");
        }

        @Override
        public ProcessingStage stage() {
            return ProcessingStage.PRE_PROCESSING;
        }
    };

    /**
     * Находит первую подстроку, которая удовлетворяет регулярному выражению regex, и заменяет ее на подстроку replacement
     * Предполагаем, что параметры корректны
     * <p>
     * Стадия: процессинг
     */
    static TextProcessor replaceFirstProcessor(String regex, String replacement) {
        return new TextProcessor() {
            @Override
            public ProcessingStage stage() {
                return ProcessingStage.PROCESSING;
            }

            @Override
            public String apply(String s) {
                return (s == null) ? null : s.replaceFirst(regex, replacement);
            }
        };
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
        return new TextProcessor() {
            @Override
            public ProcessingStage stage() {
                return ProcessingStage.POST_PROCESSING;
            }

            @Override
            public String apply(String s) {
                return (s == null || maxLength > s.length()) ? s : s.substring(0, maxLength);
            }
        };
    }

    /**
     * Обработчик заменяет все символы на заглавные
     * <p>
     * Стадия: постпроцессинг
     */
    static TextProcessor upperCaseProcessor() {
        return UPPERCASE_PROCESSOR;
    }

    TextProcessor UPPERCASE_PROCESSOR = new TextProcessor() {
        @Override
        public ProcessingStage stage() {
            return ProcessingStage.POST_PROCESSING;
        }

        @Override
        public String apply(String s) {
            return (s == null) ? null : s.toUpperCase();
        }
    };
}
