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
        StringBuilder stringBuilderWithValidSym = new StringBuilder();
        for (char ch : str.toCharArray()) {
            if (contains(ch, validSym) || Character.isDigit(ch)) {
                stringBuilderWithValidSym.append(ch);
            }
        }
        String strMod = stringBuilderWithValidSym.toString();

        int dotCount = count(strMod, '.');
        int expCount = count(strMod, 'e');
        int subCount = count(strMod, '-');
        if (dotCount > 1 || expCount > 1 || subCount > 2) {
            return null;
        }
        if (strMod.contains("--") || strMod.contains("-e") || strMod.contains(".e") ||
                strMod.contains("e.") || strMod.endsWith("-") ||
                strMod.endsWith("e") || strMod.startsWith(".")) {
            return null;
        }
        if (dotCount + expCount > 0) {
            return parseDouble(strMod);
        }
        if (subCount == 2) {
            return null;
        }
        Double res = parseWholeNumber(strMod);
        if (res <= Integer.MAX_VALUE && res >= Integer.MIN_VALUE) {
            return res.intValue();
        }
        if (res <= Long.MAX_VALUE && res >= Long.MIN_VALUE) {
            return res.longValue();
        }
        return res;
    }

    private static final char[] validSym = {'e', '.', '-'};

    private static int count(String str, Character value) {
        return str.length() - str.replace(value.toString(),"").length();
    }

    private static boolean contains(char value, char[] container) {
        for (char elem : container) {
            if (elem == value) {
                return true;
            }
        }
        return false;
    }

    private static Double parseDouble(String str) {
        int indexExp = str.indexOf('e');
        int indexDot = str.indexOf('.');
        if (indexDot != -1 && indexExp != -1) {
            if (indexDot > indexExp) {
                return null;
            }
        }
        String power;
        String mantissa;
        if (indexExp != -1 && indexDot != -1) {
            power = str.substring(indexExp + 1);
            mantissa = str.substring(0, indexExp);
            Double mant = parseDoubleWithDot(mantissa);
            return mant * Math.pow(10, parseWholeNumber(power));
        } else if (indexExp != -1) {
            power = str.substring(indexExp + 1);
            mantissa = str.substring(0, indexExp);
            Double mant = parseWholeNumber(mantissa);
            return mant * Math.pow(10, parseWholeNumber(power));
        } else if (indexDot != -1) {
            return parseDoubleWithDot(str);
        }
        return null;
    }

    private static Double parseDoubleWithDot(String str) {
        int indexDot = str.indexOf('.');
        String mantissaWhole = str.substring(0, indexDot);
        String mantissaFract = str.substring(indexDot + 1, str.length());
        Double whole = parseWholeNumber(mantissaWhole);
        Double fract = parseWholeNumber(mantissaFract);
        while (fract > 1)
            fract = fract / 10;
        return whole + fract;
    }

    private static Double parseWholeNumber(String str) {
        if (str == null) {
            return null;
        }
        if (str.isEmpty()) {
            return null;
        }
        int indexSub = str.indexOf('-');
        if (indexSub > 0) {
            return null;
        }
        if (indexSub == 0 && str.length() == 1) {
            return null;
        }
        Double res = 0.;
        for (char ch : str.toCharArray()) {
            if (ch != '-') {
                res = res * 10 + Character.getNumericValue(ch);
            }
        }
        if (indexSub == 0) {
            res = -res;
        }
        return res;
    }

}
