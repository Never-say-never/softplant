package com.softwareplant.api.module.report.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlHelper {

    /**
     *
     * @param url
     * @param name
     * @return
     */
    public static long getIdFromUrl(final String url, final String name) {
        final Pattern pattern = Pattern.compile(name + "\\/(\\d+)");
        final Matcher matcher = pattern.matcher(url);

        long id = 0;
        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }

        return id;
    }
}
