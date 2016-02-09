package de.jibin.poi.ReadExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import de.jibin.crawler.ResultParser;
import de.jibin.db.morphia.entity.CommunityType;
import de.jibin.db.morphia.entity.FirstHandRecord;


public class ExcelFileReader 
{
	private final static int COMMUNITY_NAME_INDEX = 0;
	private final static int DISTRICT_INDEX = 1;
	private final static int DAILY_COUNT_INDEX = 2;
	private final static int DAILY_SIZE_INDEX = 3;
	private final static int DAILY_AVE_PRICE_INDEX = 4;
	private final static int DAILY_TOTAL_PRICE_INDEX = 5;
	private final static int TOTAL_SOLD_COUNT_INDEX = 6;
	private final static int TOTAL_SOLD_SIZE_INDEX = 7;
	private final static int TOTAL_NOT_SOLD_COUNT_INDEX = 8;
	private final static int TOTAL_NOT_SOLD_SIZE_INDEX = 9;
	private final static int DROP_COUNT_INDEX = 10;
	
	public static List<CommunityType> readFileToCommunityType(File file)
	{
		//2010_08_10_beijing.xls
		final String[] tokens = file.getName().substring(0, file.getName().indexOf('.')).split("_");
		final String city = tokens[tokens.length - 1];
	    final List<CommunityType> records = new ArrayList<CommunityType>();
		try {
			final FileInputStream fileInputStream = new FileInputStream(file);
			final HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			final HSSFSheet sheet = workbook.getSheetAt(0);
			final Iterator<Row> rowIterator = sheet.iterator();
			
			int count = 0;
			for(count = 0;count < 3 && rowIterator.hasNext();count++)
			{
				Row row = rowIterator.next();
				if(!verifyHeader(row))
					return records;
			}
		
			while(rowIterator.hasNext())
			{
				Row row = rowIterator.next();
				CommunityType record = readRowToCommunityType(row);
				if(record.getName() != null && !record.getName().isEmpty())
				{
					record.setCity(city);
					records.add(record);
				}
			}
			return records;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return records;
	}
	
	/**
	 * 1    2   3   4   5   6       7   8   9   10              11     
	 * 名称	行政区域	已售统计				累计已售		累计未售		退房
       --	--	套数	面积	均价	销售金额	     套数	面积	         套数   面积	            次数
              合计	--	345	41921	19677.3093	824892482	156793	19358971	236521	29599799	0
	 * @param file
	 * @return
	 */
	public static List<FirstHandRecord> readFileToFirstHandRecord(File file)
	{
		//2010_08_10_beijing.xls
		final String[] tokens = file.getName().substring(0, file.getName().indexOf('.')).split("_");
		StringBuffer buffer = new StringBuffer();
		for(int i = 0;i<tokens.length - 1;i++)
		{
			buffer.append(tokens[i]);
			buffer.append("-");
		}
		final String dateString = buffer.toString().substring(0, buffer.length() - 1);
		final String city = tokens[tokens.length - 1];
	    final List<FirstHandRecord> records = new ArrayList<FirstHandRecord>();
		try {
			final FileInputStream fileInputStream = new FileInputStream(file);
			final HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			final HSSFSheet sheet = workbook.getSheetAt(0);
			final Iterator<Row> rowIterator = sheet.iterator();
			
			int count = 0;
			for(count = 0;count < 3 && rowIterator.hasNext();count++)
			{
				Row row = rowIterator.next();
				if(!verifyHeader(row))
					return records;
			}
		
			while(rowIterator.hasNext())
			{
				Row row = rowIterator.next();
				FirstHandRecord record = readRowToFirstHandRecord(row);
				if(record.getDailySoldCount() > 0)
				{
					record.setDate(dateString);
					record.setCity(city);
					records.add(record);
				}
			}
			return records;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return records;
	}
	
	public static boolean verifyHeader(Row row)
	{
		//Get iterator to all cells of current row
		Iterator<Cell> cellIterator = row.cellIterator();
		while(cellIterator.hasNext())
		{
			Cell cell = cellIterator.next();
		    String string = cell.getStringCellValue();
		    if(string != null && !string.isEmpty() )
		    	return true;
		}
		return false;
	}
	
	//Example is for Beijing, 20100913.xls
	//e.g.  1       2   3   4       5           6           7       8       9   10      11
	//
	//      加州水郡	房山	99	8764	11175.5079	97942151	3970	338248	398	29669	0
	public static CommunityType readRowToCommunityType(Row row)
	{
		//Get iterator to all cells of current row
		Iterator<Cell> cellIterator = row.cellIterator();
		final CommunityType record = new CommunityType();
		int i = 0;
		while(cellIterator.hasNext())
		{
			Cell cell = cellIterator.next();
		    String string = cell.getStringCellValue();
			switch(i)
			{
				case COMMUNITY_NAME_INDEX :
					record.setName(ResultParser.getChineseName(string));
					break;
				case DISTRICT_INDEX :
					record.setDistrict(ResultParser.getChineseName(string));
					break;
				case DAILY_COUNT_INDEX :
					if(getNumber(string) == 0)
						record.setName("");
					break;
				default:
					break;
			}
			i++;
		}
		return record;
	}
	
	//Example is for Beijing, 20100913.xls
	//e.g.  1       2   3   4       5           6           7       8       9   10      11
	//
	//      加州水郡	房山	99	8764	11175.5079	97942151	3970	338248	398	29669	0
	public static FirstHandRecord readRowToFirstHandRecord(Row row)
	{
		//Get iterator to all cells of current row
		Iterator<Cell> cellIterator = row.cellIterator();
		final FirstHandRecord record = new FirstHandRecord();
		int i = 0;
		while(cellIterator.hasNext())
		{
			Cell cell = cellIterator.next();
		    String string = cell.getStringCellValue();
			switch(i)
			{
				case COMMUNITY_NAME_INDEX :
					record.setName(ResultParser.getChineseName(string));
					break;
				case DISTRICT_INDEX :
					record.setDistrict(ResultParser.getChineseName(string));
					break;
				case DAILY_COUNT_INDEX :
					record.setDailySoldCount(getNumber(string));
					break;
				case DAILY_SIZE_INDEX :
					record.setDailyTotalSize(getNumber(string));
					break;
				case DAILY_AVE_PRICE_INDEX :break;
				case DAILY_TOTAL_PRICE_INDEX :
					record.setDailyTotalValue(getNumber(string));
					break;
				case TOTAL_SOLD_COUNT_INDEX :
					record.setTotalSoldCount(getNumber(string));
					break;
				case TOTAL_SOLD_SIZE_INDEX :
					record.setTotalSoldSize(getNumber(string));
					break;
				case TOTAL_NOT_SOLD_COUNT_INDEX :
					record.setRemainCount(getNumber(string));
					break;
				case TOTAL_NOT_SOLD_SIZE_INDEX :
					record.setRemainSize(getNumber(string));
					break;
				case DROP_COUNT_INDEX :
					record.setDropCount(getNumber(string));
					break;
				default:
					break;
			}
			i++;
		}
		return record;
	}
	
	private static int getNumber(String string)
	{
		double count = 0;
	    try{
	    	count = Double.valueOf(string);
	    }catch(Exception e)
	    {/*ignore*/}
	    return (int)count;
	}
}
