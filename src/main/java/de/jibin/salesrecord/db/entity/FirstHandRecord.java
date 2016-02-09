package de.jibin.salesrecord.db.entity;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import de.jibin.salesrecord.commons.DateUtils;

@Document(collection = "firstHandRecords")
public class FirstHandRecord implements Serializable {
	@Id
	ObjectId id;

	String communityId;

	@Indexed
	String name;

	@Indexed
	Date date;

	@Indexed
	String district;

	@Indexed
	String city;

	int dailySoldCount;

	int dailyTotalSize;

	int dailyTotalValue;

	int totalSoldCount;

	int totalSoldSize;

	int remainCount;

	int remainSize;

	int dropCount;

	@DBRef
	CommunityType info;

	public FirstHandRecord() {
		super();
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * translation
	 * @param date
	 */
	public void setDate(String date) {
		this.date = DateUtils.convertString2Date(date, true);
	}

	public void setDailySoldCount(int dailySoldCount) {
		this.dailySoldCount = dailySoldCount;
	}

	public void setDailyTotalSize(int dailyTotalSize) {
		this.dailyTotalSize = dailyTotalSize;
	}

	public void setDailyTotalValue(int dailyTotalValue) {
		this.dailyTotalValue = dailyTotalValue;
	}

	public void setTotalSoldCount(int totalSoldCount) {
		this.totalSoldCount = totalSoldCount;
	}

	public void setTotalSoldSize(int totalSoldSize) {
		this.totalSoldSize = totalSoldSize;
	}

	public void setRemainCount(int remainCount) {
		this.remainCount = remainCount;
	}

	public void setRemainSize(int remainSize) {
		this.remainSize = remainSize;
	}

	public void setDropCount(int dropCount) {
		this.dropCount = dropCount;
	}

	public void setInfo(CommunityType info) 
	{
		this.communityId = info.getId();
		this.info = info;
	}

	public String getCommunityId() {
		return communityId;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getId() {
		return id.toHexString();
	}

	public String getName() {
		return name;
	}

	/**
	 * use a formatter
	 * @return
	 */
	public String getDate() {
		return DateUtils.convertDate2String(this.date, DateUtils.UTC, true);
	}

	public int getDailySoldCount() {
		return dailySoldCount;
	}

	public int getDailyTotalSize() {
		return dailyTotalSize;
	}

	public int getDailyTotalValue() {
		return dailyTotalValue;
	}

	public int getTotalSoldCount() {
		return totalSoldCount;
	}

	public int getTotalSoldSize() {
		return totalSoldSize;
	}

	public int getRemainCount() {
		return remainCount;
	}

	public int getRemainSize() {
		return remainSize;
	}

	public int getDropCount() {
		return dropCount;
	}

	public CommunityType getInfo() {
		return info;
	}

	public int getDailyAveragePrice() {
		return Math.round((float) (this.dailyTotalValue / this.dailyTotalSize));
	}

	public int getDailyAverageUnitPrice() {
		return Math.round((float) (this.dailyTotalValue / this.dailySoldCount));
	}
}
