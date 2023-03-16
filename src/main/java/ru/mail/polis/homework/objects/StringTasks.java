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
        if (str == null || str.isEmpty()) {
            return null;
        }
        StringBuilder myStr = new StringBuilder();
        boolean isEpsPresent = false;
        boolean isPointPresent = false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                myStr.append(str.charAt(i));
            }
            else if (str.charAt(i) == 'e' && Character.isDigit(myStr.charAt(myStr.length() - 1))) {
                if (!isEpsPresent){
                    myStr.append(str.charAt(i));
                    isEpsPresent = true;
                }
            }
            else if (str.charAt(i) == '-') {
                if (myStr.length() == 0 || myStr.charAt(myStr.length() - 1) == 'e') {
                    myStr.append(str.charAt(i));
                }
                else if (myStr.charAt(myStr.length() - 1) == '-' || Character.isDigit(myStr.charAt(myStr.length() - 1)) || myStr.charAt(myStr.length() - 1) == '.') {
                    return null;
                }
            }
            else if (str.charAt(i) == '.') {
                if (!isPointPresent && Character.isDigit(myStr.charAt(myStr.length() - 1))){
                    myStr.append(str.charAt(i));
                    isPointPresent = true;
                }
                else {
                    return null;
                }
            }
        }
        if (isPointPresent || isEpsPresent) {
            if (!Character.isDigit(myStr.charAt(myStr.length() - 1))) {
                return null;
            }
            return Double.valueOf(myStr.toString());
        }
        if (Long.valueOf(myStr.toString()) <= 2147483647 && Long.valueOf(myStr.toString()) >= -2147483648) {
            return Integer.valueOf(myStr.toString());
        }
        return Long.valueOf(myStr.toString());
    }
}
