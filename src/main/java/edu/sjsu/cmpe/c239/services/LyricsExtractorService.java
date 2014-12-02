package edu.sjsu.cmpe.c239.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import edu.sjsu.cmpe.c239.constants.Constants;
import edu.sjsu.cmpe.c239.constants.StopWords;
import edu.sjsu.cmpe.c239.interfaces.ILyricsExtractor;
import edu.sjsu.cmpe.c239.interfaces.ILyricsExtractorDAO;
import edu.sjsu.cmpe.c239.pojo.Albums;
import edu.sjsu.cmpe.c239.pojo.Artists;

@Service
public class LyricsExtractorService implements ILyricsExtractor {

	@Autowired
	public ILyricsExtractorDAO lyricsDAO;
	public RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

	@Override
	public String saveArtistDiscography(String artist) {
		String getDiscographyUrl = Constants.LW_GET_ARTIST_DISCOGRAPHY_URL;

		getDiscographyUrl = getDiscographyUrl.replace("ARTISTNAME", artist).replace("FORMAT", Constants.FORMAT.json.name());

		String discography = restTemplate.getForObject(getDiscographyUrl, String.class);

		ObjectMapper mapper = new ObjectMapper();

		try {
			mapper.writeValue(new File("C:/Sumanth/Courses/239/Project/Data/Json/"+artist+".json"), discography);
		} catch (JsonGenerationException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String result = discography;

		/*try {
			Artists artists = mapper.readValue(discography, Artists.class);
			Albums[] albums = artists.getAlbums();
			StringBuilder builder = new StringBuilder();
			for (Albums album : albums) {
				String[] songs = album.getSongs();
				for (String song : songs) {
					builder.append(song).append("\n");
				}
			}
			result = builder.toString();
			System.out.println(result);
			result = lyricsDAO.saveArtistDiscography(artists);
		} catch(Exception e) {
			e.printStackTrace();
		}*/
		return result;
	}

	@Override
	public Artists getArtistDiscography(String artist) {
		Artists artistDiscography = null;
		String discography = "";
		//lyricsDAO.getArtistDiscography(artist);
		ObjectMapper mapper = new ObjectMapper();
		try {
			File artistFile = new File("C:/Sumanth/Courses/239/Project/Data/Json/"+artist+".json");
			if (!artistFile.exists()) {
				saveArtistDiscography(artist);
			}
			discography = mapper.readValue(artistFile, String.class);
			artistDiscography = mapper.readValue(discography, Artists.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return artistDiscography;
	}

	@Override
	public void saveSongs(String artist) {
		Artists artistDiscography = getArtistDiscography(artist);
		Albums[] albums = artistDiscography.getAlbums();
		//ArrayList<String> songs = new ArrayList<String>();
		try {
			File songFile = new File("C:/Sumanth/Courses/239/Project/Data/Songs/"+artist+".txt");
			if (!songFile.exists()) {
				songFile.createNewFile();
			}
			FileWriter fw = new FileWriter(songFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (Albums album : albums) {
				String[] song = album.getSongs();
				for(int i=0; i < song.length; i++) {
					//songs.add(song[i]);
					bw.append(song[i]).append("\n");
				}
			}
			bw.close();
			for (Albums album : albums) {
				String[] song = album.getSongs();
				for(int i=0; i < song.length; i++) {
					try {
						getLyrics(artist, song[i]);
					} catch (IOException e) {
						if (e.getMessage().contains("IO Error")) {
							System.out.println("IO ERROR?? " +e.getMessage());
							i--;
						}
						else if (e.getMessage().contains("Internal Server Error")) {
							System.out.println("500 ERROR??" + e.getMessage());
						}
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}

		//getLyrics(artist);
	}

	@Override
	public void getLyrics(String artist, String song) throws IOException {
		HttpClient client = HttpClientBuilder.create().build();
		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(client));

		String lyricsIDUrl = Constants.CL_SEARCH_LYRIC_DIRECT_URL;
		//lyricsIDUrl = lyricsIDUrl.replace("ARTISTNAME", artist).replace("SONGNAME", "%s");
		/*File songFile = new File("C:/Sumanth/Courses/239/Project/Data/Songs/"+artist+".txt");
		FileReader fr = null;
		BufferedReader br = null;
		System.setProperty("http.keepAlive", "false");
		int count = 0;
		
			fr = new FileReader(songFile);
			br = new BufferedReader(fr);
			while (br.ready()) {
				if (count == 5) {
					br.close();
					return;
				}
				count++;
				String song = br.readLine();
				*/
		try {
			File lyricsFile = new File("C:/Sumanth/Courses/239/Project/Data/Lyrics/lyrics"+artist+".csv");
			if (!lyricsFile.exists()) {
				lyricsFile.createNewFile();
			}
			FileWriter fw = new FileWriter(lyricsFile.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
				lyricsIDUrl = lyricsIDUrl.replace("ARTISTNAME", artist).replace("SONGNAME", song);
				String result = restTemplate.getForObject(lyricsIDUrl, String.class);
				/*URI uri = null;
		try {
			uri = new URI(String.format(lyricsIDUrl, URLEncoder.encode(song, "UTF8")));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		HttpGet request = new HttpGet(uri);
		request.addHeader("Connection", "Close");*/

				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				String lyric = "";

				/*HttpResponse response = client.execute(request);

			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line=br.readLine()) != null) {
				result.append(line);
			}*/

				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(new InputSource(new StringReader(result.toString())));
				//document.getDocumentElement().normalize();
				NodeList lyricList = document.getElementsByTagName("GetLyricResult");
				for (int i = 0; i < lyricList.getLength(); i++) {
					Node lyricNode = lyricList.item(i);
					if (lyricNode.getNodeType() == Node.ELEMENT_NODE) {
						Element lyricElement = (Element)lyricNode;
						lyric = lyricElement.getElementsByTagName("Lyric").item(0).getTextContent();
					}
				}
				String processedLyric = removeStopWordsAndStem(lyric);
				System.out.println("ARTIST: " + artist + "\t\tSONG: " + song + "\n");
				System.out.println(processedLyric);
				System.out.println("\n=========================================================\n\n");
				if (!processedLyric.isEmpty() || !processedLyric.trim().isEmpty()) {
					bw.append(artist).append(",").append(song).append(",").append(processedLyric).append("\n");
				}
				bw.close();
		}catch (Exception e) {
			if (e.getMessage().contains("Internal Server Error")) {
				throw new IOException("Internal Server Error");
			} else {
				throw new IOException("IO Error");
			}
			//e.printStackTrace();
		}
	}
	
	public String removeStopWordsAndStem(String input) throws IOException {
	    TokenStream tokenStream = new StandardTokenizer(new StringReader(input));
	    tokenStream = new StopFilter(tokenStream, StopWords.stopWordsList());
	    tokenStream = new PorterStemFilter(tokenStream);
	    
	    StringBuilder sb = new StringBuilder();
	    tokenStream.reset();
	    while (tokenStream.incrementToken()) {
	        if (sb.length() > 0) {
	            sb.append(" ");
	        }
	        sb.append(tokenStream.addAttribute(CharTermAttribute.class).toString());
	    }
	    tokenStream.end();
	    tokenStream.close();
	    return sb.toString();
	}
	
	/*public String processLyrics(String lyric) {
		String processedLyric = "";
		Analyzer analyzer = new StandardAnalyzer();
		List<String> result = new ArrayList<String>();
		try {
			TokenStream stream = analyzer.tokenStream(null, new StringReader(lyric));
			stream.reset();
			while(stream.incrementToken()) {
				result.add(stream.addAttribute(CharTermAttribute.class).toString());
			}
			stream.end();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			analyzer.close();
		}
		for (String s : result) {
			processedLyric += s + " ";
		}
		return processedLyric;
	}*/

}
