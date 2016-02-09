package de.jibin.salesrecord.db.dao;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.jibin.salesrecord.db.dao.custom.FirstHandRecordDAOCustom;
import de.jibin.salesrecord.db.entity.FirstHandRecord;

@Repository
public interface FirstHandRecordDAO extends CrudRepository<FirstHandRecord, ObjectId>, FirstHandRecordDAOCustom
{
	public List<FirstHandRecord> findAll();
 	public List<FirstHandRecord> findByDate(String date);
	public List<FirstHandRecord> findByCity(String city);
	public List<FirstHandRecord> findByDistrict(String district);
	public List<FirstHandRecord> findByName(String name);
	public List<FirstHandRecord> findByCommunityId(String communityId);
	public List<FirstHandRecord> findByCommunityIdAndDateAfter(String communityId, Date date, Pageable page);
}
