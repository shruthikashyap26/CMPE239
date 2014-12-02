package edu.sjsu.cmpe.c239.constants;

public class Constants {
	
	public enum FORMAT {
		text, xml, json, html
	};
	
	// URLs for querying ChartLyric Lyrics API
	
	// Retrieves the lyric using the artist and song
	public static String CL_SEARCH_LYRIC_DIRECT_URL = "http://api.chartlyrics.com/apiv1.asmx/SearchLyricDirect?artist=ARTISTNAME&song=SONGNAME";
	
	// Search for Lyric and return LyricID and Lyric Checksum
	public static String CL_SEARCH_LYRIC_URL = "http://api.chartlyrics.com/apiv1.asmx/SearchLyric?artist=ARTISTNAME&song=SONGNAME";
	
	// Search for Lyric and return LyricID and Lyric Checksum based on Lyric Text
	public static String CL_SEARCH_LYRIC_TEXT_URL = "http://api.chartlyrics.com/apiv1.asmx/SearchLyricText?lyricText=LYRICTEXT";
	
	// Retreives the lyric using the lyricId and lyricChecksum
	public static String CL_GET_LYRIC_URL =  "http://api.chartlyrics.com/apiv1.asmx/GetLyric?lyricId=LYRICID&lyricCheckSum=LYRICCHECKSUM";

	// URLs for querying LyricWiki API
	
	// Get the lyrics
	public static String LW_GET_LYRIC_URL = "http://lyrics.wikia.com/api.php?func=getSong&artist=ARTISTNAME&song=SONGNAME&fmt=FORMAT";
	
	// Get the entire discography for Artist
	public static String LW_GET_ARTIST_DISCOGRAPHY_URL = "http://lyrics.wikia.com/api.php?func=getArtist&artist=ARTISTNAME&fmt=FORMAT";
	
	// MongoDB Database URI
	public static String DATABASE_URI = "mongodb://god:iamgod@ds053310.mongolab.com:53310/sjsucmpe239"; 
	
	// MongoDB Database name
	public static String DATABASE_NAME = "sjsucmpe239";
	
	// MongoDB Artist Discography Collection name
	public static String ARTIST_DISCOGRAPHY = "artistDiscography";
}
