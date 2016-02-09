package de.jibin.db.morphia.dao.impl;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.BasicDAO;

import de.jibin.db.morphia.DatabaseConnection;
import de.jibin.db.morphia.dao.FirstHandRecordDAO;
import de.jibin.db.morphia.entity.FirstHandRecord;

public class FirstHandRecordDAOImpl extends BasicDAO<FirstHandRecord, ObjectId> implements FirstHandRecordDAO
{
	public FirstHandRecordDAOImpl(DatabaseConnection connection) {
		super(FirstHandRecord.class, connection.getDataStore());
	}
	
	public boolean doCreate(List<FirstHandRecord> records) 
	{
		this.getDatastore().save(records);
		return false;
	}

	public boolean doCreate(FirstHandRecord record) 
	{
		this.getDatastore().save(record);
		return false;
	}

	public List<FirstHandRecord> findByDate(Date date) 
	{
		return this.getDatastore().find(FirstHandRecord.class).field("date").equal(date).asList();
	}

	public List<FirstHandRecord> findByCity(String city) 
	{
		return this.getDatastore().find(FirstHandRecord.class).field("city").equal(city).asList();
	}

	public List<FirstHandRecord> findByDistrict(String district) 
	{
		return this.getDatastore().find(FirstHandRecord.class).field("district").equal(district).asList();
	}

	public List<FirstHandRecord> findByName(String name) 
	{
		return this.getDatastore().find(FirstHandRecord.class).field("name").equal(name).asList();
	}

	public List<FirstHandRecord> findByLocation(double lat, double lon,
			int distance) {
		return null;
//		final Point searchLocation = GeoJson.point(lat, lon);
//		return this.getDatastore().find(FirstHandRecord.class).field("location").near(searchLocation, distance).asList();
	}

	public List<FirstHandRecord> findAll() {
		return this.find().asList();
	}
}
