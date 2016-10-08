import java.util.HashMap;

public class Player { 
	
	public String name; 
	public HashMap<Integer, Double> gamesPlayed;  
	public HashMap<Integer, Double> minutes; 
	public HashMap<Integer, Double> fgPercentage; 
	public HashMap<Integer, Double> threepPercentage;
	public HashMap<Integer, Double> ftPercentage;
	public HashMap<Integer, Double> rebounds; 
	public HashMap<Integer, Double> assists;
	public HashMap<Integer, Double> blocks;
	public HashMap<Integer, Double> steals; 
	public HashMap<Integer, Double> fouls;
	public HashMap<Integer, Double> turnovers;
	public HashMap<Integer, Double> points; 
	public HashMap<Integer, Double> FTM;
	public HashMap<Integer, Double> FTA;
	public HashMap<Integer, Double> FGM; 
	public HashMap<Integer, Double> FGA;
	public HashMap<Integer, Double> threePM;
	public HashMap<Integer, Double> threePA; 
	
	public double salary;
	 
	public Player() { 
		gamesPlayed = new HashMap<Integer, Double>(); 
		minutes = new HashMap<Integer, Double>();
		fgPercentage = new HashMap<Integer, Double>();
		threepPercentage = new HashMap<Integer, Double>(); 
		ftPercentage = new HashMap<Integer, Double>(); 
		rebounds = new HashMap<Integer, Double>(); 
		assists = new HashMap<Integer, Double>(); 
		blocks = new HashMap<Integer, Double>(); 
		steals = new HashMap<Integer, Double>(); 
		fouls = new HashMap<Integer, Double>(); 
		turnovers = new HashMap<Integer, Double>(); 
		points = new HashMap<Integer, Double>(); 
		
		FTM = new HashMap<Integer, Double>(); 
		FTA = new HashMap<Integer, Double>(); 
		FGM = new HashMap<Integer, Double>(); 
		FGA = new HashMap<Integer, Double>(); 
		threePM = new HashMap<Integer, Double>(); 
		threePA = new HashMap<Integer, Double>();
		
	}
	

}
