package edu.sjsu.cmpe.c239.dao;

import java.util.Map;
import java.util.Set;

import org.bson.BSONObject;

import com.mongodb.DBObject;

import edu.sjsu.cmpe.c239.pojo.Albums;

public class ArtistDBObject implements DBObject {
	
	private String artist;
	private AlbumsDBObject[] albums;

	@Override
	public boolean containsField(String arg0) {
		return false;
	}

	@Override
	@Deprecated
	public boolean containsKey(String arg0) {
		return false;
	}

	@Override
	public Object get(String arg0) {
		return null;
	}

	@Override
	public Set<String> keySet() {
		return null;
	}

	@Override
	public Object put(String arg0, Object arg1) {
		return null;
	}

	@Override
	public void putAll(BSONObject arg0) {
		
	}

	@Override
	public void putAll(Map arg0) {
		
	}

	@Override
	public Object removeField(String arg0) {
		return null;
	}

	@Override
	public Map toMap() {
		return null;
	}

	@Override
	public boolean isPartialObject() {
		return false;
	}

	@Override
	public void markAsPartialObject() {
		
	}

}
