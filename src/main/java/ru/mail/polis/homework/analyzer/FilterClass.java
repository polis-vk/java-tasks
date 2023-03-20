package ru.mail.polis.homework.analyzer;

public class FilterClass implements TextAnalyzer {

    long point;
    long maxLenght = -10;
    String[] spams = {};
    String[] negatives = {"=(", ":(", ":|"};
    String str2;

    FilterClass(long maxLenght) {
        this.maxLenght = maxLenght;
        this.point = 1;
    }

    FilterClass(String[] spams) {
        this.point = 2;
        this.spams = spams;
    }

    FilterClass() {
        this.point = 3;
    }

    FilterClass(String str2) {
        this.str2 = str2;
        this.point = 4;
    }

    @Override
    public FilterType longTextFilter(String str) {
//        System.out.println(str.length());
//        System.out.println(this.maxLenght);
        if (str.length() > this.maxLenght) {

            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }

    @Override
    public long point() {
        return this.point;
    }

    @Override
    public FilterType spamFilter(String str) {
        for (String spams : this.spams) {
            if (str.contains(spams)) {
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType negativeFilter(String str) {
        for (String negatives : this.negatives) {
            if (str.contains(negatives)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return FilterType.GOOD;
    }


    // фильтр, проверяющий одинкаовость строк с помощью редакционного расстояния; допустимый процент различия между строками = 20%
    public FilterType levenshteinFilter(String str) {
        int N = str.length() + 1;
        int M = this.str2.length() + 1;
        long[][] D = new long[N][M];
        for (int i = 0; i < M; i++) {
            D[0][i] = i;
        }
        for (int i = 0; i < N; i++) {
            D[i][0] = i;
        }
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                if (this.str2.charAt(j - 1) == str.charAt(i - 1)) {
                    D[i][j] = D[i - 1][j - 1];
                } else {
                    D[i][j] = Math.min(D[i - 1][j], Math.min(D[i][j - 1], D[i - 1][j - 1])) + 1;
                }
            }
        }
//        for(int i = 0; i<N; i++){
//            for(int j = 0; j<M; j++){
//                System.out.print(D[i][j]+" ");
//            }
//            System.out.println();
//        }
//        System.out.println(D[N-1][M-1]);
        if ((double) D[N - 1][M - 1] / Math.max(this.str2.length(), str.length()) > 0.2) {
            return FilterType.NO_SIMILAR_STRING;
        }
        return FilterType.GOOD;
    }

}
