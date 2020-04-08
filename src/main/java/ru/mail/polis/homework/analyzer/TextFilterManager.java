package ru.mail.polis.homework.analyzer;


import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Задание написать систему фильтрации комментариев.
 * Надо реализовать три типа обязательных фильтров
 * 1) фильтр для слишком длинных текстов (длина задается при создании) (TOO_LONG)
 * 2) фильтр для спама (передается массив плохих слов, которых не должно быть в тексте) (SPAM)
 * 3) фильтр для текстов с плохими эмоциями. (в тексте не должно быть таких смайлов:
 * "=(", ":(", ":|" (NEGATIVE_TEXT)
 * + сделать любой свой фильтр (CUSTOM)
 * <p>
 * Класс TextFilterManager должен содержать все фильтры, которые передаются ему в конструкторе,
 * и при анализе текста через метод analyze должен выдавать первый "успешный" фильтр,
 * если не один не прошел, то возвращать тип GOOD.
 * Дополнительное задание: нужно всем типам фильтров задать приоритет
 * (SPAM, TOO_LONG, NEGATIVE_TEXT, CUSTOM - в таком порядке) и возвращать тип с максимальным приоритетом.
 * Отсортировать фильтра можно с помощью функции
 * Arrays.sort(filter, (filter1, filter2) -> {
 * if (filter1 < filter2) {
 * return -1;
 * } else if (filter1 == filter2) {
 * return 0;
 * }
 * return 1;
 * }
 * где вместо сравнение самих фильтров должно быть стравнение каких-то количественных параметров фильтра
 * <p>
 * 2 балла ( + 2 балла за доп приоритет)
 * Итого 15 баллов + 2 дополнительных
 */
public class TextFilterManager {
    private TextAnalyzer[] filters;
    /**
     * Для работы с каждым элементом массива, нужно использовать цикл for-each
     * Хочется заметить, что тут мы ничего не знаем, какие конкретно нам объекты переданы, знаем только то,
     * что в них реализован интерфейс TextAnalyzer
     */
    public TextFilterManager(TextAnalyzer[] filters) {
        this.filters = filters.clone();
        sortFilters1(this.filters);
        //sortFilters2(this.filters);
        //sortFilters3(this.filters);

    }

    /**
     * Если переменная текст никуда не ссылается, то это означает, что не один фильтр не сработал
     */
    public FilterType analyze(String text) {
        FilterType type;
        for (TextAnalyzer filter : filters) {
            type = filter.analyze(text);
            if (type != FilterType.GOOD) return type;
        }
        return FilterType.GOOD;
    }
    /* (Old)
    *  Первая версия компаратора. Используется порядок типов фильтров в enum FilterType
    *  Минусы - зависимость от порядка элементов в перечислении
    * */
    private void sortFilters1(TextAnalyzer[] filters){
        Arrays.sort(filters, (filter1, filter2) -> {
            if (filter1.getFilterType().ordinal() > filter2.getFilterType().ordinal()) {
                return 1;
            } else if (filter1.getFilterType().ordinal() == filter2.getFilterType().ordinal()) {
                return 0;
            }
            return -1;
        });
    }
    /*
     *  Вторая версия компаратора. Используется поле priority в классах сравниваемых фильтров.
     *  Минусы: Изменение фильтров - необходимость добавления поля и метода к каждому сравниваемому классу.
     * */
    private void sortFilters2(TextAnalyzer[] filters){
        Arrays.sort(filters, (filter1, filter2) -> {
            if (filter1.getPriority() > filter2.getPriority()) {
                return 1;
            } else if (filter1.getPriority() == filter2.getPriority()) {
                return 0;
            }
            return -1;
        });
    }
    /*  (В порядке бреда)
     *  Третья версия компаратора. Используется метод с забитыми магическими константами, возвращающий число, отражающее приоритет фильтра.
     *  Минусы: Приоритеты записаны хардкодом. Решение в лоб.
     *  Плюсы:  Пока единственное решение, никак не затрагивающее другие классы
     * */
    private void sortFilters3(TextAnalyzer[] filters){
        Arrays.sort(filters, (filter1, filter2) -> {
            if (filter1.getPriority() > filter2.getPriority()) {
                return 1;
            } else if (filter1.getPriority() == filter2.getPriority()) {
                return 0;
            }
            return -1;
        });
    }
    private int getPriority(FilterType type) {
        switch (type.toString()){
            case "SPAM":
                return 1;
            case "TOO_LONG":
                return 2;
            case "NEGATIVE_TEXT":
                return 3;
            case "CUSTOM":
                return 4;
            default:
                return -1;
        }
    }
}
