package ru.mail.polis.homework.objects;

import org.jetbrains.annotations.Nullable;

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
        double[] res = {0, 0};
        StringBuilder res_str = new StringBuilder("");
        int i = 0, c_e = 0, res_l = 0, fl = 0;
        if (str == null) {
            return null;
        }
        if (str.toString().equals("")) {
            return null;
        }
        while (i < str.length()) {
            int start_i = res_l, c_sign = 0, c_p = 0;
            while (i < str.length() && str.charAt(i) != 'e') {
                if (str.charAt(i) == '-' && c_sign < 2) {
                    c_sign++;
                    res_str.append(str.charAt(i));
                    res_l++;
                } else if (c_sign >= 2) {
                    return null;
                }
                if (str.charAt(i) == '.') {
                    c_p++;
                    res_str.append(str.charAt(i));
                    res_l++;
                } else if (c_sign >= 2) {
                    return null;
                }
                if (Character.isDigit(str.charAt(i))) {
                    res_str.append(str.charAt(i));
                    res_l++;
                }
                i++;
            }

            fl++;

            if (i < str.length() && c_e < 1 && str.charAt(i) == 'e') {
                c_e++;
            } else if (i < str.length()) {
                return null;
            }

            if (((c_sign == 1 && res_str.charAt(0) == '-') || c_sign == 0) && start_i != res_l && fl < 2) {
                if (c_p == 1 && str.charAt(i - 1) != '.') {
                    res[c_e] = toNumber(res_str.substring(start_i, res_l));
                } else if (str.charAt(i - 1) == '.') {
                    return null;
                } else if (c_p == 0) {
                    res[c_e] = toInt(res_str.substring(start_i, res_l));
                }
            } else if (start_i != res_l) {
                return null;
            }
            if (i < str.length() && str.charAt(i) == 'e') {
                res_str.append(str.charAt(i));
                res_l++;
            }
        }
        if (res[1] == 0) {
            if (res[0] > Integer.MAX_VALUE){
                return (long) res[0];
            }
            else{
                return (int) res[0];
            }
        } else {
            return res[0] * Math.pow(10, res[1]);
        }
    }

    private static double toNumber(String str) {
        int sign = 1, i = 0;
        if (str.charAt(0) == '-') {
            sign = -1;
            i = 1;
        }
        int start_i = 0;
        while (i < str.length() && str.charAt(i) != '.') {
            i++;
        }
        long int_num = toInt(str.substring(start_i, i - 1));
        double dot_num = toInt(str.substring(i + 1, str.length() - 1)) / Math.pow(10, str.length() - i - 2);
        return (double) int_num + dot_num;
    }

    private static long toInt(String str) {
        long res = 0;
        int sign = 1, j = 0, delta = 0;
        if (str.charAt(0) == '-') {
            sign = -1;
            delta = 1;
            j = 1;
        }
        for (int i = j; i < str.length(); i++) {
            double pow = Math.pow(10, str.length() - i - delta);
            switch (str.charAt(i)) {
                case '1':
                    res += 1 * pow;
                    break;
                case '2':
                    res += 2 * pow;
                    break;
                case '3':
                    res += 3 * pow;
                    break;
                case '4':
                    res += 4 * pow;
                    break;
                case '5':
                    res += 5 * pow;
                    break;
                case '6':
                    res += 6 * pow;
                    break;
                case '7':
                    res += 7 * pow;
                    break;
                case '8':
                    res += 8 * pow;
                    break;
                case '9':
                    res += 9 * pow;
                    break;
            }
        }
        return res * sign;
    }
}
