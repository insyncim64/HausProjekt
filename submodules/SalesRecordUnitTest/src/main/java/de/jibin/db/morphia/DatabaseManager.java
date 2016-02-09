package de.jibin.db.morphia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.jibin.commons.Constants;
import de.jibin.db.morphia.dao.CommunityTypeDAO;
import de.jibin.db.morphia.dao.FirstHandRecordDAO;
import de.jibin.db.morphia.dao.RoomTypeDAO;
import de.jibin.db.morphia.dao.SecondHandRecordDAO;
import de.jibin.db.morphia.dao.impl.CommunityTypeDAOImpl;
import de.jibin.db.morphia.dao.impl.FirstHandRecordDAOImpl;
import de.jibin.db.morphia.dao.impl.RoomTypeDAOImpl;
import de.jibin.db.morphia.dao.impl.SecondHandRecordDAOImpl;
import de.jibin.db.morphia.entity.CommunityType;
import de.jibin.db.morphia.entity.FirstHandRecord;
import de.jibin.poi.ReadExcel.ExcelFileReader;

public class DatabaseManager 
{
	final DatabaseConnection  connection;
	final CommunityTypeDAO communityDAO;
	final FirstHandRecordDAO fhRecordDAO;
	final RoomTypeDAO roomDAO;
	final SecondHandRecordDAO shRecordDAO;
	
	public DatabaseManager()
	{
		this(Constants.LOCAL_IP_ADDR, Constants.LOCAL_DB_NAME);
	}
	
	public DatabaseManager(String ip, int port, String dbName, String user, String pw)
	{
		connection = new DatabaseConnection(ip, port, dbName, user, pw);
		communityDAO = new CommunityTypeDAOImpl(connection);
		fhRecordDAO = new FirstHandRecordDAOImpl(connection);
		shRecordDAO = new SecondHandRecordDAOImpl(connection);
		roomDAO = new RoomTypeDAOImpl(connection);
	}
	
	public DatabaseManager(String ip, String db)
	{
		connection = new DatabaseConnection(ip, db);
		communityDAO = new CommunityTypeDAOImpl(connection);
		fhRecordDAO = new FirstHandRecordDAOImpl(connection);
		shRecordDAO = new SecondHandRecordDAOImpl(connection);
		roomDAO = new RoomTypeDAOImpl(connection);
	}
	
	public CommunityTypeDAO getCommunityDAO() {
		return communityDAO;
	}

	public FirstHandRecordDAO getFhRecordDAO() {
		return fhRecordDAO;
	}

	public RoomTypeDAO getRoomDAO() {
		return roomDAO;
	}

	public SecondHandRecordDAO getShRecordDAO() {
		return shRecordDAO;
	}

	public void readFirstHandRecordExcelFilesToDB()
	{
		List<File> files = new ArrayList<File>();
		for(int i = 0;i<files.size();i++)
		{
			File file = files.get(i);
			List<CommunityType> infos = ExcelFileReader.readFileToCommunityType(file);
			for(CommunityType info : infos)
			{
				if(!communityDAO.isCommunityTypeExist(info.getName(), info.getDistrict(), info.getCity()))
					communityDAO.doCreate(info);
			}
			
			List<FirstHandRecord> records = ExcelFileReader.readFileToFirstHandRecord(file);
			for(FirstHandRecord record : records)
			{
				CommunityType info = communityDAO.findByExactName(record.getName(), record.getDistrict(), record.getCity());
				if(info != null)
					record.setInfo(info);
			}
			fhRecordDAO.doCreate(records);
		}
	}
	
	public void wipeAll()
	{
		this.connection.getDataStore().getDB().dropDatabase();
	}
	
	public void close()
	{
		this.connection.close();
	}
}
