package de.jibin.poi.de.jibin;

import java.io.File;
import java.util.List;

import org.junit.Test;

import de.jibin.commons.Constants;
import de.jibin.db.morphia.entity.CommunityType;
import de.jibin.db.morphia.entity.FirstHandRecord;
import de.jibin.poi.ReadExcel.ExcelFileReader;
import junit.framework.TestCase;

public class ExcelFileReaderTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
	public ExcelFileReaderTest(String name)
	{
		super(name);
	}
	
	@Test
	public void testReadCommunityType()
	{
		File file = new File("res/testdata/ExcelFileReader/2010_08_01_beijing.xls");
		List<CommunityType> lists = ExcelFileReader.readFileToCommunityType(file);
		assertEquals("should be " + 80, 80, lists.size());
		CommunityType record = lists.get(0);
		assertEquals("name is " + "加州水郡", "加州水郡", record.getName());
		assertEquals("district is " + "房山", "房山", record.getDistrict());
		assertEquals("city is " + Constants.BEIJING_STRING, Constants.BEIJING_STRING, record.getCity());
		
		record = lists.get(1);
		assertEquals("name is " + "绿地中央广场·新里西斯莱公馆", "绿地中央广场·新里西斯莱公馆", record.getName());
		assertEquals("district is " + "大兴", "大兴", record.getDistrict());
		assertEquals("city is " + Constants.BEIJING_STRING, Constants.BEIJING_STRING, record.getCity());
		
		record = lists.get(2);
		assertEquals("name is " + "香悦四季", "香悦四季", record.getName());
		assertEquals("district is " + "顺义", "顺义", record.getDistrict());
		assertEquals("city is " + Constants.BEIJING_STRING, Constants.BEIJING_STRING, record.getCity());
		
		record = lists.get(79);
		assertEquals("name is " + "腾龙家园", "腾龙家园", record.getName());
		assertEquals("district is " + "房山", "房山", record.getDistrict());
		assertEquals("city is " + Constants.BEIJING_STRING, Constants.BEIJING_STRING, record.getCity());
		
		file = new File("res/testdata/ExcelFileReader/2010_08_02_beijing.xls");
		lists = ExcelFileReader.readFileToCommunityType(file);
		assertTrue(lists.isEmpty());
		
		file = new File("res/testdata/ExcelFileReader/2010_08_03_beijing.xls");
		lists = ExcelFileReader.readFileToCommunityType(file);
		assertTrue(lists.isEmpty());
		
		file = new File("res/testdata/ExcelFileReader/2010_08_04_beijing.xls");
		lists = ExcelFileReader.readFileToCommunityType(file);
		assertTrue(lists.isEmpty());
	}
	
	@Test
	public void testReadFirstHandRecord()
	{
		File file = new File("res/testdata/ExcelFileReader/2010_08_01_beijing.xls");
		List<FirstHandRecord> lists = ExcelFileReader.readFileToFirstHandRecord(file);
		//test normal case
		/*
		 * 0  加州水郡	房山	99	8764	11175.5079	97942151	3970	338248	398	29669	0
		 * 1 绿地中央广场·新里西斯莱公馆	大兴	29	3163	19269.5077	60949453	1812	196225	294	48219	0
         * 3 香悦四季	顺义	20	2347	15131.3068	35513177	967	113160	524	69251	0
		 * 79 腾龙家园	房山	1	92	2395.3913	220376	265	32305	206	8351	0
		 */
		assertEquals("should be " + 80, 80, lists.size());
		FirstHandRecord record = lists.get(0);
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
		
		record = lists.get(1);
		assertEquals("name is " + "绿地中央广场·新里西斯莱公馆", "绿地中央广场·新里西斯莱公馆", record.getName());
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
		
		record = lists.get(2);
		assertEquals("name is " + "香悦四季", "香悦四季", record.getName());
		assertEquals("district is " + "顺义", "顺义", record.getDistrict());
		assertEquals("daily sold count is " + 20, 20, record.getDailySoldCount());
		assertEquals("daily sold size is " + 2347, 2347, record.getDailyTotalSize());
		assertEquals("daily ave price is " + 15131, 15131, record.getDailyAveragePrice());
		assertEquals("daily total price is " + 35513177, 35513177, record.getDailyTotalValue());
		assertEquals("total sold count is " + 967, 967, record.getTotalSoldCount());
		assertEquals("total sold size is " + 113160, 113160, record.getTotalSoldSize());
		assertEquals("remain count is " + 524, 524, record.getRemainCount());
		assertEquals("remain size is " + 69251, 69251, record.getRemainSize());
		assertEquals("drop count is " + 0, 0, record.getDropCount());
		assertEquals("date is " + "2010-08-01", "2010-08-01", record.getDate());
		assertEquals("city is " + Constants.BEIJING_STRING, Constants.BEIJING_STRING, record.getCity());
		
		record = lists.get(79);
		assertEquals("name is " + "腾龙家园", "腾龙家园", record.getName());
		assertEquals("district is " + "房山", "房山", record.getDistrict());
		assertEquals("daily sold count is " + 1, 1, record.getDailySoldCount());
		assertEquals("daily sold size is " + 92, 92, record.getDailyTotalSize());
		assertEquals("daily ave price is " + 2395, 2395, record.getDailyAveragePrice());
		assertEquals("daily total price is " + 220376, 220376, record.getDailyTotalValue());
		assertEquals("total sold count is " + 265, 265, record.getTotalSoldCount());
		assertEquals("total sold size is " + 32305, 32305, record.getTotalSoldSize());
		assertEquals("remain count is " + 206, 206, record.getRemainCount());
		assertEquals("remain size is " + 8351, 8351, record.getRemainSize());
		assertEquals("drop count is " + 0, 0, record.getDropCount());
		assertEquals("date is " + "2010-08-01", "2010-08-01", record.getDate());
		assertEquals("city is " + Constants.BEIJING_STRING, Constants.BEIJING_STRING, record.getCity());
		
		file = new File("res/testdata/ExcelFileReader/2010_08_02_beijing.xls");
		lists = ExcelFileReader.readFileToFirstHandRecord(file);
		assertTrue(lists.isEmpty());
		
		file = new File("res/testdata/ExcelFileReader/2010_08_03_beijing.xls");
		lists = ExcelFileReader.readFileToFirstHandRecord(file);
		assertTrue(lists.isEmpty());
		
		file = new File("res/testdata/ExcelFileReader/2010_08_04_beijing.xls");
		lists = ExcelFileReader.readFileToFirstHandRecord(file);
		assertTrue(lists.isEmpty());
		
		
	}
}
