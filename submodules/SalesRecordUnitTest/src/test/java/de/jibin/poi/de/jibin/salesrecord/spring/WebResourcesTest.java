package de.jibin.poi.de.jibin.salesrecord.spring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.jibin.commons.Constants;
import de.jibin.db.morphia.DatabaseManager;
import de.jibin.db.morphia.entity.CommunityType;
import de.jibin.db.morphia.entity.FirstHandRecord;
import de.jibin.poi.ReadExcel.ExcelFileReader;
//import de.jibin.commons.Constants;
//import de.jibin.db.morphia.DatabaseManager;
//import de.jibin.db.morphia.entity.CommunityType;
//import de.jibin.poi.ReadExcel.ExcelFileReader;
import de.jibin.poi.test.config.TestApplicationConfig;
import de.jibin.salesrecord.commons.WebConstants;
import de.jibin.salesrecord.config.WebMvcConfig;
import de.jibin.salesrecord.resource.CommunityTypeResource;
import de.jibin.salesrecord.resource.FirstHandRecordResource;
import junit.framework.TestCase;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestApplicationConfig.class, WebMvcConfig.class })
@WebAppConfiguration
public class WebResourcesTest extends TestCase {
	private MockMvc communityMockMvc;
	private MockMvc firstHandRecordMockMvc;

	@Autowired
	private CommunityTypeResource mockCommunityTypeResource;
	
	@Autowired
	private FirstHandRecordResource mockFirstHandRecordResource;

	@Before
	public void setup() {
		this.communityMockMvc = MockMvcBuilders.standaloneSetup(mockCommunityTypeResource).build();
		this.firstHandRecordMockMvc = MockMvcBuilders.standaloneSetup(mockFirstHandRecordResource).build();
		
		//create database
		DatabaseManager manager = new DatabaseManager(Constants.LOCAL_IP_ADDR, Constants.TEST_DB_NAME);
		manager.wipeAll();
		File file = new File("res/testdata/ExcelFileReader/2010_08_01_beijing.xls");
		List<FirstHandRecord> records = null;
		
		List<CommunityType> infos = ExcelFileReader.readFileToCommunityType(file);
		for(CommunityType info : infos)
		{
			if(!manager.getCommunityDAO().isCommunityTypeExist(info.getName(), info.getDistrict(), info.getCity()))
				manager.getCommunityDAO().doCreate(info);
		}
		
		records = ExcelFileReader.readFileToFirstHandRecord(file);
		for(FirstHandRecord record : records)
		{
			CommunityType info = manager.getCommunityDAO().findByExactName(record.getName(), record.getDistrict(), record.getCity());
			if(info != null)
				record.setInfo(info);
		}
		manager.getFhRecordDAO().doCreate(records);
	}
	
	@Test
	public void testFirstHandRecord() throws Exception
	{
		File file = new File("res/testdata/ExcelFileReader/2010_08_01_beijing.xls");
		List<CommunityType> infos = ExcelFileReader.readFileToCommunityType(file);
		List<String> communityIds = new ArrayList<String>();
		//there are 80 entries.
		for(CommunityType info : infos)
		{
			de.jibin.salesrecord.db.entity.CommunityType sInfo = mockCommunityTypeResource.showCommunity(info.getName(), "", "").get(0);
			assertEquals("name should be equal ", info.getName(), sInfo.getName());
			communityIds.add(sInfo.getId());
		}
		
		//shoule exist
		for(String id : communityIds)
		{
//			String result = 
					firstHandRecordMockMvc.perform(get("/fhrecords?" + WebConstants.COMMUNITY_ID_STRING + "=" + id)).andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(jsonPath("$.length()", is(1)))
//					.andReturn().getResponse()
//					.getContentAsString()
					;
			
//			System.out.print(result);
		}
		
		//should not
		for(String id : communityIds)
		{
			firstHandRecordMockMvc.perform(get("/fhrecords?" + WebConstants.COMMUNITY_ID_STRING + "=" + id + "&"+ WebConstants.DATE_STRING + "=2016-01-01" + "&"+ WebConstants.COUNT_STRING + "=10")).andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(jsonPath("$.length()", is(0)));
		}
	}

	@Test
	public void testCommunityType() throws Exception 
	{
		/**
		 * search UTF-8 CJK characters test find only with name
		 */
		communityMockMvc.perform(get("/community?" + WebConstants.NAME_STRING + "=加州水郡")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.length()", is(1))).andExpect(jsonPath("$.[0].name", is("加州水郡")))
				.andExpect(jsonPath("$.[0].district", is("房山"))).andExpect(jsonPath("$.[0].city", is("beijing")));

		/**
		 * test null, since no shanghai
		 */
		communityMockMvc.perform(
				get("/community?" + WebConstants.NAME_STRING + "=加州水郡" + "&" + WebConstants.CITY_STRING + "=上海"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.length()", is(0)));

		/**
		 * test with city
		 */
		communityMockMvc.perform(
				get("/community?" + WebConstants.NAME_STRING + "=加州水郡" + "&" + WebConstants.CITY_STRING + "=beijing"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.length()", is(1)));

		/**
		 * test with city and district
		 */
		communityMockMvc.perform(get("/community?" + WebConstants.NAME_STRING + "=加州水郡" + "&" + WebConstants.CITY_STRING
				+ "=beijing" + "&" + WebConstants.DISTRICT_STRING + "=房山")).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(1)));

		/**
		 * search URL UTF-8 format
		 */
		communityMockMvc.perform(get("/community?" + WebConstants.NAME_STRING + "=%E5%8A%A0%E5%B7%9E%E6%B0%B4%E9%83%A1"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.length()", is(1)))
				.andExpect(jsonPath("$.[0].name", is("加州水郡"))).andExpect(jsonPath("$.[0].district", is("房山")))
				.andExpect(jsonPath("$.[0].city", is("beijing")));
		
		// verify(mockResource, times(1)).showCommunity("加州水郡", "上海", "");
		// verifyNoMoreInteractions(mockResource);
		// when(mockResource.showCommunity("")).thenReturn();
		// assertEquals("no entries ", 0, resource.findAll().size());
		// mockMvc.perform(get("",
		// 1L)).andExpect(status().isOk()).andExpect(arg0)
	}
}
