package de.jibin.salesrecord.db.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roomTypes")
public class RoomType implements Serializable
{
	@Id
	ObjectId id;
	
	int roomCount;
	
	int hallCount;
	
	int size;
	
	int buildingFloorCount;
	
	boolean decorated;
	
	List<String> picURLs = new ArrayList<String>();
}
