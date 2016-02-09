package de.jibin.salesrecord.db.dao.custom.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import de.jibin.salesrecord.commons.DateUtils;
import de.jibin.salesrecord.db.dao.custom.FirstHandRecordDAOCustom;
import de.jibin.salesrecord.db.entity.FirstHandRecord;

public class FirstHandRecordDAOCustomImpl implements FirstHandRecordDAOCustom
{
	@Autowired  
    private MongoTemplate mongoTemplate;  
	
	public List<FirstHandRecord> findByCommunityIdAndDate(String communityId, String date, int count) 
	{
		Query query = new Query();
		query.addCriteria(new Criteria("communityId").is(communityId));
		Date dateDate = DateUtils.convertString2Date(date, true);
		query.addCriteria(new Criteria("date").gt(dateDate));
		query.limit(count);
		return this.mongoTemplate.find(query, FirstHandRecord.class);
	}

}
