package edu.sjsu.cmpe.c239.pojo;

public class Albums {

	private String album;
	private String year;
	private String amazonLink;
	private String[] songs;
	//private Songs[] songs;

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getAmazonLink() {
		return amazonLink;
	}

	public void setAmazonLink(String amazonLink) {
		this.amazonLink = amazonLink;
	}

	public String[] getSongs() {
		return songs;
	}

	public void setSongs(String[] songs) {
		this.songs = songs;
	}

	/*public Songs[] getSongs() {
		return songs;
	}

	public void setSongs(Songs[] songs) {
		this.songs = songs;
	}

	@Override
	public String toString() {
		return "Albums [album=" + album + ", year=" + year
				+ ", amazonLink=" + amazonLink + ", songs="
				+ Arrays.toString(songs) + "]";
	}*/
}
