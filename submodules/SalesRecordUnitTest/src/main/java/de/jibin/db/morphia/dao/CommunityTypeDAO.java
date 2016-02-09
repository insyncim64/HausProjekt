package de.jibin.db.morphia.dao;

import java.util.List;

import de.jibin.db.morphia.entity.CommunityType;

public interface CommunityTypeDAO 
{
	public boolean doCreate(CommunityType type);
	public List<CommunityType> findAll();
	public CommunityType findById(String id);
	public List<CommunityType> findByName(String name);
	public List<CommunityType> findByLocation(double lat, double lon, int distance);
	public boolean isCommunityTypeExist(String name, String district, String city);
	public CommunityType findByExactName(String name, String district, String city);	
}
