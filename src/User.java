import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;


public class User {
	
	public static void main(String [] args){
		HashMap<String,Player> players = ParsePlayerData.getPlayerData();
		//HashMap<String,Player> players = null;
		System.out.println("Welcome to the stat tracker 3000!!");
		Scanner sc = new Scanner(System.in);
		
		while(true){
			System.out.println("\nHere is the Menu, choose a menu number!:");
			int choice = 0;
			try{
				System.out.println("1. Search for a player's stat");
				System.out.println("2. Get the ten players who play more than"
						+ " 20 minutes a game, average more than 5 assists a game"
						+ " with the lowest turnover to assists ratio");
				System.out.println("3. Get the ten players with the most points "
						+ "per 36 minutes played");
				System.out.println("4. Get the ten players with the highest"
						+ " points per million dollars salary");
				System.out.println("5. Quit");
				choice = sc.nextInt();
			}
			catch(InputMismatchException e){
				System.out.println("Please choose a number!");
			}
			switch(choice){
			case 1: 
				System.out.println("\nWhat Player?");
				String playerName = null;
				try{
					while(playerName == null || playerName.equals("") ){
						playerName = sc.nextLine();
					}
				}
				catch(InputMismatchException e){
					System.out.println("Please enter a valid name!");
				}
				if(!players.containsKey(playerName)){
					System.out.println("This player is not in the NBA!");
					continue;
				}
				System.out.println("\nWhat stat, choose a number?");
				System.out.println("1. games played");
				System.out.println("2. minutes");
				System.out.println("3. field goal percentage");
				System.out.println("4. three point percentage");
				System.out.println("5. ftPercentage");
				System.out.println("6. rebounds");
				System.out.println("7. assists");
				System.out.println("8. blocks");
				System.out.println("9. steals");
				System.out.println("10. fouls");
				System.out.println("11. turnovers");
				System.out.println("12. points");
				System.out.println("13. free throws made per game");
				System.out.println("14. free throws attempted per game");
				System.out.println("15. field goals made per game");
				System.out.println("16. field goals attempted per game");
				System.out.println("17. three pointers made per game");
				System.out.println("18. three pointers attempted per game");

				int stat  = 0;
				try{
					 stat = sc.nextInt();
				}
				catch(InputMismatchException e){
					System.out.println("Please enter a valid number choice!");
					continue;
				}
				if(stat<1 || stat>18){
					System.out.println("Please enter a valid number choice!");
					continue;
				}
				choice1(sc, players, playerName, stat);
				break;
			case 2:
				choice2(players);
				break;
			case 3: 
				choice3(players);
				break;
			case 4:
				choice4(players);
				break;
			case 5: 
				System.out.println("\nThank you for using our system!");
				System.exit(1);
				break;
			default:
				continue;
			}
		}
		
	}
	
	public static void choice4(HashMap<String, Player> players){
		PointsPerSalary [] maxList =
				new PointsPerSalary [players.keySet().size()];
		int i =0;
		for(String key : players.keySet()){
			Player p = players.get(key);
			if(p.points.get(16) == null || p.salary == 0 || p.points.get(16)<5){
				PointsPerSalary obj = new PointsPerSalary(-1 , p);
				maxList[i] = obj;
				i++;
			}
			else{
				double average = p.points.get(16)/(p.salary/1000000);
				PointsPerSalary obj = new PointsPerSalary(average , p);
				maxList[i] = obj;
				i++;
			}
		}
		Arrays.sort(maxList);
		int count = 1;
		for(int j = maxList.length-1;j>=maxList.length-10;j--){
			PointsPerSalary obj = maxList[j];
			System.out.println(count+") "+obj.player.name+": " + obj.ratio);
			count++;
		}
		
	}
	
	
	
	public static void choice3(HashMap<String,Player> players){
		PointsPer36 [] maxList =
				new PointsPer36 [players.keySet().size()];
		int i =0;
		for(String key : players.keySet()){
			Player p = players.get(key);
			if(p.points.get(16) == null || p.minutes.get(16) == null ||
					p.minutes.get(16)<10 ){
				PointsPer36 obj = new PointsPer36(-1 , p);
				maxList[i] = obj;
				i++;
			}
			else{
				double average = p.points.get(16)/p.minutes.get(16);
				average *=36;
				PointsPer36 obj = new PointsPer36(average , p);
				maxList[i] = obj;
				i++;
			}
		}
		Arrays.sort(maxList);
		int count = 1;
		for(int j = maxList.length-1;j>=maxList.length-10;j--){
			PointsPer36 obj = maxList[j];
			System.out.println(count+") "+obj.player.name+": " + obj.average);
			count++;
		}
		
	}
	
	public static void choice2(HashMap<String,Player> players){
		TurnoverRatioClass [] maxList =
				new TurnoverRatioClass [players.keySet().size()];
		int i =0;
		for(String key : players.keySet()){
			Player p = players.get(key);
			if(p.assists.get(16) == null || p.turnovers.get(16) == null ||
					p.turnovers.get(16)== 0||p.assists.get(16)<5 ||
					p.minutes.get(16)<20 ){
				TurnoverRatioClass obj = new TurnoverRatioClass(-1 , p);
				maxList[i] = obj;
				i++;
			}
			else{
				double ratio = p.assists.get(16)/p.turnovers.get(16);
				TurnoverRatioClass obj = new TurnoverRatioClass(ratio , p);
				maxList[i] = obj;
				i++;
			}
		}
		Arrays.sort(maxList);
		int count = 1;
		for(int j = maxList.length-1;j>=maxList.length-10;j--){
			TurnoverRatioClass obj = maxList[j];
			System.out.println(count+") "+obj.player.name+": " + obj.ratio);
			count++;
		}
		
	}
	
	public static void choice1(Scanner sc,HashMap<String,Player> players, 
			String playerName, int stat){
		Player player = players.get(playerName.trim());
		int year = -1; 
		System.out.println();
		switch(stat){
		case 1:
			year = getYear(sc, players, playerName);
			if(year == -1){
				return;
			}
			System.out.println(player.name+"'s games played in 20"+year+": "+player.gamesPlayed.get(year));
			break;
		case 2:
			year = getYear(sc, players, playerName);
			if(year == -1){
				return;
			}
			System.out.println(player.name+"'s minutes per game in 20"+year+": "+player.minutes.get(16));
			break;
		case 3:
			year = getYear(sc, players, playerName);
			if(year == -1){
				return;
			}
			System.out.println(player.name+"'s field goal percentage in 20"+year+" : "+player.fgPercentage.get(16));
			break;
		case 4:
			year = getYear(sc, players, playerName);
			if(year == -1){
				return;
			}
			System.out.println(player.name+"'s three point percentage in 20"+year+": "+player.threepPercentage.get(16));
			break;
		case 5:
			year = getYear(sc, players, playerName);
			if(year == -1){
				return;
			}
			System.out.println(player.name+"'s free throw percentage in 20"+year+": "+player.ftPercentage.get(16));
			break;
		case 6:
			year = getYear(sc, players, playerName);
			if(year == -1){
				return;
			}
			System.out.println(player.name+"'s rebounds in 20"+year+": "+player.rebounds.get(16));
			break;
		case 7:
			year = getYear(sc, players, playerName);
			if(year == -1){
				return;
			}
			System.out.println(player.name+"'s assists in 20"+year+": "+player.assists.get(16));
			break;
		case 8:
			year = getYear(sc, players, playerName);
			if(year == -1){
				return;
			}
			System.out.println(player.name+"'s blocks in 20"+year+": "+player.blocks.get(16));
			break;
		case 9:
			year = getYear(sc, players, playerName );
			if(year == -1){
				return;
			}
			System.out.println(player.name+"'s steals in 20"+year+": "+player.steals.get(16));
			break;
		case 10:
			year = getYear(sc, players, playerName);
			if(year == -1){
				return;
			}
			System.out.println(player.name+"'s fouls in 20"+year+": "+player.fouls.get(16));
			break;
		case 11:
			year = getYear(sc, players, playerName);
			if(year == -1){
				return;
			}
			System.out.println(player.name+"'s turnovers in 20"+year+": "+player.turnovers.get(16));
			break;
		case 12:
			year = getYear(sc, players, playerName);
			if(year == -1){
				return;
			}
			System.out.println(player.name+"'s points in 20"+year+": "+player.points.get(16));
			break;
		case 13:
			year = getYear(sc, players, playerName);
			if(year == -1){
				return;
			}
			System.out.println(player.name+"'s free throws made in 20"+year+": "+player.FTM.get(16));
			break;
		case 14:
			year = getYear(sc, players, playerName);
			if(year == -1){
				return;
			}
			System.out.println(player.name+"'s free throws attempted in 20"+year+": "+player.FTA.get(16));
			break;
		case 15:
			year = getYear(sc, players, playerName);
			if(year == -1){
				return;
			}
			System.out.println(player.name+"'s field goals made in 20"+year+": "+player.FGM.get(16));
			break;
		case 16:
			year = getYear(sc, players, playerName);
			if(year == -1){
				return;
			}
			System.out.println(player.name+"'s field foals attempted in 20"+year+": "+player.FGA.get(16));
			break;
		case 17:
			year = getYear(sc, players, playerName);
			if(year == -1){
				return;
			}
			System.out.println(player.name+"'s three pointers made in 20"+year+": "+player.threePM.get(16));
			break;
		case 18:
			year = getYear(sc, players, playerName);
			if(year == -1){
				return;
			}
			System.out.println(player.name+"'s three pointers attempted in 20"+year+": "+player.threePA.get(16));
			break;
		}
		
	}
	public static int getYear(Scanner sc, HashMap<String, Player> players, String name){
		System.out.println("\nWhat year are you looking for?");
		try{
			int input = sc.nextInt();
			if(input<1980 || input >2016){
				System.out.println("This is not a valid year!");
				return -1;
			}
			String stringYear = input+"";
			int parsed = Integer.parseInt(stringYear.substring(2));
			if(!players.get(name).blocks.containsKey(parsed)){
				System.out.println("This player did not play this year!");
				return -1;
			}
			else{
				return parsed;
			}
			
		}
		catch(InputMismatchException e){
			System.out.println("Please pick a valid year");
			return -1;
		}
	}

	
	
	private static class TurnoverRatioClass implements Comparable<TurnoverRatioClass>{
		
		protected double ratio;
		protected Player player;
		
		public TurnoverRatioClass(double ratio, Player player){
			this.ratio = ratio;
			this.player = player;
		}
		
		@Override
		public int compareTo(TurnoverRatioClass o) {
			if( this.ratio < o.ratio){
				return -1;
			}
			else if(this.ratio > o.ratio){
				return 1;
			}
			return 0;
		}
	}
	
private static class PointsPer36 implements Comparable<PointsPer36>{
		
		protected double average;
		protected Player player;
		
		public PointsPer36(double average, Player player){
			this.average = average;
			this.player = player;
		}
		
		@Override
		public int compareTo(PointsPer36 o) {
			if( this.average < o.average){
				return -1;
			}
			else if(this.average > o.average){
				return 1;
			}
			return 0;
		}
	}

private static class PointsPerSalary implements Comparable<PointsPerSalary>{
	
	protected double ratio;
	protected Player player;
	
	public PointsPerSalary(double ratio, Player player){
		this.ratio = ratio;
		this.player = player;
	}
	
	@Override
	public int compareTo(PointsPerSalary o) {
		if( this.ratio < o.ratio){
			return -1;
		}
		else if(this.ratio > o.ratio){
			return 1;
		}
		return 0;
	}
}


}
