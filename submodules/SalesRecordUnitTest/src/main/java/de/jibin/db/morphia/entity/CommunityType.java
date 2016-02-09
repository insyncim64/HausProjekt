package de.jibin.db.morphia.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.geo.Point;

@Entity("communityTypes")
public class CommunityType 
{
	@Id
	ObjectId id;
	
	String name;
	
	Point location;
	
	String district;
	
	String city;
	
	@Embedded
	List<String> schools = new ArrayList<String>();
	
	@Embedded
	List<RoomType> types = new ArrayList<RoomType>();
	
	@Embedded
	List<String> picURLs = new ArrayList<String>();

	public CommunityType(String name, Point location,String city, String district,
			List<String> schools, List<RoomType> types, List<String> picURLs) {
		super();
		this.city = city;
		this.name = name;
		this.location = location;
		this.district = district;
		this.schools = schools;
		this.types = types;
		this.picURLs = picURLs;
	}

	public CommunityType() {
		super();
	}

	public String getCity() {
		return city;
	}

	public String getId() {
		return id.toHexString();
	}

	public String getName() {
		return name;
	}

	public Point getLocation() {
		return location;
	}

	public String getDistrict() {
		return district;
	}

	public List<String> getSchools() {
		return schools;
	}

	public List<RoomType> getTypes() {
		return types;
	}

	public List<String> getPicURLs() {
		return picURLs;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
