package de.jibin.salesrecord.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import de.jibin.salesrecord.db.dao.RoomTypeDAO;

@EnableWebMvc
@Controller
@RequestMapping("/roomtype")
public class RoomTypeResource 
{
	RoomTypeDAO roomTypeRepository;
	
	@Autowired
	public RoomTypeResource(RoomTypeDAO dao)
	{
		this.roomTypeRepository = dao;
	}
}
