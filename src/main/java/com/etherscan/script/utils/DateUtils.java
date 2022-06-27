package com.etherscan.script.utils;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;

@Slf4j
public class DateUtils
{
    public static final String[] monthUaList =
        {
            "Січень",
            "Лютий",
            "Березень",
            "Квітень",
            "Травень",
            "Червень",
            "Липень",
            "Серпень",
            "Вересень",
            "Жовтень",
            "Листопад",
            "Грудень"
        };

    public static final String[] monthRuList =
        {
            "Январь",
            "Февраль",
            "Март",
            "Апрель",
            "Май",
            "Июнь",
            "Июль",
            "Август",
            "Сентябрь",
            "Октябрь",
            "Ноябрь",
            "Декабрь"
        };
    public static final String DATE_FORMAT_GENERAL = "dd-MM-yyyy";
    public static final String DATE_TIME_FORMAT_GENERAL = "dd-MM-yyyy HH:mm:ss";
    public static final String DATE_FORMAT_DATEPICKER_JS = "yyyy-MM-dd";
    public static final String DATE_FORMAT_BAIL = "yyyy-MM-dd";
    public static final String DATE_FORMAT_REST_API = "yyyy-MM-dd";
    public static final String DATE_FORMAT_2DIGITS_YEAR = "ddMMyy";

    public static DateTimeFormatter GENERAL_WITH_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_GENERAL);
    public static DateTimeFormatter GENERAL_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT_GENERAL);
    public static DateTimeFormatter BAIL_DATES_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT_BAIL);

    public static String formatWithUaMothName(Date date)
    {
        return formatWithMonthName(date, new Locale("ua", "UA"));
    }

    public static String formatWithMonthName(Date date, String locale)
    {
        return getConcatinatedString(date, getCal(locale), new Locale(checkLocaleName(locale)));
    }

    public static String formatWithMonthName(Date date, Locale locale)
    {
        return getConcatinatedString(date, getCal(locale), locale);
    }

    public static String formatWithMonthName(Date date)
    {
        return formatWithMonthName(date, "");
    }

    public static String format(Date date)
    {
        return format(date, DATE_FORMAT_GENERAL);
    }

    public static String format(LocalDateTime date)
    {
        return date.format(GENERAL_WITH_TIME_FORMATTER);
    }

    public static String format(LocalDateTime date, String pattern)
    {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String format(LocalDate date)
    {
        return date.format(GENERAL_FORMATTER);
    }

    public static String format(LocalDate date, String pattern)
    {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String format(Date date, String format)
    {
        return new SimpleDateFormat(format).format(date);
    }

    public static String format(long date, String format)
    {
        return new SimpleDateFormat(format).format(date);
    }

    public static String format(ZonedDateTime dateTime)
    {
        return dateTime.format(GENERAL_FORMATTER);
    }

    public static Date parse(String date)
    {
        return parse(date, DATE_FORMAT_GENERAL);
    }

    public static Date parse(String date, String format)
    {
        Date result = null;
        try
        {
            result = new SimpleDateFormat(format).parse(date);
        }
        catch (ParseException e)
        {
            log.warn("Cannot parse date from string '" + date + "'");
        }
        return result;
    }

    public static boolean isDate(String date)
    {
        return isDate(date, DATE_FORMAT_GENERAL);
    }

    public static boolean isDate(String date, String format)
    {
        try
        {
            new SimpleDateFormat(format).parse(date);
        }
        catch (ParseException e)
        {
            return false;
        }
        return true;
    }


    public static int getDay(Date date)
    {
        Calendar cal = getCal();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getMonth(Date date)
    {
        Calendar cal = getCal();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public static int getYear(Date date)
    {
        Calendar cal = getCal();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static List<Integer> getMonthListByQuarter(int quarter)
    {
        List<Integer> result = new ArrayList<>();
        result.add(0 + ((quarter - 1)) * 3);
        result.add(1 + ((quarter - 1)) * 3);
        result.add(2 + ((quarter - 1)) * 3);
        return result;
    }

    public static Date getCurrentDateWithoutTime()
    {
        Calendar cal = getCal();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date parseEndDateForReports(String toDate)
    {
        if (!StringUtils.isEmpty(toDate))
        {
            Date to = parse(toDate, DATE_FORMAT_BAIL);
            Calendar calendar = getCal();
            calendar.setTime(to);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        }
        return new Date();
    }

    /**
     * Используется для отчетов
     * Если дата начала не указана, возвращаем -2 мес
     */
    public static Date parseStartDateForReports(String fromDate)
    {
        if (!StringUtils.isEmpty(fromDate))
        {
            return parse(fromDate, DATE_FORMAT_BAIL);
        }
        else
        {
            Calendar c = getCal();
            c.add(Calendar.MONTH, -2);
            c.set(Calendar.DAY_OF_MONTH, 1);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            return c.getTime();
        }
    }

    public static Calendar getCal()
    {
        return getCal("ru");
    }

    public static Calendar getCal(String str)
    {
        return getCal(getLocale(str));
    }

    public static Locale getLocale()
    {
        return getLocale(null);
    }

    public static Locale getLocale(String str)
    {
        return new Locale(checkLocaleName(str));
    }

    public static Calendar getCal(Locale locale)
    {
        return new GregorianCalendar(locale);
    }

    public static String getCurrentDate()
    {
        return getCurrentDate(new Locale("ru", "RU"));
    }

    public static String getCurrentUADate()
    {
        return getCurrentDate(new Locale("ua", "UA"));
    }

    public static String getCurrentDate(Locale locale)
    {
        return formatWithMonthName(getCal().getTime(), locale);
    }

    public static Date convert(LocalDate dateToConvert)
    {
        return java.util.Date.from(dateToConvert.atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant());
    }

    public static Date convert(ZonedDateTime dateToConvert)
    {
        return java.util.Date.from(dateToConvert.toInstant());
    }

    public static LocalDate convert(Date dateToConvert)
    {
        if (dateToConvert instanceof java.sql.Date) return ((java.sql.Date) dateToConvert).toLocalDate();
        return dateToConvert.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    }

    public static LocalDateTime convertWithTime(Date dateToConvert)
    {
        if (dateToConvert instanceof java.sql.Date) return new Date(dateToConvert.getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return dateToConvert.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
    }

    public static int daysBetween(Date startDateInclusive, Date endDateExclusive)
    {
        return (int) ChronoUnit.DAYS.between(DateUtils.convert(startDateInclusive), DateUtils.convert(endDateExclusive));
    }

    public static int hoursBetween(Temporal startDateInclusive, Temporal endDateExclusive)
    {
        return (int) ChronoUnit.HOURS.between(startDateInclusive, endDateExclusive);
    }

//    public static boolean isInThePastTime(Date date){
//        if(Objects.isNull(date)){
//            return false;
//        }
//        Days days = Days.daysBetween(new LocalDate(date), new LocalDate(getCal().getTime()));
//        return days.getDays() > 0;
//    }

    private static String checkLocaleName(String locale)
    {
        if (locale == null || locale.isEmpty())
        {
            locale = "ru";
        }
        return locale;
    }

    private static String getConcatinatedString(Date date, Calendar calendar, Locale local)
    {
        ResourceBundle bundle = ResourceBundle.getBundle("com.gopawnshop.common.localization.calendar.Month", local);
        calendar.setTime(date);

        return calendar.get(Calendar.DAY_OF_MONTH) + " " + bundle.getString(
            String.valueOf(calendar.get(Calendar.MONTH))) + " " + calendar.get(Calendar.YEAR);
    }

    @Builder
    @Data
    public static class Period
    {
        private Date from;
        private Date to;
    }
}
