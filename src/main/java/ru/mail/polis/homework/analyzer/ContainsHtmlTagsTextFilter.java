package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class ContainsHtmlTagsTextFilter extends SpamTextFiler {
    private final static FilterType TYPE_FILTER = FilterType.CUSTOM;
    private final static String[] openedHtmlTags = {"<br>", "<i>", "<p>", "<html>", "<body>", "<head>", "href", "img", "title",
        "style", "<h1>", "<h2>", "<h3>", "<h4>", "<h5>", "<h6>", "<b>", "<td>", "<tr>"};

    private final static String[] tags = Arrays.copyOf(openedHtmlTags, openedHtmlTags.length * 2);
    private final static String[] closedHtmlTags = getClosedTags();

    static {
        System.arraycopy(closedHtmlTags, 0, tags, openedHtmlTags.length, closedHtmlTags.length);
    }

    public ContainsHtmlTagsTextFilter() {
        super(tags);
    }

    @Override
    public FilterType getFilterType() {
        return TYPE_FILTER;
    }

    private static String[] getClosedTags() {
        String[] resultArray = ContainsHtmlTagsTextFilter.openedHtmlTags;
        for (int i = 0; i < resultArray.length; i++) {
            resultArray[i] = "</" + ContainsHtmlTagsTextFilter.openedHtmlTags[i].substring(1);
        }
        return resultArray;
    }
}