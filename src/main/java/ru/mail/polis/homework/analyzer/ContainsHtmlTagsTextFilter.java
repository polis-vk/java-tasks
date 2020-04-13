package ru.mail.polis.homework.analyzer;

public class ContainsHtmlTagsTextFilter extends SpamTextFiler {
    private final static FilterType TYPE_FILTER = FilterType.CUSTOM;
    private final static String[] openedHtmlTags = {"<br>", "<i>", "<p>", "<html>", "<body>", "<head>", "href", "img", "title",
        "style", "<h1>", "<h2>", "<h3>", "<h4>", "<h5>", "<h6>", "<b>", "<td>", "<tr>"};
    private final static String[] closedHtmlTags = getClosedTags();

    public ContainsHtmlTagsTextFilter() {
        super(openedHtmlTags);
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.CUSTOM;
    }

    @Override
    public FilterType analyze(String str) {
        if ((findInText( str, TYPE_FILTER) == TYPE_FILTER) ||
            (findInText( str, TYPE_FILTER) == TYPE_FILTER)) {
            return TYPE_FILTER;
        }
        return FilterType.GOOD;
    }

    private static String[] getClosedTags() {
        String[] resultArray = ContainsHtmlTagsTextFilter.openedHtmlTags;
        for (int i = 0; i < resultArray.length; i++) {
            resultArray[i] = "</" + ContainsHtmlTagsTextFilter.openedHtmlTags[i].substring(1);
        }
        return resultArray;
    }
}
