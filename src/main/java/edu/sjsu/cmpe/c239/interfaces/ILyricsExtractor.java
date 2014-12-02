package edu.sjsu.cmpe.c239.interfaces;

import java.io.IOException;

import edu.sjsu.cmpe.c239.pojo.Artists;

public interface ILyricsExtractor {
	
	public String saveArtistDiscography(String artist);

	public Artists getArtistDiscography(String artist);

	public void saveSongs(String artist);
	
	public void getLyrics(String artist, String song) throws IOException;

}
