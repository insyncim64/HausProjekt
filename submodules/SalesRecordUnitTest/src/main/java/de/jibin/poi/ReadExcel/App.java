package de.jibin.poi.ReadExcel;

import java.io.File;
import java.util.List;

import de.jibin.commons.Constants;
import de.jibin.db.morphia.DatabaseManager;
import de.jibin.db.morphia.entity.CommunityType;
import de.jibin.db.morphia.entity.FirstHandRecord;

/**
 * Hello world!
 *
 */
public class App 
{
	public void readFirstHandRecordFile(String[] args)
	{
		final String OPENSHIFT_MONGODB_DB_PORT = args[0];
		final String OPENSHIFT_MONGODB_DB_NAME = args[1];
		final String OPENSHIFT_MONGODB_DB_USERNAME = args[2];
		final String OPENSHIFT_MONGODB_DB_PASSWORD = args[3];
		//create database
		DatabaseManager manager = new DatabaseManager("127.0.0.1", Integer.valueOf(OPENSHIFT_MONGODB_DB_PORT), OPENSHIFT_MONGODB_DB_NAME, OPENSHIFT_MONGODB_DB_USERNAME, OPENSHIFT_MONGODB_DB_PASSWORD);
		File file = new File("res/testdata/ExcelFileReader/2010_08_01_beijing.xls");
		List<FirstHandRecord> records = null;
		
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
	}
	
    public static void main( String[] args )
    {
        App app = new App();
        app.readFirstHandRecordFile(args);
    }
}
