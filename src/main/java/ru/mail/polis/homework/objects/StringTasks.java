package ru.mail.polis.homework.objects;

import java.lang.Math;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валдино
     * Любой класс-обертка StringTasksTest над примитивами наследуется от Number
     * Если вы используете функции типа Double.valueOf() -- получите только половину тугриков.
     * Для полного количества тугриков надо парсить в ручную.
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     */
    public static Number valueOf(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        int checkDot = 0;
        int checkEps = 0;
        int checkMinus = 0;
        int memoryMinus = -1;
        int memoryEps = -1;
        int memoryDot = -1;
        StringBuilder temporary = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            Character buff = str.charAt(i);
            if (Check(buff)) {
                temporary.append(buff);
            }
        }
        for (int i = 0; i < temporary.length(); i++) {
            if (temporary.charAt(i) == '.') {
                checkDot += 1;
                continue;
            } else if (temporary.charAt(i) == 'e') {
                checkEps += 1;
                continue;
            } else if (temporary.charAt(i) == '-') {
                checkMinus += 1;
            }
        }
        if (checkDot > 1 || checkEps > 1 || checkMinus > 2) {
            return null;
        }
        if (temporary.charAt(0) == 'e' || temporary.charAt(temporary.length() - 1) == 'e') {
            return null;
        }
        if (temporary.charAt(0) == '.' || temporary.charAt(temporary.length() - 1) == '.') {
            return null;
        }
        for (int i = 0; i < temporary.length() - 1; i++) {
            if (temporary.charAt(i) == '-' && temporary.charAt(i + 1) == '-') {
                return null;
            }
        }
        if ((temporary.charAt(temporary.length() - 1)) == '-') {
            return null;
        }
        for (int i = 0; i < temporary.length(); i++) {
            if (temporary.charAt(i) == 'e' && (temporary.charAt(i - 1) == '.' || temporary.charAt(i - 1) == '-')) {
                return null;
            }
        }
        for (int i = 0; i < temporary.length(); i++) {
            if (temporary.charAt(i) == '-' && i != 0) {
                memoryMinus = i;
                continue;
            } else if (temporary.charAt(i) == 'e') {
                memoryEps = i;
                continue;
            } else if (temporary.charAt(i) == '.') {
                memoryDot = i;
            }
        }
        String result = temporary.toString();
        if (memoryDot != -1 || memoryMinus != -1 || memoryEps != -1) {
            if (result.charAt(0) == '-') {
                return toDouble(result, memoryDot, memoryEps, memoryMinus, true);
            } else {
                return toDouble(result, memoryDot, memoryEps, memoryMinus, false);
            }
        }
        long resultNumber = Long.valueOf(result);
        if (Integer.MIN_VALUE > resultNumber || resultNumber > Integer.MAX_VALUE) {
            if (result.charAt(0) == '-') {
                return toLong(result, true);
            } else {
                return toLong(result, false);
            }
        } else if (Integer.MIN_VALUE <= resultNumber && resultNumber <= Integer.MAX_VALUE) {
            if (result.charAt(0) == '-') {
                return toInt(result, true);
            } else {
                return toInt(result, false);
            }
        }
        return 0;
    }

    public static double toDouble(String result, int memoryDot, int memoryEps, int memoryMinus, boolean choice) {
        int epsPart = 0;
        long integerPart = -1;
        double doublePart = 0;
        boolean intPart = true;
        boolean flagDot = true;
        boolean flagEps = true;
        boolean flagMinus = true;
        if (memoryDot == -1) {
            flagDot = false;
        }
        if (memoryEps == -1) {
            flagEps = false;
        }
        if (memoryMinus == -1) {
            flagMinus = false;
        }
        if (!choice) {
            if (flagDot) {
                intPart = false;
                integerPart = 0;
                int rank = memoryDot - 1;
                for (int i = 0; i < memoryDot; i++) {
                    integerPart += CheckInt(result.charAt(i)) * Math.pow(10, rank);
                    rank -= 1;
                }
                if (flagEps) {
                    rank = 1;
                    for (int i = memoryDot + 1; i < memoryEps; i++) {
                        doublePart += CheckInt(result.charAt(i)) * Math.pow(10, -rank);
                        rank += 1;
                    }
                } else {
                    rank = 1;
                    for (int i = memoryDot + 1; i < result.length(); i++) {
                        doublePart += CheckInt(result.charAt(i)) * Math.pow(10, -rank);
                        rank += 1;
                    }
                    return integerPart + doublePart;
                }
            }
            if (flagEps) {
                if (intPart) {
                    integerPart = 0;
                    int rank = memoryEps - 1;
                    for (int i = 0; i < memoryEps; i++) {
                        integerPart += CheckInt(result.charAt(i)) * Math.pow(10, rank);
                        rank -= 1;
                    }
                }
                if (flagMinus) {
                    int rank = result.length() - memoryEps - 3;
                    for (int i = memoryEps + 2; i < result.length(); i++) {
                        epsPart += CheckInt(result.charAt(i)) * Math.pow(10, rank);
                        rank -= 1;
                    }
                } else {
                    int rank = result.length() - memoryEps - 2;
                    for (int i = memoryEps + 1; i < result.length(); i++) {
                        epsPart += CheckInt(result.charAt(i)) * Math.pow(10, rank);
                        rank -= 1;
                    }
                }
            }
            if (flagMinus) {
                return (integerPart + doublePart) * Math.pow(10, (-1 * epsPart));
            } else {
                return (integerPart + doublePart) * Math.pow(10, epsPart);
            }
        } else {
            if (flagDot) {
                intPart = false;
                integerPart = 0;
                int rank = memoryDot - 2;
                for (int i = 1; i < memoryDot; i++) {
                    integerPart -= CheckInt(result.charAt(i)) * Math.pow(10, rank);
                    rank -= 1;
                }
                if (flagEps) {
                    rank = 1;
                    for (int i = memoryDot + 1; i < memoryEps; i++) {
                        doublePart -= CheckInt(result.charAt(i)) * Math.pow(10, -1 * rank);
                        rank += 1;
                    }
                } else {
                    rank = 1;
                    for (int i = memoryDot + 1; i < result.length(); i++) {
                        doublePart -= CheckInt(result.charAt(i)) * Math.pow(10, -1 * rank);
                        rank += 1;
                    }
                    return integerPart + doublePart;
                }
            }
            if (flagEps) {
                if (intPart) {
                    integerPart = 0;
                    int rank = memoryEps - 2;
                    for (int i = 1; i < memoryEps; i++) {
                        integerPart -= CheckInt(result.charAt(i)) * Math.pow(10, rank);
                        rank -= 1;
                    }
                }
                if (flagMinus) {
                    int rank = result.length() - memoryEps - 3;
                    for (int i = memoryEps + 2; i < result.length(); i++) {
                        epsPart += CheckInt(result.charAt(i)) * Math.pow(10, rank);
                        rank -= 1;
                    }
                } else {
                    int rank = result.length() - memoryEps - 2;
                    for (int i = memoryEps + 1; i < result.length(); i++) {
                        epsPart += CheckInt(result.charAt(i)) * Math.pow(10, rank);
                        rank -= 1;
                    }
                }
            }
            if (flagMinus) {
                return (integerPart + doublePart) * Math.pow(10, (-1 * epsPart));
            } else {
                return (integerPart + doublePart) * Math.pow(10, epsPart);
            }
        }
    }

    public static int toInt(String result, boolean choice) {
        int toInt = 0;
        int rank = result.length() - 1;
        if (!choice) {
            for (int i = 0; i < result.length(); i++) {
                toInt += CheckInt(result.charAt(i)) * Math.pow(10, rank);
                rank -= 1;
            }
        } else {
            for (int i = 0; i < result.length(); i++) {
                toInt -= CheckInt(result.charAt(i)) * Math.pow(10, rank);
                rank -= 1;
            }
        }
        return toInt;
    }

    public static long toLong(String result, boolean choice) {
        long toLong = 0;
        int rank = result.length() - 1;
        if (!choice) {
            for (int i = 0; i < result.length(); i++) {
                toLong += CheckInt(result.charAt(i)) * Math.pow(10, rank);
                rank -= 1;
            }
        } else {
            for (int i = 0; i < result.length(); i++) {
                toLong -= CheckInt(result.charAt(i)) * Math.pow(10, rank);
                rank -= 1;
            }
        }
        return toLong;
    }

    public static int CheckInt(char buff) {
        switch (buff) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            default:
                return 0;
        }
    }

    public static boolean Check(Character buff) {
        if (Character.isDigit(buff)) {
            return true;
        }
        switch (buff) {
            case '-':
                return true;
            case '.':
                return true;
            case 'e':
                return true;
            default:
                return false;
        }
    }
}
