package ru.mail.polis.homework.objects;

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
        int dot_ind = -1;
        int e_ind = -1;
        StringBuilder ans = new StringBuilder();
        String tmp_ans = "";
        for (int i = 0; i < str.length(); i++) {
            char cur = str.charAt(i);
            switch (cur) {
                case '.':
                case 'e':
                case '-': {
                    break;
                }
                default: {
                    if (!Character.isDigit(cur)) {
                        continue;
                    }
                    break;
                }
            }

            tmp_ans += cur;
        }

        for (int i = 0; i < tmp_ans.length(); i++) {
            char cur = tmp_ans.charAt(i);
            switch (cur) {
                case '.': {
                    if (dot_ind != -1) {
                        return null;
                    }
                    dot_ind = i;
                    break;
                }
                case 'e': {
                    if (e_ind != -1) {
                        return null;
                    }
                    e_ind = i;
                    break;
                }
                case '-': {
                    if (!(i == 0 || ans.charAt(i - 1) == 'e')) {
                        return null;
                    }
                    break;
                }
                default: {
                    break;
                }
            }
            ans.append(cur);
        }

        if (e_ind == ans.length() - 1 || dot_ind == ans.length() - 1) {
            return null;
        }

        if (e_ind < dot_ind && e_ind != -1) {
            return null;
        }

        if (ans.length() == 0) {
            return null;
        }

        int start = 0;
        if (ans.charAt(0) == '-') {
            start = 1;
        }

        if (dot_ind != -1 || e_ind != -1) {
            double num = 0;

            if (dot_ind == -1) {
                dot_ind = ans.length();
            }
            if (e_ind == -1) {
                e_ind = ans.length();
            }

            for (int i = start; i < Math.min(dot_ind, e_ind); i++) {
                num *= 10;
                num += ans.charAt(i) - '0';
            }

            double after_dot = 0;
            int extra = 0;
            for (int i = dot_ind + 1; i < e_ind; i++) {
                if (ans.charAt(i) == '0' && extra == i - dot_ind + 1) {
                    extra++;
                }
                after_dot *= 10;
                after_dot += ans.charAt(i) - '0';
            }

            int dot_degree = extra;
            double tmp_after_dot = after_dot;
            while (tmp_after_dot >= 1) {
                tmp_after_dot /= 10;
                dot_degree++;
            }

            num += after_dot * Math.pow(10, -(long) dot_degree);
            int e_start = e_ind + 1;
            if (e_start + 1 < ans.length() && ans.charAt(e_start) == '-') {
                e_start++;
            }

            int degree = 0;
            for (int i = e_start; i < ans.length(); i++) {
                degree *= 10;
                degree += ans.charAt(i) - '0';
            }

            if (e_start != e_ind + 1) {
                degree *= -1;
            }

            num *= Math.pow(10, (long) degree);

            if (start != 0) {
                num *= -1;
            }

            return num;
        } else {
            long num = 0;
            for (int i = start; i < ans.length(); i++) {
                num *= 10;
                num += ans.charAt(i) - '0';
            }
            if (start != 0) {
                num *= -1;
            }

            if (num > Integer.MAX_VALUE || num < Integer.MIN_VALUE) {
                return num;
            }
            return (int) num;
        }
    }
}
