package ru.mail.polis.homework.analyzer;

import java.util.ArrayList;

public class Analyzers {

    public static class TooLongAnalyzer implements TextAnalyzer {
        long maxLength_;
        static byte priority_ = 2;

        public TooLongAnalyzer(long maxLength) {
            this.maxLength_ = maxLength;
        }

        @Override
        public FilterType filtering(String text) {
            if (text.length() > this.maxLength_) {
                return FilterType.TOO_LONG;
            }
            return FilterType.GOOD;
        }

        @Override
        public byte getPriority() {
            return TooLongAnalyzer.priority_;
        }
    }


    public static class SpamAnalyzer implements TextAnalyzer {
        ArrayList<String> spam_ = new ArrayList<>();
        static byte priority_ = 1;

        public SpamAnalyzer(String[] spam) {
            for (String i : spam) {
                this.spam_.add(i);
            }
        }

        @Override
        public FilterType filtering(String text) {
            for (String i : this.spam_) {
                if (text.contains(i)) {
                    return FilterType.SPAM;
                }
            }
            return FilterType.GOOD;
        }

        @Override
        public byte getPriority() {
            return SpamAnalyzer.priority_;
        }
    }

    public static class NegativeTextAnalyzer implements TextAnalyzer {
        String[] negative_ = new String[]{"=(", ":(", ":|"};
        static byte priority_ = 3;

        @Override
        public FilterType filtering(String text) {
            for (String i : this.negative_) {
                if (text.contains(i)) {
                    return FilterType.NEGATIVE_TEXT;
                }
            }
            return FilterType.GOOD;
        }

        @Override
        public byte getPriority() {
            return NegativeTextAnalyzer.priority_;
        }
    }

    /**
     * Фильтр, проверяющий текст на избыточное количество вводных слов-паразитов
     * Если в тексте больше одного раза встречается какое либо из "плохих" слов
     * возвращается тип - NOT_CONCISE_TEXT (не лаконичный текст)
     */
    public static class NotConciseTextAnalyzer implements TextAnalyzer {
        String[] parasiticWords = {"В общем,", "Короче,", "Значит,"};
        static byte priority_ = 4;

        @Override
        public FilterType filtering(String text) {
            for (String parasiticWord : this.parasiticWords) {
                if (countWord(text, parasiticWord) > 1) {
                    return FilterType.NOT_CONCISE_TEXT;
                }
            }
            return FilterType.GOOD;
        }

        //количество вхождений подстроки в строку
        private int countWord(String str, String target) {
            return (str.length() - str.replace(target, "").length()) / target.length();
        }

        @Override
        public byte getPriority() {
            return NotConciseTextAnalyzer.priority_;
        }
    }
}


