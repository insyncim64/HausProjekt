package de.jibin.salesrecord.commons;

public class WebConstants 
{
	public final static String COMMUNITY_ID_STRING = "communityid";
	public final static String DATE_STRING = "date";
	public final static String COUNT_STRING = "count";
	public final static String NAME_STRING = "name";
	public final static String CITY_STRING = "city";
	public final static String DISTRICT_STRING = "district";
	
    public static final byte    CONSTRAINT_MASKDATE                   = (byte) 0x40;                 // 64
    /** allow only date entry */
    public static final byte    CONSTRAINT_DATE                       = (byte) 0x41;                 // 65
    /** allow only time entry */
    public static final byte    CONSTRAINT_TIME                       = (byte) 0x42;                 // 66
    /** allow date and time entry */
    public static final byte    CONSTRAINT_DATETIME                   = (byte) 0x43;                 // 67
}
