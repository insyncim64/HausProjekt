package de.jibin.db.morphia.dao.impl;

import java.util.List;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.geo.GeoJson;
import org.mongodb.morphia.geo.Point;
import org.mongodb.morphia.query.Query;

import de.jibin.db.morphia.DatabaseConnection;
import de.jibin.db.morphia.dao.CommunityTypeDAO;
import de.jibin.db.morphia.entity.CommunityType;

public class CommunityTypeDAOImpl extends BasicDAO<CommunityType, ObjectId> implements CommunityTypeDAO
{
	public CommunityTypeDAOImpl(DatabaseConnection connection)
	{
		super(CommunityType.class, connection.getDataStore());
	}
	
	public boolean doCreate(CommunityType type) 
	{
		this.save(type);
		return true;
	}

	public CommunityType findById(String id) 
	{
		return this.get(new ObjectId(id));
	}

	public List<CommunityType> findByName(String name) 
	{
		return this.getDatastore().find(CommunityType.class).field("name").equal(name).asList();
	}

	public List<CommunityType> findByLocation(double lat, double lon, int distance) 
	{
		final Point searchLocation = GeoJson.point(lat, lon);
		return this.getDatastore().find(CommunityType.class).field("location").near(searchLocation, distance).asList();
	}

	public boolean isCommunityTypeExist(String name, String district, String city) 
	{
		return !this.getDatastore().find(CommunityType.class).
				field("name").equal(name).
				field("district").equal(district).
				field("city").equal(city).
				asList().isEmpty();
	}

	public CommunityType findByExactName(String name, String district,
			String city) 
	{
		return this.getDatastore().find(CommunityType.class).
				field("name").equal(name).
				field("district").equal(district).
				field("city").equal(city).get();
	}

	public List<CommunityType> findAll() 
	{
		return this.find().asList();
	}
}
