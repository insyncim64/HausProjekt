package de.jibin.db.morphia.dao;

import java.util.List;

import de.jibin.db.morphia.entity.CommunityType;
import de.jibin.db.morphia.entity.SecondHandRecord;

public interface SecondHandRecordDAO 
{
	public List<SecondHandRecord> findAll();
}
