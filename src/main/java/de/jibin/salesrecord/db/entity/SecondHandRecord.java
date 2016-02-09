package de.jibin.salesrecord.db.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document( collection = "secondHandRecords")
public class SecondHandRecord implements Serializable
{
	@Id
	ObjectId id;
	
	int communityId;
	
	@Indexed
	String name;
	
	int price;
	
	@Indexed
	String date;
	
	String sellerName;
	
	String sourceName;
	
	int floorNum;
	
	int floorNumType;
	
	List<String> picURLs = new ArrayList<String>();
	
	RoomType roomType;

	public SecondHandRecord(int communityId, String name, int price,
			String date, String sellerName, String sourceName, int floorNum,
			int floorNumType, List<String> picURLs, RoomType roomType) {
		super();
		this.communityId = communityId;
		this.name = name;
		this.price = price;
		this.date = date;
		this.sellerName = sellerName;
		this.sourceName = sourceName;
		this.floorNum = floorNum;
		this.floorNumType = floorNumType;
		this.picURLs = picURLs;
		this.roomType = roomType;
	}

	public ObjectId getId() {
		return id;
	}

	public int getCommunityId() {
		return communityId;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public String getDate() {
		return date;
	}

	public String getSellerName() {
		return sellerName;
	}

	public String getSourceName() {
		return sourceName;
	}

	public int getFloorNum() {
		return floorNum;
	}

	public int getFloorNumType() {
		return floorNumType;
	}

	public List<String> getPicURLs() {
		return picURLs;
	}

	public RoomType getRoomType() {
		return roomType;
	}
}
