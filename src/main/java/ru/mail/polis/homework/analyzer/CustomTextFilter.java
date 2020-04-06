package ru.mail.polis.homework.analyzer;

public class CustomTextFilter implements TextAnalyzer {
    private String[] openedHtmlTags = {"<br>", "<i>", "<p>", "<html>", "<body>", "<head>", "href", "img", "title",
        "style", "<h1>", "<h2>", "<h3>", "<h4>", "<h5>", "<h6>", "<b>", "<td>", "<tr>"};
    private String[] closedHtmlTags = getClosedTags(openedHtmlTags);

    @Override
    public FilterType getTypeOfFilter(String str) {
        if ((FindInText.find(openedHtmlTags, str, FilterType.CUSTOM) == FilterType.CUSTOM) &&
            (FindInText.find(openedHtmlTags, str, FilterType.CUSTOM) == FilterType.CUSTOM)) {
            return FilterType.CUSTOM;
        }
        return FilterType.GOOD;
    }

    private String[] getClosedTags(String[] arr) {
        String[] resultArray = arr;
        for (int i = 0; i < resultArray.length; i++) {
            resultArray[i] = "</" + arr[i].substring(1);
        }
        return resultArray;
    }
}
