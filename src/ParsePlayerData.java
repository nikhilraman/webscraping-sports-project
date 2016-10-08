import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParsePlayerData {  
	
	protected static HashMap<String, String> playerURLs = new HashMap<String, String>(); 
	
	protected static HashMap<String, String> playerInfoURLs = new HashMap<String, String>();
	
	protected static HashSet<String> teamURLs = new HashSet<String>(); 
	
	protected static HashMap<String,Player> players = new HashMap<String,Player>();
	
	/*public static void main(String[] args) { 
		long startTime = System.currentTimeMillis();
		getPlayerData();
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
		 
	}*/
	
	public static HashMap<String,Player> getPlayerData() { 
		playerURLs = getPlayerURLs();  
		
		System.out.println("Retrieving Data. Please be Patient!");
		int count = 0; 
		
		for (String playerName: playerURLs.keySet()) {  
			count++; 
			if (count > 0 && count % 40 == 0) { 
				System.out.println("Processing........");
			}
			
			String playerURI = playerURLs.get(playerName);   
			String playerInfoURI = playerInfoURLs.get(playerName);
			
			Document statsDoc = null;
			try {
				statsDoc = Jsoup.connect(playerURI).get();  
			} catch (IOException e) {
				System.out.println("unable to connect");
				e.printStackTrace(); 
				return null;
			}  
			
			Player x = new Player(); 
			x.name = playerName;
			
			Elements statRows = statsDoc.getElementsByClass("stathead").first().parent().children(); 
			//System.out.println(playerName + " - " + statRows.size()); 
			
			for (int i = 2; i < statRows.size() - 1; i++) { 
				Element row = statRows.get(i); 
				Elements stats = row.children(); 
				Integer year = Integer.parseInt(stats.get(0).text().substring(5)); 
				double GP = Double.parseDouble(stats.get(2).text()); 
				double MIN = Double.parseDouble(stats.get(4).text()); 
				String[] fgs = stats.get(5).text().split("\\-"); 
				double FGM = Double.parseDouble(fgs[0]); 
				double FGA = Double.parseDouble(fgs[1]);
				double FGP = Double.parseDouble(stats.get(6).text()); 
				String[] threes = stats.get(7).text().split("\\-"); 
				double threePM = Double.parseDouble(threes[0]); 
				double threePA = Double.parseDouble(threes[1]);
				double threePP = Double.parseDouble(stats.get(8).text()); 
				String[] fts = stats.get(9).text().split("\\-"); 
				double FTM = Double.parseDouble(fts[0]); 
				double FTA = Double.parseDouble(fts[1]);
				double FTP = Double.parseDouble(stats.get(10).text());
				double REB = Double.parseDouble(stats.get(13).text());
				double AST = Double.parseDouble(stats.get(14).text());
				double BLK = Double.parseDouble(stats.get(15).text());
				double STL = Double.parseDouble(stats.get(16).text()); 
				double PF = Double.parseDouble(stats.get(17).text());
				double TO = Double.parseDouble(stats.get(18).text());
				double PTS = Double.parseDouble(stats.get(19).text());
				
				x.gamesPlayed.put(year, GP); 
				x.minutes.put(year, MIN); 
				x.fgPercentage.put(year, FGP); 
				x.threepPercentage.put(year, threePP); 
				x.ftPercentage.put(year, FTP); 
				x.rebounds.put(year, REB); 
				x.assists.put(year, AST); 
				x.blocks.put(year, BLK); 
				x.steals.put(year, STL); 
				x.fouls.put(year, PF); 
				x.turnovers.put(year, TO); 
				x.points.put(year, PTS);   
				x.FGM.put(year, FGM); 
				x.FGA.put(year, FGA); 
				x.threePM.put(year, threePM);
				x.threePA.put(year, threePA);
				x.FTM.put(year, FTM);
				x.FTA.put(year, FTA);
				
				/*if (i == statRows.size() - 2) { 
					System.out.print(playerName + " - " + year + " - " + GP + " - " + MIN + " - " + FGP + " - " + threePP + " - " + FTP + " - ");
					System.out.println(REB + " - " + AST + " - " + BLK + " - " + STL + " - " + PTS + "pts - " + FGM + " - " + FTM + " - " + threePM);
				}*/
			}  
			
			players.put(x.name,x);  
			
			/* ---------- --------- GET PLAYER SALARIES ------------------------ */
			
			// GET PLAYER SALARIES  --- Highlight and Comment out section below: cmd+ctrl+/
			
			
			Document infoDoc = null;
			try {
				infoDoc = Jsoup.connect(playerInfoURI).get();  
			} catch (IOException e) {
				System.out.println("unable to connect");
				e.printStackTrace(); 
				return null;
			}   
			
			Elements salaryInfo = infoDoc.select("li.first.last");
			
			if (salaryInfo.size() > 0) { 
				String salary = salaryInfo.first().child(0).child(0).child(0).text(); 
				double salaryNum;
				try {
					salaryNum = NumberFormat.getNumberInstance(java.util.Locale.US).parse(salary.substring(1)).doubleValue(); 
					x.salary = salaryNum;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(); 
					return players;
				} 
				//System.out.println(playerName + ": " + salaryInfo.size() + " - " + salaryNum); 
			} 
			
			/* ------------ FINISHED GETTING PLAYER SALARIES ------------------------ */
			
		}   
		return players;
	}
	
	// private helper method to get all of the URLs for all teams rosters
	private static HashSet<String> getTeamURLs() { 
		
		HashSet<String> teamURLs = new HashSet<String>();
		
		String teamsURL = "http://espn.go.com/nba/players";  
	
		// try to connect to the url
		Document doc = null;
		try {
			doc = Jsoup.connect(teamsURL).get();  
		} catch (IOException e) {
			e.printStackTrace(); 
			return null;
		} 
		
		Elements divisions = doc.getElementsByClass("small-logos"); 
		for (Element div: divisions) { 
			Elements teams = div.children();  
			for (Element team: teams) {  
				String teamURI = team.child(0).attr("href");  
				String base = teamURI.substring(0, 28); 
				String end = teamURI.substring(28); 
				String rosterURI = base + "roster/" + end;  
				//System.out.println(teamURI);
				//System.out.println(rosterURI); 
				teamURLs.add(rosterURI);
			}
		}  
		
		return teamURLs; 
	}
	
	private static HashMap<String, String> getPlayerURLs() { 
		teamURLs = getTeamURLs();  
		
		HashMap<String, String> playerURLs = new HashMap<String, String>(); 
		
		for (String teamURI: teamURLs) {  
			Document rosterDoc = null;
			try {
				rosterDoc = Jsoup.connect(teamURI).get();  
			} catch (IOException e) {
				e.printStackTrace(); 
				return null;
			}  
			
			Elements players = rosterDoc.getElementsByClass("sortcell"); 
			/*if (players.size() > 14) { 
				System.out.println("Getting Players!"); 
			} 
			else { 
				System.out.println("Getting Nothing");
			} */ 
			for (Element playerHeader: players) { 
				Element player = playerHeader.child(0);
				String playerName = player.text(); 
				String playerURI = player.attr("href");  
				playerInfoURLs.put(playerName, playerURI);
				// adjust to stats URI
				String base = playerURI.substring(0, 30); 
				String end = playerURI.substring(30); 
				String playerStatsURI = base + "stats/" + end; 
				//System.out.println(playerName + " - " + playerStatsURI);  
				playerURLs.put(playerName, playerStatsURI);
			}	
		} 
		return playerURLs; 
	} 
	
	
	
	
	
	
}
