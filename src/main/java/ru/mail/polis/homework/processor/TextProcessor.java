package ru.mail.polis.homework.processor;

import java.util.ArrayList;

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

    ArrayList<ProcessingStage> stages = new ArrayList<>();
    ProcessingStage[] allStages = ProcessingStage.values();

    static TextProcessor validate() {
        if (stages.size() < 2) {
            return new TextProcessor() {};
        }

        ProcessingStage prev = stages.get(stages.size() - 2), current = stages.get(stages.size() - 1);

        int prevIndex = 0, currentIndex = 0;

        for (int i = 0; i < allStages.length; i++) {
            if (prev == allStages[i]) {
                prevIndex = i;
            } else if (current == allStages[i]) {
                currentIndex = i;
            }
        }

        return prevIndex <= currentIndex ? new TextProcessor() {} : null;
    }

    /**
     * Схлопывает все пустые символы в один пробел.
     * Более формально, заменить каждую подстроку, удовлетворяющую регулярному выражению \s+ на 1 пробел.
     *
     * Стадия: препроцессинг
     */
    static TextProcessor squashWhiteSpacesProcessor() {
        stages.add(ProcessingStage.PRE_PROCESSING);

        TextProcessor result = validate();

        stages.clear();

        return result;
    }

    /**
     * Находит первую подстроку, которая удовлетвроряет регулярному выражению regex, и заменяет ее на подстроку replacement
     * Предполагаем, что параметры корректны
     *
     * Стадия: процессинг
     */
    static TextProcessor replaceFirstProcessor(String regex, String replacement) {
        stages.add(ProcessingStage.PROCESSING);

        TextProcessor result = validate();

        stages.clear();

        return result;
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
        stages.add(ProcessingStage.POST_PROCESSING);

        TextProcessor result = validate();

        stages.clear();

        return result;
    }

    /**
     * Обработчик заменяет все символы на заглавные
     *
     * Стадия: постпроцессинг
     */
    static TextProcessor upperCaseProcessor() {
        stages.add(ProcessingStage.POST_PROCESSING);

        TextProcessor result = validate();

        stages.clear();

        return result;
    }
}
