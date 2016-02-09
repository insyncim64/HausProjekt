package de.jibin.salesrecord.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import de.jibin.salesrecord.commons.WebConstants;
import de.jibin.salesrecord.db.dao.SecondHandRecordDAO;
import de.jibin.salesrecord.db.entity.FirstHandRecord;

@EnableWebMvc
@Controller
@RequestMapping("/shrecords")
public class SecondHandRecordResource 
{
	private SecondHandRecordDAO shRecordRepository;
	
	@Autowired
	public SecondHandRecordResource(SecondHandRecordDAO dao)
	{
		this.shRecordRepository = dao;
	}
	
	@RequestMapping(params = {WebConstants.COMMUNITY_ID_STRING, 
			                  WebConstants.DATE_STRING, 
			                  WebConstants.COUNT_STRING},
				    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public List<FirstHandRecord> showSecondHandRecords(
	@RequestParam(value = WebConstants.COMMUNITY_ID_STRING) String id, 
	@RequestParam(value = WebConstants.DATE_STRING) String date, 
	@RequestParam(value = WebConstants.COUNT_STRING) int count) 
	{
		return null;
	}
}
