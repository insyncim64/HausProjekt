package de.jibin.db.morphia.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.BasicDAO;

import de.jibin.db.morphia.DatabaseConnection;
import de.jibin.db.morphia.dao.RoomTypeDAO;
import de.jibin.db.morphia.entity.RoomType;

public class RoomTypeDAOImpl extends BasicDAO<RoomType, ObjectId> implements RoomTypeDAO
{

	public RoomTypeDAOImpl(DatabaseConnection connection) 
	{
		super(RoomType.class, connection.getDataStore());
	}

	public List<RoomType> findAll() 
	{
		return this.find().asList();
	}
}
