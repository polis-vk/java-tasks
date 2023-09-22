package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.List;

public class Main {

    private  static SearchInTheShredderList generate() {
        //"кобольд", "триппер", "предпринимательство",
        //            "клювонос", "счерчивание", "тёска", "мошенница", "косость", "велорикша", "агамия",
        //            "жаворонок", "тоскливость", "трансформировка", "пронзительность", "уставший", "шары", "тото"
        List<String> data = Arrays.asList("ронок", "ольд", "ельность", "уста", "рмировка", "ость", "ивость", "кос", "вело",
                "моше", "рикша", "нница", "счерч", "ппер", "трансфо", "предприни", "три", "клюв", "ска", "мательство", "ага", "онос",
                "мия", "жаво", "пронзит", "тё", "ивание", "тоскл", "коб", "вший", "ры", "ша", "ма", "ма");
        return new SearchInTheShredderList(data);
    }
    public static void main(String[] args)
    {
        SearchInTheShredderList list = generate();
        int[] ans = list.positionPartString("рикша");
        System.out.print(ans.toString());
    }
}
