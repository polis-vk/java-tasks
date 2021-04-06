package ru.mail.polis.homework.objects;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валдино
     * Любой класс-обертка StringTasksTest над примитивами наследуется от Number
     * Нельзя использовать функции Double.valueOf() и другие такие же.
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     */
    public static Number valueOf(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        int dotCount = str.length() - str.replace(".", "").length();
        int eCount = str.length() - str.replace("e", "").length();
        int deCount = str.length() - str.replace("-", "").length();
        if (dotCount > 1 || eCount > 1 || deCount > 2) {
            return null;
        }
        StringBuilder term = new StringBuilder();
        for (char ch : str.toCharArray()) {
            if (Character.isDigit(ch) || ch == '.' || ch == 'e' || ch == '-') {
                term.append(ch);
            }
        }
        String finalTerm = term.toString();

        if (finalTerm.contains("--") || finalTerm.contains("-e") || finalTerm.contains(".e") || finalTerm.endsWith(("-"))
                || finalTerm.endsWith(("e"))) {
            return null;
        }

        if (dotCount + eCount > 0) {
            return parseToDouble(finalTerm);
        }

        double IntOrLong = parseNumbers(finalTerm);
        if (IntOrLong > Integer.MAX_VALUE || IntOrLong < Integer.MIN_VALUE) {
            return (long)IntOrLong;
        }
        return (int)IntOrLong;
    }

    private static Double parseNumbers(String str) {
        int minus = 1;
        int firstCharacter = 0;
        if (str.charAt(0) == '-') {
            minus = -1;
            firstCharacter = 1;
        }
        double res = str.charAt(firstCharacter) - (int) '0';
        for (int i = firstCharacter + 1; i < str.length(); i++) {
            res = res * 10 + (str.charAt(i) - (int) '0');
        }
        return res * minus;
    }

    private static Double parseToDouble(String str) {
        double res = 0;
        int dotPos = str.indexOf(".");
        int ePos = str.indexOf("e");
        int sign = str.charAt(0) == '-' ? -1 : 1;
        double powerOfE = 1;
        double intPart;
        double fracPart = 0;
        if (ePos > 0) {
            powerOfE = Math.pow(10, parseNumbers((str.substring(ePos + 1, str.length()))));
        }

        if (dotPos >= 0) {
            intPart = Math.abs(parseNumbers((str.substring(0, dotPos))));
            if (ePos < 0) {
                fracPart = Math.abs(parseNumbers((str.substring(dotPos + 1, str.length()))) / Math.pow(10, str.length() - str.indexOf(".") - 1));
            } else {
                fracPart = Math.abs(parseNumbers((str.substring(dotPos + 1, ePos))) / Math.pow(10, str.indexOf('e') - str.indexOf('.') - 1));
            }
        } else {
            intPart = Math.abs(parseNumbers((str.substring(0, ePos))));
        }
        res = sign * (intPart + fracPart) * powerOfE;
        return res;
    }

}
