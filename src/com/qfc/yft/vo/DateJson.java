package com.qfc.yft.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.qfc.yft.entity.json.JsonImport;



public class DateJson extends JsonImport{

	private int date;
	private int day;
	private int hours;
	private int minutes;
	private int month;
	private long nanos;//useless
	private int seconds;
	private long time;
	private int timezoneOffset;
	private int year;
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(toDate());
	}
	
	public final Date toDate(){
		return new Date(time);
	}
	
	
	public final int getDate() {
		return date;
	}

	public final void setDate(int date) {
		this.date = date;
	}

	public final int getDay() {
		return day;
	}

	public final void setDay(int day) {
		this.day = day;
	}

	public final int getHours() {
		return hours;
	}

	public final void setHours(int hours) {
		this.hours = hours;
	}

	public final int getMinutes() {
		return minutes;
	}

	public final void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public final int getMonth() {
		return month;
	}

	public final void setMonth(int month) {
		this.month = month;
	}

	public final long getNanos() {
		return nanos;
	}

	public final void setNanos(long nanos) {
		this.nanos = nanos;
	}

	public final int getSeconds() {
		return seconds;
	}

	public final void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public final long getTime() {
		return time;
	}

	public final void setTime(long time) {
		this.time = time;
	}

	public final int getTimezoneOffset() {
		return timezoneOffset;
	}

	public final void setTimezoneOffset(int timezoneOffset) {
		this.timezoneOffset = timezoneOffset;
	}

	public final int getYear() {
		return year;
	}

	public final void setYear(int year) {
		this.year = year;
	}

	public DateJson(JSONObject job){
		super(job);
	}
	
	/*
	 * {"date":27,
	 * "day":4,
	 * "hours":11,
	 * "minutes":27,
	 * "month":2,
	 * "nanos":0,
	 * "seconds":9,
	 * "time":1395890829000,
	 * "timezoneOffset":-480,"year":114}
	 */
	@Override
	public void initJackJson(JSONObject job) throws JSONException {
		try{
			
			if(job.has("date")) setDate(job.getInt("date"));
			if(job.has("day")) setDay(job.getInt("day"));
			if(job.has("hours")) setHours(job.getInt("hours"));
			if(job.has("minutes")) setMinutes(job.getInt("minutes"));
			if(job.has("month")) setMonth(job.getInt("month"));
			if(job.has("nanos")) setNanos(job.getLong("nanos"));
			if(job.has("seconds")) setSeconds(job.getInt("seconds"));
			if(job.has("time")) setTime(job.getLong("time"));
			if(job.has("timezoneOffset")) setTimezoneOffset(job.getInt("timezoneOffset"));
			if(job.has("year")) setYear(job.getInt("year"));
		}catch(JSONException e){
			e.printStackTrace();
		}

	}

}
