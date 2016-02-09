package de.jibin.salesrecord.resource;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import de.jibin.salesrecord.commons.DateUtils;
import de.jibin.salesrecord.commons.WebConstants;
import de.jibin.salesrecord.db.dao.FirstHandRecordDAO;
import de.jibin.salesrecord.db.entity.FirstHandRecord;
@EnableWebMvc
@Controller
public class FirstHandRecordResource 
{
	static final Logger logger = LoggerFactory.getLogger(FirstHandRecordResource.class);
	private FirstHandRecordDAO fhRecordRepository;
	
	@Autowired
	public FirstHandRecordResource(FirstHandRecordDAO dao)
	{
		this.fhRecordRepository = dao;
	}
	
	@RequestMapping(value = "/fhrecords", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public List<FirstHandRecord> showFirstHandRecords(
			@RequestParam(WebConstants.COMMUNITY_ID_STRING) String communityId,
			@RequestParam(value = WebConstants.DATE_STRING, required = false, defaultValue = "2000-01-01") String date,
			@RequestParam(value = WebConstants.COUNT_STRING, required = false, defaultValue = "10") String countStr) {
		int count = 10;
		try
		{
			count = Integer.valueOf(countStr);
		}catch(Exception e)
		{
			return new ArrayList<FirstHandRecord>();
		}
		logger.debug(FirstHandRecordResource.class.getName(), communityId, date, countStr);
		return fhRecordRepository.findByCommunityIdAndDateAfter(communityId, DateUtils.convertString2Date(date, true), new PageRequest(0, count));
	}
	
	
}
