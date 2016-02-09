package de.jibin.commons;

public class Constants 
{
	public final static int HEADING_EAST_WEST_TYPE = 0;
	public final static int HEADING_NORTH_SOUTH_TYPE = 1;
	
	public final static String CN_HIGH_FLOOR_STRING = "\u9ad8\u697c\u5c42"; //高楼层
	public final static String CN_MIDDLE_FLOOR_STRING = "\u4e2d\u697c\u5c42"; //中楼层
	public final static String CN_LOW_FLOOR_STRING = "\u4f4e\u697c\u5c42"; //低楼层
	public final static String CN_GOOD_DECORATED_STRING = "\u7CBE\u88C5"; //精装
	public final static String CN_NO_DECORATED_STRING = "\u6BDB\u576F"; //毛坯
	public final static String CN_MIDDLE_DECORATED_STRING = "\u4E2D\u88C5"; //中装
	public final static String CN_LOW_DECORATED_STRING = "\u7B80\u88C5"; //简装
	
	public final static char ROOM_CHAR = '\u5ba4'; //房
	public final static char HALL_CHAR = '\u5385'; //厅
	public final static char SQUARE_METER_CHAR = '\u33a1'; //㎡
	public final static char CN_LEFT_QUOTE_CHAR = '\uff08'; //（
	public final static char CN_RIGHT_QUOTE_CHAR = '\uff09'; //）
	public final static char CN_TOTAL_CHAR = '\u5171'; //共
	public final static char CN_FLOOR_CHAR = '\u5c42'; //层
	
	public final static String CN_EAST_STRING = "\u4E1C"; //东
	public final static String CN_WEST_STRING = "\u897F"; //西
	public final static String CN_NORTH_STRING = "\u5317"; //北
	public final static String CN_SOUTH_STRING = "\u5357"; //南
	
	public final static int GOOD_DECORATED_TYPE = 0;
	public final static int NO_DECORATED_TYPE = 1;
	public final static int MIDDLE_DECORATED_TYPE = 2;
	public final static int LOW_DECORATED_TYPE = 3;
	public final static int UNKNOWN_DECORATED_TYPE = -1;
	
	public final static int HIGH_FLOOR_TYPE = 0;
	public final static int MIDDLE_FLOOR_TYPE = 1;
	public final static int LOW_FLOOR_TYPE = 2;
	public final static int UNKNOWN_FLOOR_TYPE = -1;
	
	public final static int SOUTH_TYPE = 0;
	public final static int EAST_TYPE = 1;
	public final static int UNKNOWN_DIRECTION_TYPE = -1;
	
	public final static String BEIJING_STRING = "beijing";
	public final static String SHANGHAI_STRING = "shanghai";
	public final static String GUANGZHOU_STRING = "guangzhou";
	
	public static final String TEST_DB_NAME = "test_db";
	public static final String LOCAL_DB_NAME = "local_db";
	public static final String LOCAL_IP_ADDR = "127.0.0.1";
	public static final int LOCAL_DB_PORT = 27017;
	
	//public static final String OPENSHIFT_MONGODB_DB_NAME= "salesrecord";
	//public static final String OPENSHIFT_MONGODB_DB_PASSWORD= "xMf91nga9iWJ";
	//public static final int OPENSHIFT_MONGODB_DB_PORT= 27017;
	//public static final String OPENSHIFT_MONGODB_DB_URL= "mongodb://admin:xMf91nga9iWJ@127.4.221.130:27017/";
	//public static final String OPENSHIFT_MONGODB_DB_USERNAME= "admin";
}