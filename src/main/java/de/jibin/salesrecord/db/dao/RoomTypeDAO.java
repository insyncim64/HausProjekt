package de.jibin.salesrecord.db.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.jibin.salesrecord.db.dao.custom.RoomTypeDAOCustom;
import de.jibin.salesrecord.db.entity.RoomType;

@Repository
public interface RoomTypeDAO  extends CrudRepository<RoomType, ObjectId>, RoomTypeDAOCustom
{
	public List<RoomType> findAll();
}
