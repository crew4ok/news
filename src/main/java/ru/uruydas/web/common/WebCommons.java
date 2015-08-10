package ru.uruydas.web.common;

import java.time.format.DateTimeFormatter;

public final class WebCommons {
    private WebCommons() { }

    public static final String VERSION_PREFIX = "/v1";

    public static final int PAGING_COUNT = 10;

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
}
