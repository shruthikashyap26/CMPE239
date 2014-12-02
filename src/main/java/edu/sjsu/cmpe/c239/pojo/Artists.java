package edu.sjsu.cmpe.c239.pojo;

import java.util.Arrays;

public class Artists {

	private String artist;
	private Albums[] albums;

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public Albums[] getAlbums() {
		return albums;
	}

	public void setAlbums(Albums[] albums) {
		this.albums = albums;
	}

	@Override
	public String toString() {
		return "Artists [artist=" + artist + ", albums="
				+ Arrays.toString(albums) + "]";
	}

}
