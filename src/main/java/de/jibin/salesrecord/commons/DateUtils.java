package de.jibin.salesrecord.commons;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Helper class for string to date and date to string conversion
 * 
 */
public class DateUtils {

	private final static String FORMAT_OFFSET = "[Z|(+|-)hh:mm]";
	private final static String FORMAT_TIME = "hh:mm:ss" + FORMAT_OFFSET;
	private final static String FORMAT_DATE = "CCYY-MM-DD" + FORMAT_OFFSET;
	private final static String FORMAT_DATETIME = "CCYY-MM-DDThh:mm:ss" + FORMAT_OFFSET;

	private final static long DIV_SECOND = 1000;
	private final static long DIV_MINUTE = DIV_SECOND * 60;
	private final static long DIV_HOUR = DIV_MINUTE * 60;
	private final static long DIV_DAY = DIV_HOUR * 24;

	private final static String TZ_Z = "Z";
	private final static String TZ_PLUS = "+";
	private final static String TZ_MINUS = "-";
	private final static String TZ_UNDERSCORE = "_";

	public final static TimeZone UTC = TimeZone.getTimeZone("UTC");

    /**
     * Dynamically adds leading zeros to the String until the specified length
     * is reached.
     * 
     * @param length
     *            How long the String should be in the end
     * @param input
     *            The input String which will get the zeros appended to
     * @return The result String with leading zeros
     */
    private static String _addLeadingZeros(final int length, final String input)
    {
        final int numZeros = length - input.length();

        final StringBuffer result = new StringBuffer();
        for (int i = 0; i < numZeros; i++)
        {
            result.append("0");
        }
        result.append(input);
        return result.toString();
    }
    
	/**
	 * Checks if the given String is empty or null.
	 * 
	 * @param str
	 * @return
	 */
	private static boolean _isEmptyString(final String str) {
		if (str == null)
			return true;
		return str.trim().length() <= 0;
	}

	/**
	 * CCYY-MM-DD[Z|(+|-)hh:mm]
	 * 
	 * @param val
	 *            String in the format CCYY-MM-DD[Z|(+|-)hh:mm]
	 * @param ignoreTimeZone
	 *            True: TimeZone will be completely ignored. No adjustments are
	 *            done. False: Offset will be parsed from the input String and
	 *            the date is adjusted accordingly
	 * @return A Date Object containing the date found in the input String.
	 */
	public static final Date convertString2Date(final String val, final boolean ignoreTimeZone)
			throws RuntimeException {
		if (!_isEmptyString(val)) {
			final String tmp = val.trim().replace(TZ_UNDERSCORE, TZ_MINUS);
			// 2011-10-05+05:00
			// 2011-10-05-05:00
			// 2011-10-05Z
			// 2011-10-05 if timezone is ignored
			final int dash1Index = tmp.indexOf(TZ_MINUS);
			final int dash2Index = tmp.indexOf(TZ_MINUS, dash1Index + 1);

			if (dash1Index < 0 || dash2Index < 0)
				throw new IllegalArgumentException(
						"could not find 2 dashes in: " + val + ". Please correct the format to " + FORMAT_TIME);

			if (dash2Index <= dash1Index)
				throw new IllegalArgumentException("dash1(" + dash1Index + ") <= dash2(" + dash2Index
						+ "). Please correct the format of: " + val + " to " + FORMAT_TIME);

			final String year = tmp.substring(0, dash1Index);
			final String month = tmp.substring(dash1Index + 1, dash2Index);
			int dayOfMonth = 0;
			String day;
			if (!ignoreTimeZone) {
				int tzIndex = tmp.indexOf(TZ_Z, dash2Index + 1);
				if (tzIndex < 0)
					tzIndex = tmp.indexOf(TZ_PLUS, dash2Index + 1);
				if (tzIndex < 0)
					tzIndex = tmp.indexOf(TZ_MINUS, dash2Index + 1);
				if (tzIndex < 0 && !ignoreTimeZone)
					throw new RuntimeException(
							"DateTime format is not correct: " + val + ", correct format is " + FORMAT_DATE);

				day = tmp.substring(dash2Index + 1, tzIndex);
				try {
					dayOfMonth = Integer.parseInt(day);
				} catch (final NumberFormatException nfe) {
					throw new RuntimeException("Exception upon parsing the day in method convertString2Date");
				}
			} else {
				day = tmp.substring(dash2Index + 1, dash2Index + 3);
				try {
					dayOfMonth = Integer.parseInt(day);
				} catch (final NumberFormatException nfe) {
					throw new RuntimeException(
							"ignoreTimeZone = true but the String contains a TimeZone suffix: " + val);
				}
			}

			try {
				return getUTCTime(Integer.parseInt(year), Integer.parseInt(month) - 1, dayOfMonth, 0, 0, 0, 0);
			} catch (final Exception e) {
				throw new RuntimeException(
						"Exception occured while parsing date, month and year in method convertString2Date. Please make sure you use the correct format "
								+ FORMAT_DATETIME);
			}
		}
		return null;
	}

	/**
	 * CCYY-MM-DD[Z|(+|-)hh:mm]
	 * 
	 * @param date
	 * @param currentTimeZone
	 *            The TimeZone in which the Date Object was created
	 * @param ignoreTimeZone
	 *            True: TimeZone will be completely ignored. No adjustments are
	 *            done. False: Offset will be parsed from the input String and
	 *            the date is adjusted accordingly
	 * @return A String representation of the date in the format
	 *         CCYY-MM-DD[Z|(+|-)hh:mm]
	 */
	public static final String convertDate2String(final Date date, final TimeZone currentTimeZone,
			final boolean ignoreTimeZone) {
		int cal[] = getUTCTimeCompoments(date);

		final StringBuffer timeString = new StringBuffer();
		timeString.append(_addLeadingZeros(4, String.valueOf(cal[Calendar.YEAR])) + TZ_MINUS);
		timeString.append(_addLeadingZeros(2, String.valueOf(cal[Calendar.MONTH] + 1)) + TZ_MINUS);
		timeString.append(_addLeadingZeros(2, String.valueOf(cal[Calendar.DATE])));

		final int era = getEraFromYear(cal[Calendar.YEAR]);

		if (!ignoreTimeZone)
			timeString.append(getOffsetFromTimestamp(
					DateUtils.getTimezoneOffset(currentTimeZone, era, cal[Calendar.YEAR], cal[Calendar.MONTH],
							cal[Calendar.DATE], cal[Calendar.DAY_OF_WEEK], DateUtils.getMillisInDay(cal))));

		return timeString.toString();
	}

	public static String getOffsetFromTimestamp(final long offset) {
		final int hours = getOffsetHoursFromTimestamp(Math.abs(offset));
		final int minutes = getOffsetMinutesFromTimestamp(Math.abs(offset) - (hours * DIV_HOUR));
		final int sign = getOffsetSignFromTimestamp(offset);

		if (offset == 0)
			return TZ_Z;
		else {
			final String neg = (sign < 0) ? TZ_MINUS : TZ_PLUS;

			return neg + _addLeadingZeros(2, String.valueOf(hours)) + ":"
					+ _addLeadingZeros(2, String.valueOf(minutes));
		}
	}

	private static int getOffsetSignFromTimestamp(final long offset) {
		if (offset >= 0)
			return 1;
		else
			return -1;
	}

	private static int getOffsetHoursFromTimestamp(final long offset) {
		final int hours = (int) (offset / DIV_HOUR);
		return hours;
	}

	private static int getOffsetMinutesFromTimestamp(final long offset) {
		final int minutes = (int) (offset / DIV_MINUTE);
		return minutes;
	}

	public static final int getMillisInDay(Calendar cal) {
		int hour = cal.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000;
		int min = cal.get(Calendar.MINUTE) * 60 * 1000;
		int sec = cal.get(Calendar.SECOND) * 1000;
		int millis = cal.get(Calendar.MILLISECOND);
		return hour + min + sec + millis;
	}

	public static final int getMillisInDay(int[] cal) {
		int hour = cal[Calendar.HOUR_OF_DAY] * 60 * 60 * 1000;
		int min = cal[Calendar.MINUTE] * 60 * 1000;
		int sec = cal[Calendar.SECOND] * 1000;
		int millis = cal[Calendar.MILLISECOND];
		return hour + min + sec + millis;
	}

	public static int getTimezoneOffset(TimeZone timeZone, int era, int year, int month, int day, int dayOfWeek,
			int milliseconds) {
		return timeZone.getOffset(era, year, month, day, dayOfWeek, milliseconds);
	}

	public static Date getUTCTime(int year, int month, int day, int hour, int min, int sec, int millis) {
		Calendar cal = Calendar.getInstance(DateUtils.UTC);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, min);
		cal.set(Calendar.SECOND, sec);
		cal.set(Calendar.MILLISECOND, millis);
		return cal.getTime();
	}

	public static int[] getUTCTimeCompoments(Date date) {
		Calendar cal = Calendar.getInstance(DateUtils.UTC);
		cal.setTime(date);
		int[] ret = new int[Calendar.FIELD_COUNT];
		for (int i = 0; i < Calendar.FIELD_COUNT; i++) {
			try {
				ret[i] = cal.get(i);
			} catch (Throwable th) {
				// Catch any exception if there are not filled fields
			}
		}
		return ret;
	}

	public static int getEraFromYear(final int year) {
		return year < 0 ? 0 : 1;
	}
}
