package de.jibin.poi.de.jibin;

import java.io.File;
import java.util.List;

import org.junit.Test;

import de.jibin.commons.Constants;
import de.jibin.db.morphia.DatabaseManager;
import de.jibin.db.morphia.entity.CommunityType;
import de.jibin.db.morphia.entity.FirstHandRecord;
import de.jibin.poi.ReadExcel.ExcelFileReader;
import junit.framework.TestCase;

public class DatabaseManagerTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
	public DatabaseManagerTest(String name)
	{
		super(name);
	}
	
	@Test
	public void testDatabaseConnection()
	{
		DatabaseManager manager = new DatabaseManager(Constants.LOCAL_IP_ADDR, Constants.TEST_DB_NAME);
		manager.wipeAll();
		assertEquals("Community type should be empty ", 0, manager.getCommunityDAO().findAll().size());
		assertEquals("Room type should be empty ", 0, manager.getRoomDAO().findAll().size());
		assertEquals("First hand record should be empty ", 0, manager.getFhRecordDAO().findAll().size());
		assertEquals("Second hand record should be empty ", 0, manager.getShRecordDAO().findAll().size());
		manager.close();
	}
	
	@Test
	public void testCommunityTypeDAO()
	{
		DatabaseManager manager = new DatabaseManager(Constants.LOCAL_IP_ADDR, Constants.TEST_DB_NAME);
		manager.wipeAll();
		File file = new File("res/testdata/ExcelFileReader/2010_08_01_beijing.xls");
		List<CommunityType> infos = ExcelFileReader.readFileToCommunityType(file);
		//there are 80 entries.
		for(CommunityType info : infos)
		{
			if(!manager.getCommunityDAO().isCommunityTypeExist(info.getName(), info.getDistrict(), info.getCity()))
				manager.getCommunityDAO().doCreate(info);
		}
		
		assertEquals("should be " + 80, 80, manager.getCommunityDAO().findAll().size());
		manager.close();
		
		//close and reopen it should be the same
		manager = new DatabaseManager(Constants.LOCAL_IP_ADDR, Constants.TEST_DB_NAME);
		assertEquals("should be " + 80, 80, manager.getCommunityDAO().findAll().size());
		manager.close();
	}
	
	@Test
	public void testFirstHandRecordDAO()
	{
		DatabaseManager manager = new DatabaseManager(Constants.LOCAL_IP_ADDR, Constants.TEST_DB_NAME);
		manager.wipeAll();
		File file = new File("res/testdata/ExcelFileReader/2010_08_01_beijing.xls");
		List<FirstHandRecord> records = ExcelFileReader.readFileToFirstHandRecord(file);
		//there are 80 entries.
		manager.getFhRecordDAO().doCreate(records);
		assertEquals("should be " + 80, 80, manager.getFhRecordDAO().findAll().size());
		manager.close();
		
		//close and reopen it should be the same
		manager = new DatabaseManager(Constants.LOCAL_IP_ADDR, Constants.TEST_DB_NAME);
		assertEquals("should be " + 80, 80, manager.getFhRecordDAO().findAll().size());
		manager.close();
		
		manager = new DatabaseManager(Constants.LOCAL_IP_ADDR, Constants.TEST_DB_NAME);
		manager.wipeAll();
		
		List<CommunityType> infos = ExcelFileReader.readFileToCommunityType(file);
		for(CommunityType info : infos)
		{
			if(!manager.getCommunityDAO().isCommunityTypeExist(info.getName(), info.getDistrict(), info.getCity()))
				manager.getCommunityDAO().doCreate(info);
		}
		
		records = ExcelFileReader.readFileToFirstHandRecord(file);
		for(FirstHandRecord record : records)
		{
			CommunityType info = manager.getCommunityDAO().findByExactName(record.getName(), record.getDistrict(), record.getCity());
			if(info != null)
				record.setInfo(info);
		}
		manager.getFhRecordDAO().doCreate(records);
		
		records = manager.getFhRecordDAO().findByName("加州水郡");
		FirstHandRecord record = records.get(0);
		assertEquals("name is " + "加州水郡", "加州水郡", record.getName());
		assertEquals("district is " + "房山", "房山", record.getDistrict());
		assertEquals("daily sold count is " + 99, 99, record.getDailySoldCount());
		assertEquals("daily sold size is " + 8764, 8764, record.getDailyTotalSize());
		assertEquals("daily ave price is " + 11175, 11175, record.getDailyAveragePrice());
		assertEquals("daily total price is " + 97942151, 97942151, record.getDailyTotalValue());
		assertEquals("total sold count is " + 3970, 3970, record.getTotalSoldCount());
		assertEquals("total sold size is " + 338248, 338248, record.getTotalSoldSize());
		assertEquals("remain count is " + 398, 398, record.getRemainCount());
		assertEquals("remain size is " + 29669, 29669, record.getRemainSize());
		assertEquals("drop count is " + 0, 0, record.getDropCount());
		assertEquals("date is " + "2010-08-01", "2010-08-01", record.getDate());
		assertEquals("city is " + Constants.BEIJING_STRING, Constants.BEIJING_STRING, record.getCity());
		assertEquals("info name is " + "加州水郡", "加州水郡", record.getInfo().getName());
		assertEquals("info district is " + "房山", "房山", record.getInfo().getDistrict());
		assertEquals("info city is " + Constants.BEIJING_STRING, Constants.BEIJING_STRING, record.getInfo().getCity());
		assertEquals("info communityID should be equal",  record.getCommunityID(), record.getInfo().getId());
		
		records = manager.getFhRecordDAO().findByName("绿地中央广场·新里西斯莱公馆");
		record = records.get(0);
		assertEquals("district is " + "大兴", "大兴", record.getDistrict());
		assertEquals("daily sold count is " + 29, 29, record.getDailySoldCount());
		assertEquals("daily sold size is " + 3163, 3163, record.getDailyTotalSize());
		assertEquals("daily ave price is " + 19269, 19269, record.getDailyAveragePrice());
		assertEquals("daily total price is " + 60949453, 60949453, record.getDailyTotalValue());
		assertEquals("total sold count is " + 1812, 1812, record.getTotalSoldCount());
		assertEquals("total sold size is " + 196225, 196225, record.getTotalSoldSize());
		assertEquals("remain count is " + 294, 294, record.getRemainCount());
		assertEquals("remain size is " + 48219, 48219, record.getRemainSize());
		assertEquals("drop count is " + 0, 0, record.getDropCount());
		assertEquals("date is " + "2010-08-01", "2010-08-01", record.getDate());
		assertEquals("city is " + Constants.BEIJING_STRING, Constants.BEIJING_STRING, record.getCity());
		assertEquals("info name is " + "绿地中央广场·新里西斯莱公馆", "绿地中央广场·新里西斯莱公馆", record.getInfo().getName());
		assertEquals("info district is " + "大兴", "大兴", record.getInfo().getDistrict());
		assertEquals("info city is " + Constants.BEIJING_STRING, Constants.BEIJING_STRING, record.getInfo().getCity());
		
		manager.close();
		
	}
}
