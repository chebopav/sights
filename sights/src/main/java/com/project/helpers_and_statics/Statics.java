package com.project.helpers_and_statics;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Statics {

    public static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24L;

    public static final LocalDate UPDATE_DATE = LocalDate.now().plus(14, ChronoUnit.DAYS);
}