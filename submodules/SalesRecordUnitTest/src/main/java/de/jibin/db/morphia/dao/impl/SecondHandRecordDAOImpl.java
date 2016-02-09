package de.jibin.db.morphia.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.BasicDAO;

import de.jibin.db.morphia.DatabaseConnection;
import de.jibin.db.morphia.dao.SecondHandRecordDAO;
import de.jibin.db.morphia.entity.SecondHandRecord;

public class SecondHandRecordDAOImpl extends BasicDAO<SecondHandRecord, ObjectId> implements SecondHandRecordDAO
{

	public SecondHandRecordDAOImpl(DatabaseConnection connection) {
		super(SecondHandRecord.class, connection.getDataStore());
	}

	public void wipeAll() 
	{
	}

	public List<SecondHandRecord> findAll() 
	{
		return this.find().asList();
	}

}
