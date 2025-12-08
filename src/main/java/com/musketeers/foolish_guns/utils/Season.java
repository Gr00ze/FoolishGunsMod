package com.musketeers.foolish_guns.utils;

import java.time.LocalDate;
import java.time.Month;

public class Season {
    public enum SeasonalMode {HALLOWEEN, CHRISTMAS, NORMAL}
    public static  SeasonalMode getSeasonalMode() {
        LocalDate d = LocalDate.now();
        Month m = d.getMonth();
        int day = d.getDayOfMonth();

        if (m == Month.OCTOBER && day == 31) return SeasonalMode.HALLOWEEN;
        if (m == Month.DECEMBER && (day == 24 || day == 25)) return SeasonalMode.CHRISTMAS;
        return SeasonalMode.CHRISTMAS;
    }
}
