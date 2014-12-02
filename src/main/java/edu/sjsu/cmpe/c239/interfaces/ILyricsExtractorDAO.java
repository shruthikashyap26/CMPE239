package edu.sjsu.cmpe.c239.interfaces;

import edu.sjsu.cmpe.c239.pojo.Artists;

public interface ILyricsExtractorDAO {
	
	public void getConnection();
	public void getDB(String dbname);
	public String saveArtistDiscography(Artists artist);
	public Artists getArtistDiscography(String artist);
}
