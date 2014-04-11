package assignment3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.joda.time.LocalDate;
import org.json.JSONException;
import org.json.JSONObject;

public class FileHelper implements Runnable{
	
	public static List<SoccerMatch> extractMatchesFromFile(String filePath)
			throws IOException {

		File file = new File(filePath);
		List<SoccerMatch> soccerMatches = new ArrayList<SoccerMatch>();

		BufferedReader reader = new BufferedReader(new FileReader(file));

		String line;

		while( (line=reader.readLine()) != null ){
			if(!line.startsWith("//")){	//The file can contain comment
				soccerMatches.add(SoccerMatch.extractOneSoccerMatch(line));
			}
		}
		reader.close();
		return soccerMatches;
	}

	public static void theUltimate(String queryFile, int daysBeforeMatch,
			int daysAfterMatch, String baseDirectory) throws IOException,
			JSONException, InterruptedException, ExecutionException {

		List<SoccerMatch> allMatches = FileHelper.extractMatchesFromFile(queryFile);

		for(SoccerMatch match : allMatches){
			FileHelper fileHelper = new FileHelper(match, daysBeforeMatch,
					daysAfterMatch, baseDirectory);
			
			fileHelper.run();
		}

	}

	private static void writeMatchToFile(SoccerMatch soccerMatch,
			int daysBeforeMatch, int daysAfterMatch, String baseDirectory)
					throws JSONException {

		LocalDate matchDate = soccerMatch.matchDate();
		LocalDate querySince = matchDate.minusDays(daysBeforeMatch);
		LocalDate queryUntil = matchDate.plusDays(daysAfterMatch);

		//File format is: BaseDirectory/Date_HomeTeam_AwayTeam.txt
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(baseDirectory);
		stringBuilder.append(File.separator);
		stringBuilder.append(soccerMatch.getDateString());
		stringBuilder.append('_');
		stringBuilder.append(soccerMatch.getHomeTeam());
		stringBuilder.append('_');
		stringBuilder.append(soccerMatch.getAwayTeam());
		
		File outputFile = new File(stringBuilder.toString());
		if(outputFile.exists()){
			outputFile.delete();
		}

		writeMatchDetail(soccerMatch, new File(stringBuilder.toString()));
		
		TweetCrawler homeTeamCrawler = new TweetCrawler(soccerMatch
				.getHomeTeam().replaceAll(" ", "%20"), querySince.toString(),
				queryUntil.toString());

		homeTeamCrawler.retrieveTweets(soccerMatch.getHomeTeam(),
				stringBuilder.toString());

		TweetCrawler awayTeamCrawler = new TweetCrawler(soccerMatch
				.getAwayTeam().replaceAll(" ", "%20"), querySince.toString(),
				queryUntil.toString());

		awayTeamCrawler.retrieveTweets(soccerMatch.getAwayTeam(),
				stringBuilder.toString());
	}
	
	private static void writeMatchDetail(SoccerMatch soccerMatch,
			File outputFilePath) {
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					outputFilePath, true));
			
			JSONObject match = new JSONObject(soccerMatch);
			writer.write(match.toString());
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private SoccerMatch soccerMatch;
	private int daysBeforeMatch;
	private int daysAfterMatch;
	private String baseDirectory;
	
	public FileHelper(SoccerMatch soccerMatch, int daysBeforeMatch,
			int daysAfterMatch, String baseDirectory) {
		this.soccerMatch = soccerMatch;
		this.daysAfterMatch = daysAfterMatch;
		this.daysBeforeMatch = daysBeforeMatch;
		this.baseDirectory = baseDirectory;
	}

	@Override
	public void run() {
		try {
			this.writeMatchToFile(soccerMatch, daysBeforeMatch, daysAfterMatch,
					baseDirectory);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
