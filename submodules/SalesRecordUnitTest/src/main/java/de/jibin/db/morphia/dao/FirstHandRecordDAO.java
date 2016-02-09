package de.jibin.db.morphia.dao;

import java.util.Date;
import java.util.List;

import de.jibin.db.morphia.entity.FirstHandRecord;

public interface FirstHandRecordDAO
{
	public boolean doCreate(List<FirstHandRecord> record);
	public boolean doCreate(FirstHandRecord record);
	public List<FirstHandRecord> findAll();
	public List<FirstHandRecord> findByDate(Date date);
	public List<FirstHandRecord> findByCity(String city);
	public List<FirstHandRecord> findByDistrict(String district);
	public List<FirstHandRecord> findByName(String name);
	public List<FirstHandRecord> findByLocation(double lat, double lon, int distance);
}
