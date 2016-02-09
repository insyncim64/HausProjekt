package de.jibin.salesrecord.db.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "communityTypes")
public class CommunityType implements Serializable
{
	@Id
	ObjectId id;
	
	@Indexed
	String name;
	
	/**
	 * location is stored in GeoJSON format.
	 * {
	 *   "type" : "Point",
	 *   "coordinates" : [ x, y ]
	 * }
	 */
	@GeoSpatialIndexed
	GeoJsonPoint location;
	
	@Indexed
	String district;
	
	@Indexed
	String city;
	
	List<String> schools = new ArrayList<String>();
	
	List<RoomType> types = new ArrayList<RoomType>();
	
	List<String> picURLs = new ArrayList<String>();

	public CommunityType(String name, GeoJsonPoint location,String city, String district,
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

	public void setLocation(GeoJsonPoint location) {
		this.location = location;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
