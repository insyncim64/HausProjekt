package de.jibin.db.morphia.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("roomTypes")
public class RoomType 
{
	@Id
	ObjectId id;
	
	int roomCount;
	
	int hallCount;
	
	int size;
	
	int buildingFloorCount;
	
	boolean decorated;
	
	@Embedded
	List<String> picURLs = new ArrayList<String>();
}
