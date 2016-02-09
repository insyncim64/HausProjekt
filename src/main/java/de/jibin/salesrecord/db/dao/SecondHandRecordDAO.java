package de.jibin.salesrecord.db.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.jibin.salesrecord.db.dao.custom.SecondHandRecordDAOCustom;
import de.jibin.salesrecord.db.entity.SecondHandRecord;

@Repository
public interface SecondHandRecordDAO  extends CrudRepository<SecondHandRecord, ObjectId>, SecondHandRecordDAOCustom
{
	public List<SecondHandRecord> findAll();
}
