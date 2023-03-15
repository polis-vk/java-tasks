package ru.mail.polis.homework.objects;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валидино
     * Любой класс-обертка StringTasksTest над примитивами наследуется от Number
     * Можно использовать функции типа Double.valueOf()
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     * БЕЗ РЕГУЛЯРОК!
     * 6 тугриков
     */
    public static Number valueOf(String str) {
        if(str == null || str == "") {
            return null;
        }
        str = removeUnnecessarySymbols(str);
        int i = 0;
        if(str.charAt(i) != '-' && !Character.isDigit(str.charAt(i))) {
            return null;
        }
        if(str.charAt(i) == '-') {
            i++;
        }
        if(!Character.isDigit(str.charAt(i))) {
            return null;
        }
        do {
            i++;
            if(i == str.length()) {
                Long result = Long.valueOf(str);
                if(result.intValue() == result.longValue()) {
                    return Integer.valueOf(str);
                } else {
                    return result;
                }
            }
        }while (Character.isDigit(str.charAt(i)));
        if(str.charAt(i) == '.') {
            i++;
            if(!Character.isDigit(str.charAt(i))) {
                return null;
            }
            else {
                do {
                    i++;
                    if(i == str.length()) {
                        return Double.valueOf(str);
                    }
                }while (Character.isDigit(str.charAt(i)));
                if(str.charAt(i) != 'e') {
                    return null;
               }
            }
        }
        if (str.charAt(i) == 'e') {
            i++;
            if(i == str.length()
            || str.charAt(i) != '-'
            && !Character.isDigit(str.charAt(i))) {
                return null;
            } else {
                if(str.charAt(i) == '-') {
                    i++;
                }
                if(!Character.isDigit(str.charAt(i))) {
                    return null;
                }
                do {
                    i++;
                    if(i == str.length()) {
                        return Double.valueOf(str);
                    }
                }while (Character.isDigit(str.charAt(i)));
                return null;
            }
        } else {
            return null;
        }
    }
    private static String removeUnnecessarySymbols(String str) {
        int i = 0;
        while (i < str.length()) {
            if(str.charAt(i) != '-'
               && !Character.isDigit(str.charAt(i))
               && str.charAt(i) != 'e'
               && str.charAt(i) != '.'
            ) {
                if(i == 0) {
                    if(i + 1 == str.length()) {
                        return "";
                    } else {
                        str = str.substring(1);
                    }
                } else if(i == str.length() - 1){
                    str = str.substring(0, i);
                } else {
                    str = str.substring(0, i) + str.substring(i + 1);
                }

            } else {
                i++;
            }
        }
        return str;
    }
}
