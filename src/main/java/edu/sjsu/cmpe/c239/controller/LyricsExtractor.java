package edu.sjsu.cmpe.c239.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sjsu.cmpe.c239.interfaces.ILyricsExtractor;
import edu.sjsu.cmpe.c239.pojo.Artists;

@Controller
@RequestMapping("/")
public class LyricsExtractor {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String retrieveIndex() {
		return "index";
	}

	@Autowired
	public ILyricsExtractor lyricsExtractor;
	
	@RequestMapping(value="/saveArtistDiscography", method=RequestMethod.POST)
	public @ResponseBody String saveArtistDiscography(@RequestParam String artist) {
		String discography = lyricsExtractor.saveArtistDiscography(artist);
		return discography;
	}
	
	@RequestMapping(value="/getArtistDiscography", method=RequestMethod.GET)
	public @ResponseBody String getArtistDiscography(@RequestParam String artist) {
		Artists artistDiscography = lyricsExtractor.getArtistDiscography(artist);
		return artistDiscography.toString();
	}

	@RequestMapping(value="/saveSongs", method=RequestMethod.POST)
	public @ResponseBody String saveSongs(@RequestParam String artist) {
		lyricsExtractor.saveSongs(artist);
		return "Songs saved";
	}
	
	@RequestMapping(value="/getLyrics", method=RequestMethod.GET)
	public @ResponseBody String getLyrics(@RequestParam String artist, @RequestParam String song) {
		try {
			lyricsExtractor.getLyrics(artist, song);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Lyrics Saved";
	}
	
}
