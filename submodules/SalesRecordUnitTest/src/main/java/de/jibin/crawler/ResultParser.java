package de.jibin.crawler;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import de.jibin.commons.Constants;

public class ResultParser 
{
	private static final Pattern TOTAL_FLOOR_PATTERN = Pattern.compile(".*" + Constants.CN_LEFT_QUOTE_CHAR + Constants.CN_TOTAL_CHAR + "+([0-9]+)" +  Constants.CN_FLOOR_CHAR+ Constants.CN_RIGHT_QUOTE_CHAR + ".*");
	private static final Pattern ROOM_PATTERN = Pattern.compile("^([0-9]+)" + Constants.ROOM_CHAR + ".*");
	private static final Pattern HALL_PATTERN = Pattern.compile("[^0-9]*([0-9]+)" + Constants.HALL_CHAR + ".*");
	private static final Pattern SIZE_PATTERN = Pattern.compile("[^0-9]*([0-9]+)" + Constants.SQUARE_METER_CHAR);
	private static final Pattern PRICE_PATTERN = Pattern.compile("[^0-9]*([0-9]+)[^0-9]*");
	public static final Pattern CHINESE_NAME_PATTERN = Pattern.compile("([\u4e00-\u9fa5\u00B7]+)");
	
	public void parseFile(File file)
	{
		try {
			final Document doc = Jsoup.parse(file, "GB2312", "http://esf.sh.fang.com/");
			final Element tableContent = doc.getElementsByTag("table").get(0);
			for(int i = 1; i < tableContent.children().size();i++)
			{
				final Element e = tableContent.child(i);
				if(e.children().size()==6)
				{
				    final Element infoElement = e.child(0);
				    final Element imageInfoElement = infoElement.child(0);
				    final String urlStr = imageInfoElement.attr("src");
				    
				    final Element detailInfoElement = infoElement.child(1);
				    final int[] counts = this.getCountInStr(detailInfoElement.child(0).text());
				    
				    final String floorInfoText = detailInfoElement.child(0).text();
				    final int floorType = this.getFloorType(floorInfoText);
				    final int totalFloorCount = this.getTotalFloorCount(floorInfoText);
				    final int decorateType = this.getDecorateType(floorInfoText);
				    
				    final Element priceElement = e.child(1);
				    final int price = this.getPrice(priceElement.text());
				    
				    final Element dateElement = e.child(3);
				    final String date = dateElement.text();
				    
				    final Element agentNameElement = e.child(4);
				    final String agentName = getChineseName(agentNameElement.text());
				    
				    final Element companyNameElement = e.child(5);
				    final String companyName = getChineseName(companyNameElement.text());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int[] getCountInStr(final String detailString)
	{
		final int[] counts = new int[3];
		for(int i=0;i<3;i++)
		{
			counts[i] = -1;
		}
		
		if(detailString == null)
			return counts;
		
		try{
			final Matcher roomMatcher = ROOM_PATTERN.matcher(detailString);
			if (roomMatcher.find()) 
			{
			    counts[0] = Integer.valueOf(roomMatcher.group(1));
			}
			final Matcher hallMatcher = HALL_PATTERN.matcher(detailString);
			if(hallMatcher.find())
			{
			    counts[1] = Integer.valueOf(hallMatcher.group(1));
			}
			final Matcher sizeMatcher = SIZE_PATTERN.matcher(detailString);
			if(sizeMatcher.find())
			{
			    counts[2] = Integer.valueOf(sizeMatcher.group(1));
			}
		}
		catch(Exception e)
		{
			//parsing error
			return counts;
		}
		return counts;
	}
	
	public int getTotalFloorCount(final String detailString)
	{
		int count = -1;
		if(detailString == null)
			return count;
		try{
			final Matcher roomMatcher = TOTAL_FLOOR_PATTERN.matcher(detailString);
			if (roomMatcher.find()) 
			{
			    count = Integer.valueOf(roomMatcher.group(1));
			}
		}
		catch(Exception e)
		{
			//parsing error
			return count;
		}
		return count;
	}
	
	public int getFloorType(final String detailString)
	{
		if(detailString == null)
			return Constants.UNKNOWN_FLOOR_TYPE;
	    if(detailString.contains(Constants.CN_HIGH_FLOOR_STRING))
	    {
	    	return Constants.HIGH_FLOOR_TYPE;
	    } 
	    else if(detailString.contains(Constants.CN_MIDDLE_FLOOR_STRING))
	    {
	    	return Constants.MIDDLE_FLOOR_TYPE;
	    } 
	    else if(detailString.contains(Constants.CN_LOW_FLOOR_STRING))
	    {
	    	return Constants.LOW_FLOOR_TYPE;
	    } 
	    else
	    {
	    	return Constants.UNKNOWN_FLOOR_TYPE;
	    }
	}
	
	public int getDecorateType(final String detailString)
	{
	    if(detailString == null)
	    	return Constants.UNKNOWN_DECORATED_TYPE;
	    if(detailString.contains(Constants.CN_GOOD_DECORATED_STRING))
	    {
	    	return Constants.GOOD_DECORATED_TYPE;
	    }else if(detailString.contains(Constants.CN_NO_DECORATED_STRING))
	    {
	    	return Constants.NO_DECORATED_TYPE;
	    }else if(detailString.contains(Constants.CN_MIDDLE_DECORATED_STRING))
	    {
	    	return Constants.MIDDLE_DECORATED_TYPE;
	    }else if(detailString.contains(Constants.CN_LOW_DECORATED_STRING))
	    {
	    	return Constants.LOW_DECORATED_TYPE;
	    }
	    else
	    {
	    	return Constants.UNKNOWN_DECORATED_TYPE;
	    }
	}
	
	public int getPrice(final String detailString)
	{
		if(detailString == null)
			return -1;
		
		int price = -1;
		try{
			final Matcher priceMatcher = PRICE_PATTERN.matcher(detailString);
			if(priceMatcher.matches())
			{
				price = Integer.valueOf(priceMatcher.group(1));
			}
		}
		catch(Exception e)
		{
			//parsing error
			return -1;
		}
		return price;
	}
	
	public int getRoomDirectionType(final String detailString)
	{
		if(detailString == null)
			return Constants.UNKNOWN_DIRECTION_TYPE;
		if(detailString.contains(Constants.CN_SOUTH_STRING))
		{
			return Constants.SOUTH_TYPE;
		}else if(detailString.contains(Constants.CN_EAST_STRING))
		{
			return Constants.EAST_TYPE;
		}else
		{
			return Constants.UNKNOWN_DIRECTION_TYPE;
		}
	}
	
	public static String getChineseName(final String detailString)
	{
		if(detailString == null)
			return "";
		
		try{
			final Matcher nameMatcher = CHINESE_NAME_PATTERN.matcher(detailString);
			if(nameMatcher.find())
			{
				return nameMatcher.group(1);
			}
			return "";
		}
		catch(Exception e)
		{
			//parsing error
			return "";
		}
	}
}
