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

        int pointCounts = str.length() - str.replace(".", "").length();
        int eCounts = str.length() - str.replace("e", "").length();
        int minusCounts = str.length() - str.replace("-", "").length();

        if (pointCounts > 1 || eCounts > 1 || minusCounts > 2) {
            return null;
        }

        StringBuilder digitStringBuilder = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c) || c == 'e' || c == '-' || c == '.') {
                digitStringBuilder.append(c);
            }
        }

        String digitString = digitStringBuilder.toString();
        if (digitString.equals("e")
                || digitString.equals("-")
                || digitString.equals(".")
                || digitString.equals("--")
                || digitString.length() == 0) {
            return null;
        }

        if (eCounts + pointCounts == 0) {
            if (!checkMinuses(minusCounts, digitString)) {
                return null;
            }
            long longResult = getLongFormat(digitString);
            if (longResult <= Integer.MAX_VALUE && longResult >= Integer.MIN_VALUE) {
                return (int) longResult;
            }
            return longResult;
        }
        //Все последующие операции для Double
        int indexE = digitString.indexOf('e');
        int indexPoint = digitString.indexOf('.');
        int indexMinus = digitString.indexOf('-');

        if (eCounts == 0) {
            if (!checkMinuses(minusCounts, digitString)) {
                return null;
            }
            if (digitString.charAt(indexMinus + 1) == '.') {
                return null;
            }
            if (digitString.charAt(digitString.length() - 1) == '.') {
                return null;
            }
            return getDoubleFormat(digitString);
        }

        if (pointCounts == 0 || pointCounts + eCounts == 2) {
            if (minusCounts == 2) {
                if (digitString.charAt(0) != '-' || digitString.charAt(indexE + 1) != '-') {
                    return null;
                }
            }
            if (minusCounts == 1) {
                if (digitString.charAt(0) != '-' && digitString.charAt(indexE + 1) != '-') {
                    return null;
                }
            }
            if (digitString.charAt(indexMinus + 1) == 'e' || indexMinus == digitString.length()) {
                return null;
            }
            if (pointCounts == 1) {
                if (indexPoint > indexE
                        || indexPoint == 0) {
                    return null;
                }
            }
        }
        return getDoubleFormat(digitString);
    }

    public static double getDoubleFormat(String strDigit) {
        boolean sign = strDigit.charAt(0) == '-';
        boolean signE = strDigit.contains("e-");
        String unsignedStr = strDigit;

        if (sign) {
            unsignedStr = strDigit.substring(1);
        }

        double result;
        int indexOfE = unsignedStr.indexOf('e');
        int indexOfPoint = unsignedStr.indexOf('.');

        if (indexOfE != -1) {
            String numberBeforeE = unsignedStr.substring(0, indexOfE);
            String numberAfterE;
            result = getNumberFromString(numberBeforeE);

            if (signE) {
                numberAfterE = unsignedStr.substring(indexOfE + 2);
            } else {
                numberAfterE = unsignedStr.substring(indexOfE + 1);
            }

            double exponent = getNumberFromString(numberAfterE);

            if (indexOfPoint != -1) {
                result /= Math.pow(10, numberBeforeE.length() - indexOfPoint - 1);
            }

            if (signE) {
                result /= Math.pow(10, exponent);
            } else {
                result *= Math.pow(10, exponent);
            }

        } else {
            result = getNumberFromString(unsignedStr);
            result /= Math.pow(10, unsignedStr.length() - indexOfPoint - 1);
        }

        if (sign) {
            return -result;
        }
        return result;
    }

    public static double getNumberFromString(String strDigits) {
        int exponent = 0;
        double result = 0;
        for (int i = strDigits.length() - 1; i >= 0; i--) {
            if (strDigits.charAt(i) == '.') {
                continue;
            }
            result += (strDigits.charAt(i) - '0') * Math.pow(10, exponent);
            exponent++;
        }
        return result;
    }

    public static long getLongFormat(String strDigit) {
        boolean sign = strDigit.charAt(0) == '-';
        double result = 0;
        String unsignedStr = strDigit;

        if (sign) {
            unsignedStr = strDigit.substring(1);
        }
        result = getNumberFromString(unsignedStr);

        if (sign) {
            return -(long) result;
        }
        return (long) result;
    }

    public static boolean checkMinuses(int minusCounts, String digitString) {
        if (minusCounts == 2) {
            return false;
        }
        if (minusCounts == 1) {
            int firstMinus = digitString.indexOf('-');
            if (firstMinus > -1 && firstMinus != 0) {
                return false;
            }
        }
        return true;
    }
}
