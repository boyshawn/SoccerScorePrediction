package assignment3;

import org.joda.time.LocalDate;


public class SoccerMatch {
	//Attributes
	//Date      Time    Home Team           Away Team   Full Time (Half Time) 
	private LocalDate date;
	private String time;
	private String homeTeam;
	private String awayTeam;
	private String halfTimeScore;
	private int homeTeamScore;
	private int awayTeamScore;
	
	//Constructor
	public SoccerMatch(LocalDate date, String time, String homeTeam,
			String awayTeam, int homeTeamScore, int awayTeamScore,
			String halfTimeScore) {
		
		this.date = date;
		this.time = time;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.homeTeamScore = homeTeamScore;
		this.awayTeamScore = awayTeamScore;
		this.halfTimeScore = halfTimeScore;
	}

	public LocalDate matchDate() {
		return date;
	}
	
	public String getDateString(){
		return date.toString();
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	public String getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}

	public String getHalfTimeScore() {
		return halfTimeScore;
	}

	public void setHalfTimeScore(String halfTimeScore) {
		this.halfTimeScore = halfTimeScore;
	}

	public int getHomeTeamScore() {
		return homeTeamScore;
	}

	public void setHomeTeamScore(int homeTeamScore) {
		this.homeTeamScore = homeTeamScore;
	}

	public int getAwayTeamScore() {
		return awayTeamScore;
	}

	public void setAwayTeamScore(int awayTeamScore) {
		this.awayTeamScore = awayTeamScore;
	}
	
	public String getWinner(){
		if(this.homeTeamScore > this.awayTeamScore){	//Home Team win
			return homeTeam;
		}
		else if(this.awayTeamScore > this.homeTeamScore){	//Away Team win
			return awayTeam;
		}
		else{	//Draw
			return "";
		}
	}
	
	@Override
	public String toString(){
		return date.toString() + " " + time + " " + homeTeam + " " + awayTeam + " "
				+ homeTeamScore + ":" + awayTeamScore + " " + halfTimeScore;
	}
	
	public static SoccerMatch extractOneSoccerMatch(String match){
		//17/8/2013	12:45	Liverpool FC	-	Stoke City	1:0 (1:0)
		String[] matchDetails = match.split("\t");
		
		//Date
		String rawDate = matchDetails[0];
		
		//Date massage
		String[] dateSplit = rawDate.split("/");
		LocalDate date = new LocalDate(Integer.parseInt(dateSplit[2]),
				Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[0]));
		
		String time = matchDetails[1];
		String homeTeam = matchDetails[2];
		String awayTeam = matchDetails[4];
		
		String fullTimeScore = matchDetails[5];
		String[] scoreDetails = fullTimeScore.split("[: ()]");
		int homeTeamScore = Integer.parseInt(scoreDetails[0]);
		int awayTeamScore = Integer.parseInt(scoreDetails[1]);
		String halfTimeScore = scoreDetails[3] + ":" + scoreDetails[4];
		
		return new SoccerMatch(date, time, homeTeam, awayTeam, homeTeamScore, awayTeamScore, halfTimeScore);
	}
}
