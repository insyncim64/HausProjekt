package de.jibin.salesrecord.db.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.jibin.salesrecord.db.dao.custom.CommunityTypeDAOCustom;
import de.jibin.salesrecord.db.entity.CommunityType;

@Repository
public interface CommunityTypeDAO extends CrudRepository<CommunityType, ObjectId>, CommunityTypeDAOCustom
{
	public List<CommunityType> findAll();
	public CommunityType findById(long id);
	public List<CommunityType> findByName(String name);
	public List<CommunityType> findByNameAndCity(String name, String city);
	public CommunityType findByNameAndCityAndDistrict(String name,  String city, String district);	
	public List<CommunityType> findByLocation(GeoJsonPoint point, int distance);
}
