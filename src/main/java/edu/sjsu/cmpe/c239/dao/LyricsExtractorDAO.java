package edu.sjsu.cmpe.c239.dao;

import java.net.UnknownHostException;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteResult;

import edu.sjsu.cmpe.c239.constants.Constants;
import edu.sjsu.cmpe.c239.interfaces.ILyricsExtractorDAO;
import edu.sjsu.cmpe.c239.pojo.Artists;

@Service
public class LyricsExtractorDAO implements ILyricsExtractorDAO {
	
	public MongoClient mongoClient;
	public MongoClientURI mongoClientUri;
	public DB mongoDB;
	
	public void getConnection() {
		try {
			mongoClientUri = new MongoClientURI(Constants.DATABASE_URI);
			mongoClient = new MongoClient(mongoClientUri);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void getDB(String dbname) {
		if (mongoClient != null) {
			mongoDB = mongoClient.getDB(dbname);
		} else {
			getConnection();
			mongoDB = mongoClient.getDB(dbname);
		}
	}	
	
	public String saveArtistDiscography(Artists artist) {
		getConnection();
		getDB(Constants.DATABASE_NAME);

		Gson gson = new Gson();
		String artistJson = gson.toJson(artist);
		
		BasicDBObject artistDBObject = new BasicDBObject(Constants.ARTIST_DISCOGRAPHY, artistJson);
		
		DBCollection discographyCollection = mongoDB.getCollection(Constants.ARTIST_DISCOGRAPHY);
		WriteResult result = discographyCollection.save(artistDBObject);
		return result.toString();
	}
	
	public Artists getArtistDiscography(String artist) {
		getConnection();
		getDB(Constants.DATABASE_NAME);
		//{ "_id" : { "$oid" : "547cfff6c497d5908ec1732f"} , "artistDiscography" : "{\"artist\":\"Train\",
		DBCollection discographyCollection = mongoDB.getCollection(Constants.ARTIST_DISCOGRAPHY);
		DBObject query = new BasicDBObject("_id.oid", "547cfff6c497d5908ec1732f");
		//DBCursor cursor = discographyCollection.find(query);
		DBCursor cursor = discographyCollection.find();
		Artists artistInfo = null;
		try {
			while (cursor.hasNext()) {
				System.out.println(cursor.next());;
				System.out.println("\n\n\n");
			}
		} finally {
			cursor.close();
		}
		/*BasicDBObject query = new BasicDBObject(Constants.ARTIST_DISCOGRAPHY+".artist", artist);
		
		DBCursor cursor = discographyCollection.find(query);
		
		try {
			while(cursor.hasNext()) {
				//artistInfo = cursor.next();
				System.out.println(cursor.next());
			}
		} finally {
			cursor.close();
		}*/
		return artistInfo;
	}

}
