package de.jibin.db.morphia.dao;

import java.util.List;

import de.jibin.db.morphia.entity.CommunityType;
import de.jibin.db.morphia.entity.RoomType;

public interface RoomTypeDAO 
{
	public List<RoomType> findAll();
}
