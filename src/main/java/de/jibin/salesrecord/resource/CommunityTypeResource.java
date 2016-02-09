package de.jibin.salesrecord.resource;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import de.jibin.salesrecord.commons.WebConstants;
import de.jibin.salesrecord.db.dao.CommunityTypeDAO;
import de.jibin.salesrecord.db.entity.CommunityType;

@EnableWebMvc
@Controller
public class CommunityTypeResource {
	static final Logger logger = LoggerFactory.getLogger(CommunityTypeResource.class);
	private CommunityTypeDAO communityTypeRepository;

	@Autowired
	public CommunityTypeResource(CommunityTypeDAO dao) {
		this.communityTypeRepository = dao;
	}

	@RequestMapping(value = "/community", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public List<CommunityType> showCommunity(
			@RequestParam(WebConstants.NAME_STRING) String name,
			@RequestParam(value = WebConstants.CITY_STRING, required = false, defaultValue = "") String city,
			@RequestParam(value = WebConstants.DISTRICT_STRING, required = false, defaultValue = "") String district) {
		List<CommunityType> communities = null;
		try {
			if (name.contains("%"))
				name = URLDecoder.decode(name, "UTF-8");
			if (city.contains("%"))
				city = URLDecoder.decode(city, "UTF-8");
			if (district.contains("%"))
				district = URLDecoder.decode(district, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		logger.debug(CommunityTypeResource.class.getName(), name, city, district);
		if (city.isEmpty() && district.isEmpty()) {
			communities = communityTypeRepository.findByName(name);
		} else if (district.isEmpty() && !city.isEmpty()) {
			communities = communityTypeRepository.findByNameAndCity(name, city);
		} else if (!name.isEmpty() && !district.isEmpty() && !city.isEmpty()) {
			communities = new ArrayList<CommunityType>();
			communities.add(communityTypeRepository.findByNameAndCityAndDistrict(name, city, district));
		}
		if (communities != null)
			return communities;
		else
			return new ArrayList<CommunityType>();
	}
}
