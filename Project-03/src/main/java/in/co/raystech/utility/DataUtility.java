package in.co.raystech.utility;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * data Uility class to formate data
 * 
 * @author Vikas Singh
 *
 */
public class DataUtility {
	/**
	 * Application time data formate
	 */
	public static final String APP_DATE_FORMATE = "MM/dd/yyyy";

	public static final String APP_TIME_FORMATE = "MM/dd/yyyy HH:mm:ss";

	/**
	 * Applicaton time data formate
	 */
	public static final SimpleDateFormat formatter = new SimpleDateFormat(APP_DATE_FORMATE);
	public static final SimpleDateFormat timeFormatter = new SimpleDateFormat(APP_TIME_FORMATE);

	/**
	 * getString(String s) Trims and trailing and leading spaces of a String
	 *
	 * 
	 */
	public static String getString(String val) {
		if (DataValidator.isNotNull(val)) {
			return val.trim();
		} else {
			return val;
		}
	}

	/**
	 * Converts and Object to String
	 *
	 * 
	 */

	public static String getStringData(Object val) {
		if (val != null) {
			return val.toString();
		} else {
			return "";
		}
	}

	/**
	 *
	 * Converts String InTo Integer
	 *
	 *
	 */

	public static int getInt(String val) {
		if (DataValidator.isInteger(val)) {
			return Integer.parseInt(val);
		} else {
			return 0;
		}
	}

	/**
	 *
	 * Converts String InTo Long
	 *
	 */

	public static int i(String val) {
		System.out.println("........in dataUtility..........." + val);
		if (DataValidator.isLong(val)) {
			System.out.println("........in dataUtility" + val + ",,,,,," + Long.parseLong(val));
			return (int) Long.parseLong(val);
		} else {
			return (int) 0;
		}
	}

	/**
	 * Convert String into Date
	 *
	 * 
	 */

	public static Date getDate(String val) {
		System.out.println("val--" + val);
		Date date = null;
		try {
			date = formatter.parse(val);

		} catch (Exception e) {
		}
		System.out.println(".date---" + date);
		return date;
	}

	/**
	 * convert string to date
	 *
	 */
	public static String getDateString(Date date) {
		try {
			return formatter.format(date);
		} catch (Exception e) {

		}
		return "";

	}

	/**
	 * convert date and time
	 * 
	 */
	public static Date getDate(Date date, int day) {
		return null;
	}

	/**
	 * convert timestamp to string
	 *
	 */
	public static Timestamp geTimestamp(String val) {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(timeFormatter.parse(val).getTime());

		} catch (Exception e) {
			return null;
		}
		return timeStamp;

	}

	/**
	 * convert timestamp in to long
	 *
	 */
	public static Timestamp getTimeStamp(long l) {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(l);

		} catch (Exception e) {
			return null;
		}
		return timeStamp;
	}

	/**
	 * convert timestamp in to string
	 *
	 */
	public static Timestamp getCurrentTimeStamp() {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(new Date().getTime());
		} catch (Exception e) {

		}
		return timeStamp;

	}

	/**
	 * convert timestamp timestamp to long
	 * 
	 */
	public static long getTimestamp(Timestamp tm) {
		try {
			return tm.getTime();
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 *
	 * Converts String InTo Long
	 *
	 */

	public static Long getLong(String val) {
		System.out.println("........in dataUtility..........." + val);
		if (DataValidator.isLong(val)) {
			System.out.println("........in dataUtility" + val + ",,,,,," + Long.parseLong(val));
			return Long.parseLong(val);
		} else {
			return (long) 0;
		}
	}

}