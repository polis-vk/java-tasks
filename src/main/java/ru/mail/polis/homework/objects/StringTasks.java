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
        if (str == null || str.length() == 0) {
            return null;
        }

        String clean = str.replaceAll("[^\\- e .  0-9]", "");   //очистка от лишних симолов
        if (clean.replaceAll("[^.]", "").length() > 1
                || clean.replaceAll("[^e]", "").length() > 1) {
            return null;
        }

        int pointPosition = clean.indexOf('.');
        int ePosition = clean.indexOf('e');

        String mantisa;                                                             //мантиса
        String power;                                                               //порядок

        if (ePosition != -1) {                                                      //если есть е, то
            mantisa = clean.substring(0, ePosition);                                //получаем мантиссу
            power = clean.substring(ePosition + 1);                                 //получаем порядок
        } else {                                                                    //иначе
            mantisa = clean;                                                        //все мантисса
            power = "";
        }

        if (chekMinus(mantisa) || chekMinus(power)) {                               //--3 не валидно. -3e-1 валдино
            return null;
        }

        if (pointPosition != -1 || ePosition != -1) {                               //если есть . или e то
            return getDouble(mantisa, pointPosition) * Math.pow(10, getLong(power));//возвращаем double
        } else {                                                                    //иначе
            long result = getLong(mantisa);                                         //получаем long
            if (result <= Integer.MAX_VALUE && result >= Integer.MIN_VALUE) {       //если можно, то
                return (int) result;                                                //приводим long к int, возвращаем
            }                                                                       //если нельзя, то
            return result;                                                          //возвращаем long
        }
    }

    private static boolean chekMinus(String str) {
        if (str.replaceAll("[^-]", "").length() > 1) {
            return true;
        }
        if (str.indexOf('-') > 0) {
            return true;
        }
        return false;
    }

    private static double getDouble(String str, int pointPos) {
        str = str.replaceAll("[.]", "");
        if (pointPos != -1) {
            return (double) (getLong(str) / (Math.pow(10, str.length() - pointPos)));
        }
        return (double) (getLong(str));
    }

    private static long getLong(String str) {
        long result = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) != '-') {
                result += (str.charAt(i) - 48) * Math.pow(10, str.length() - i - 1);
            } else {
                result = -result;
            }
        }
        return result;
    }
}








/*



        int points = clean.replaceAll("[^.]", "").length();
        int e = clean.replaceAll("[^e]", "").length();
        int minuses = clean.replaceAll("[^-]", "").length();
        if (points > 1 || e > 1 || minuses > 2) {
            return null;
        }
        int ePosition = 0;
        for (int i = 0; i < clean.length(); i++) {
            if (clean.charAt(i) == 'e') {
                ePosition = i;
            }
            if (clean.charAt(i) == '-' && i != 0 && clean.charAt(i - 1) != 'e') {
                return null;
            }
        }

        String intagerValue;
        String expValue;
        if (e != 0) {

            intagerValue = clean.substring(0, ePosition);
            expValue = clean.substring(ePosition + 1, clean.length());

            double num = getDouble(intagerValue);
            double eNum = getLong(expValue);

            if (eNum > 0) {
                return num * (Math.pow(10, eNum));
            } else {
                return num / (Math.pow(10, -eNum));
            }
        } else {
            if (points == 0) {
                long num = getLong(clean);
                if (num <= Integer.MAX_VALUE & num >= Integer.MIN_VALUE & num % 1 == 0) {
                    return (int) num;
                }
                return num;
            } else {
                return getDouble(clean);
            }
        }
    }



    private static double getDouble(String pStr) {
        double result = 0;
        if (pStr.replaceAll("[^.]", "").length() != 0) {
            int countToEnd = 0;
            for (int i = 0; i < pStr.length(); i++) {
                if (pStr.charAt(i) == '.') {
                    countToEnd = pStr.length() - i;
                }
            }
            pStr = pStr.replaceAll("[.]", "");
            result = (double) (getLong(pStr) / (Math.pow(10, countToEnd - 1)));
        } else {
            result = getLong(pStr);
        }
        return result;
    }

    private static long getLong(String pStr) {
        int minuses = pStr.replaceAll("[^-]", "").length();
        pStr = pStr.replaceAll("[-]", "");
        long result = 0L;
        for (int i = 0; i < pStr.length(); i++) {
            result += pStr.charAt(i) - '0';
            if (i + 1 < pStr.length()) {
                result *= 10;
            }
        }
        if (minuses != 0) {
            result = -result;
        }
        return result;
    }
}
*/