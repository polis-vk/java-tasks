package ru.mail.polis.homework.objects;

import java.io.CharArrayReader;

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
        if(str == null || str.isEmpty()) return null;
        StringBuilder builder = new StringBuilder();
        boolean e = false, dot = false;
        for(int i = 0; i < str.length(); ++i)
        {
            if(Character.isDigit(str.charAt(i))) builder.append(str.charAt(i));
            if(str.charAt(i) == '-')
            {
                if(builder.isEmpty() || builder.charAt(builder.length()-1) == 'e') builder.append(str.charAt(i));
                else return null;
            }
            if(str.charAt(i) == 'e')
            {
                if(!builder.isEmpty() && Character.isDigit(builder.charAt(builder.length()-1)))
                {
                   if((!dot && !e) || (!e && dot))
                    builder.append(str.charAt(i));
                    e = true;
                }
                else return null;
            }
            if(str.charAt(i) == '.')
            {
                if(!e && !dot && !builder.isEmpty() && Character.isDigit(builder.charAt(builder.length()-1)))
                {
                    builder.append(str.charAt(i));
                    dot = true;
                }
                else return null;
            }
        }
        if(!Character.isDigit(builder.charAt(builder.length()-1))) return null;
        if(dot || e) return Double.parseDouble(builder.toString());
        long long_ = Long.parseLong(builder.toString());
        if(long_ != (int)long_) return Long.parseLong(builder.toString());
        return Integer.parseInt(builder.toString());
    }
}
