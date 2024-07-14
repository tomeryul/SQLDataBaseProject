package queries.stage1;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

import electionProg.BallotBox;
import electionProg.Citizen;
import electionProg.CoronaCitizen;
import electionProg.CoronaSoldier;
import electionProg.PoliticalCitizen;
import electionProg.PoliticalParty;
import electionProg.Soldier;
import electionProg.BallotBox.eBallotType;

public class querieCitizen {

	static String databaseName = "";
	static String url = "jdbc:mysql://localhost:3306/" + databaseName;
	static String username = "root";
	static String password = "XXXXXXX";

	static{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		try {

			return DriverManager.getConnection(url,username,password);
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}

	}


	//	this function delete All values from all the tables
	public static void deleteAll() {
		querieCitizen jdbc = new querieCitizen();
		try (Connection con = jdbc.getConnection()){
			jdbc.deleteAllCitizens(con);
			jdbc.deleteAllPoliticalCitizens(con);
			jdbc.deleteAllSoldiers(con);
			jdbc.deleteAllPartys(con);
			jdbc.deleteAllBallotBoxs(con);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	//	 delete All Citizens
	public void deleteAllCitizens(Connection con) {
		try {
			PreparedStatement stmt = con.prepareStatement("DELETE From ElectionDB.allTheCitizens");
			stmt.executeUpdate();
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//	delete All PoliticalCitizens
	public void deleteAllPoliticalCitizens(Connection con) {
		try {
			PreparedStatement stmt = con.prepareStatement("DELETE From ElectionDB.allThePoliticalCitizens");
			stmt.executeUpdate();
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//	delete All Soldiers
	public void deleteAllSoldiers(Connection con) {
		try {
			PreparedStatement stmt = con.prepareStatement("DELETE From ElectionDB.allTheSoldiers");
			stmt.executeUpdate();
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//	delete All Party's
	public void deleteAllPartys(Connection con) {
		try {
			PreparedStatement stmt = con.prepareStatement("DELETE From ElectionDB.allThePartys");
			stmt.executeUpdate();
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//	delete All BallotBox's
	public void deleteAllBallotBoxs(Connection con) {
		try {
			PreparedStatement stmt = con.prepareStatement("DELETE From ElectionDB.allTheBallotBoxs");
			stmt.executeUpdate();
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	//	this function add the values of the regular Citizens to the SQL table
	public static void addCitizen(Citizen citi) {
		querieCitizen jdbc = new querieCitizen();
		try (Connection con = jdbc.getConnection()){
			try {

				PreparedStatement stmt = con.prepareStatement("INSERT INTO ElectionDB.allTheCitizens VALUES (?,?,?,?,?,?)");
				stmt.setInt(1, citi.getId());
				stmt.setString(2, citi.getName());
				stmt.setInt(3, citi.getYearOfBirth());
				stmt.setString(4, citi.getIsIsolated());
				stmt.setInt(5, citi.getBallotBox());
				stmt.setInt(6, citi.getDaysSick());


				stmt.executeUpdate();
				stmt.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}


	//	this function add the missing values for the PoliticalCitizen's
	public static void addPoliticalCitizen(Citizen citi) {
		querieCitizen jdbc = new querieCitizen();
		try (Connection con = jdbc.getConnection()){
			try {

				String partyName = ((PoliticalCitizen) citi).getPartyName();
				int numOfVotes = ((PoliticalCitizen) citi).getVoters();

				PreparedStatement stmt = con.prepareStatement("INSERT INTO ElectionDB.allThePoliticalCitizens VALUES (?,?,?)");
				stmt.setInt(1, citi.getId());
				stmt.setInt(2, numOfVotes);
				stmt.setString(3, partyName);


				stmt.executeUpdate();
				stmt.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}


	//	this function add the missing values for the Soldier's
	public static void addSoldie(Citizen citi) {
		querieCitizen jdbc = new querieCitizen();
		try (Connection con = jdbc.getConnection()){
			try {
				PreparedStatement stmt = con.prepareStatement("INSERT INTO ElectionDB.allTheSoldiers VALUES (?,?)");
				stmt.setInt(1, citi.getId());
				stmt.setString(2, ((Soldier)citi).getCarryWeapon());

				stmt.executeUpdate();
				stmt.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}


	//	this function return all the citizens in the SQL table 
	//	first all the PoliticalCitizens then Soldier and last the regular Citizens
	public Vector<Citizen> getAllCitizens(Connection con) {
		Vector<Citizen> arrCiti = new Vector<Citizen>();
		Vector<Integer> arrOfIDs = new Vector<Integer>();
		try (Statement stmt = con.createStatement()){
			try(ResultSet rs = stmt.executeQuery("SELECT * FROM ElectionDB.allTheCitizens NATURAL JOIN ElectionDB.allThePoliticalCitizens")){
				while (rs.next()) {
					PoliticalCitizen citi = null;

					if(rs.getString(4).equalsIgnoreCase("no"))
						citi = new PoliticalCitizen(rs.getString(2), rs.getInt(1), rs.getInt(3),false,rs.getString(8));
					else {
						citi = new PoliticalCitizen(rs.getString(2), rs.getInt(1), rs.getInt(3),true,rs.getString(8));
						citi.setDaysSick(rs.getInt(6));
					}
					citi.setBallotBox(rs.getInt(5));
					citi.setVoters(rs.getInt(7));

					arrOfIDs.add(rs.getInt(1));
					arrCiti.add(citi);	
				}
			}

			try(ResultSet rs = stmt.executeQuery("SELECT * FROM ElectionDB.allTheCitizens NATURAL JOIN ElectionDB.allTheSoldiers")){
				while (rs.next()) {
					Soldier citi = null;

					if(rs.getString(4).equalsIgnoreCase("no"))
						citi = new Soldier(rs.getString(2), rs.getInt(1), rs.getInt(3),false);
					else {
						citi = new CoronaSoldier(rs.getString(2), rs.getInt(1), rs.getInt(3),true);
						citi.setDaysSick(rs.getInt(6));
					}
					citi.setBallotBox(rs.getInt(5));
					citi.setCarryWeapon(rs.getString(7));

					arrOfIDs.add(rs.getInt(1));
					arrCiti.add(citi);	
				}
			}

			try(ResultSet rs = stmt.executeQuery("SELECT * FROM ElectionDB.allTheCitizens")){
				while (rs.next()) {
					Citizen citi = null;
					int ToInsert = 1;

					if(rs.getString(4).equalsIgnoreCase("no"))
						citi = new Citizen(rs.getString(2), rs.getInt(1), rs.getInt(3),false);
					else {
						citi = new CoronaCitizen(rs.getString(2), rs.getInt(1), rs.getInt(3),true);
						citi.setDaysSick(rs.getInt(6));
					}
					citi.setBallotBox(rs.getInt(5));

					for(int i = 0; i<arrOfIDs.size();i++) {
						if(rs.getInt(1) == arrOfIDs.get(i)) {
							ToInsert = 0;
							break;
						}
					}
					if(ToInsert == 1) {
						arrCiti.add(citi);	
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrCiti;
	}


	//	this function add 1 vote a citizen with the ID we get
	public static void AddVoteToCitizen(int ID, int votes) {
		querieCitizen jdbc = new querieCitizen();
		try (Connection con = jdbc.getConnection()){
			try {
				PreparedStatement stmt = con.prepareStatement("UPDATE `ElectionDB`.`allThePoliticalCitizens` SET `numOfVotes` = '"
						+votes+"' WHERE (`id` = '"+ID+"');");

				stmt.executeUpdate();
				stmt.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}


	//	this function add all party's to the SQL table
	public static void addParty(PoliticalParty Party) {
		querieCitizen jdbc = new querieCitizen();
		try (Connection con = jdbc.getConnection()){
			try {

				PreparedStatement stmt = con.prepareStatement("INSERT INTO ElectionDB.allThePartys VALUES (?,?,?,?)");
				stmt.setString(1, Party.getNameOfParty());
				stmt.setString(2, Party.getStrPoliticalSide());
				stmt.setInt(3, Party.getDateOfEstablishment());
				stmt.setInt(4, Party.getNumOfVoters());


				stmt.executeUpdate();
				stmt.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}


	//	this function return all the kinds of types of citizens if you gives is the id 
	public Citizen getCitizenById(Connection con , int Id) {
		try (Statement stmt = con.createStatement()){
			try(ResultSet rs = stmt.executeQuery("SELECT * FROM ElectionDB.allTheCitizens NATURAL JOIN ElectionDB.allThePoliticalCitizens  where id = "+Id+"")){
				while (rs.next()) {
					PoliticalCitizen citi = null;
					if(rs.getString(4).equalsIgnoreCase("no"))
						citi = new PoliticalCitizen(rs.getString(2), rs.getInt(1), rs.getInt(3),false,rs.getString(8));
					else {
						citi = new PoliticalCitizen(rs.getString(2), rs.getInt(1), rs.getInt(3),true,rs.getString(8));
						citi.setDaysSick(rs.getInt(6));
					}
					citi.setBallotBox(rs.getInt(5));
					citi.setVoters(rs.getInt(7));
					return citi;

				}
			}

			try(ResultSet rs = stmt.executeQuery("SELECT * FROM ElectionDB.allTheCitizens NATURAL JOIN ElectionDB.allTheSoldiers  where id = "+Id+"")){
				while (rs.next()) {
					Soldier citi = null;
					if(rs.getString(4).equalsIgnoreCase("no"))
						citi = new Soldier(rs.getString(2), rs.getInt(1), rs.getInt(3),false);
					else {
						citi = new Soldier(rs.getString(2), rs.getInt(1), rs.getInt(3),true);
						citi.setDaysSick(rs.getInt(6));
					}
					citi.setBallotBox(rs.getInt(5));
					citi.setCarryWeapon((rs.getString(7)));
					return citi;

				}
			}
			try(ResultSet rs = stmt.executeQuery("SELECT * FROM ElectionDB.allTheCitizens where id = "+Id+"")){
				while (rs.next()) {
					Citizen citi = null;
					if(rs.getString(4).equalsIgnoreCase("no"))
						citi = new CoronaCitizen(rs.getString(2), rs.getInt(1), rs.getInt(3),false);
					else {
						citi = new Citizen(rs.getString(2), rs.getInt(1), rs.getInt(3),true);
						citi.setDaysSick(rs.getInt(6));
					}
					citi.setBallotBox(rs.getInt(5));
					return citi;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		Citizen citi = null; // to this part it will never come because we check all the types of citizens above
		return citi;
	}


	//	this function return an array of political Citizens
	private ArrayList<PoliticalCitizen> getArrOfElectors(Connection con, String name) {
		ArrayList<PoliticalCitizen> arrPoli = new ArrayList<PoliticalCitizen>();
		try (Statement stmt = con.createStatement()){
			try(ResultSet rs = stmt.executeQuery("SELECT * FROM ElectionDB.allThePartys, ElectionDB.allThePoliticalCitizens "
					+ "WHERE ElectionDB.allThePartys.nameOfParty = ElectionDB.allThePoliticalCitizens.PartyName AND partyName = '"+name+"'")){
				while (rs.next()) {
					arrPoli.add((PoliticalCitizen)getCitizenById(con,rs.getInt(5)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrPoli;

	}


	//	this function return all the party's and the electors in each party
	public ArrayList<PoliticalParty> getAllPartys(Connection con) {
		ArrayList<PoliticalParty> arrParty = new ArrayList<PoliticalParty>();
		try (Statement stmt = con.createStatement()){
			try(ResultSet rs = stmt.executeQuery("SELECT * FROM ElectionDB.allThePartys")){
				while (rs.next()) {
					ArrayList<PoliticalCitizen> arrPoli = new ArrayList<PoliticalCitizen>();
					PoliticalParty Party = null;
					Party = new PoliticalParty(rs.getString(1),PoliticalParty.getEnumPoliticalSide(rs.getString(2)),rs.getInt(3));
					Party.setNumOfVotes(rs.getInt(4));

					arrPoli = getArrOfElectors(con,rs.getString(1));

					Party.setPartyElectors(arrPoli);
					arrParty.add(Party);	
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrParty;
	}


	// this function add 1 to the party it gets
	public static void AddVoteToParty(PoliticalParty Party, int votes) {
		querieCitizen jdbc = new querieCitizen();
		try (Connection con = jdbc.getConnection()){
			try {
				PreparedStatement stmt = con.prepareStatement("UPDATE `ElectionDB`.`allThePartys` SET `numOfVotes` = '"
						+votes+"' WHERE (`nameOfParty` = '"+Party.getNameOfParty()+"');");

				stmt.executeUpdate();
				stmt.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}


	//	this function add all balltbox's to the SQL table
	public static void addBallotBox(BallotBox<?> theBox) {
		querieCitizen jdbc = new querieCitizen();
		try (Connection con = jdbc.getConnection()){
			try {

				PreparedStatement stmt = con.prepareStatement("INSERT INTO ElectionDB.allTheBallotBoxs VALUES (?,?,?,?,?)");
				stmt.setInt(1, theBox.getballotBoxNumber());
				stmt.setString(2, theBox.getStrBallotType());
				stmt.setString(3, theBox.getaddress());
				stmt.setInt(4, theBox.getNumOfVoters());
				stmt.setInt(5, theBox.getNumOfVoters());


				stmt.executeUpdate();
				stmt.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	//	this function add 1 to the number 
	public static void AddVoterToBallotBox(int ballotIndex, int votes) {
		querieCitizen jdbc = new querieCitizen();
		try (Connection con = jdbc.getConnection()){
			try {
				PreparedStatement stmt = con.prepareStatement("UPDATE `ElectionDB`.`allTheBallotBoxs` SET `numOfVoters` = '"
						+votes+"' WHERE (`Cbox` = '"+ballotIndex+"');");

				stmt.executeUpdate();
				stmt.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	//	this function add 1 to the number of votes 
	public static void uppVoteToBallotBox(int ballotIndex, int votes) {
		querieCitizen jdbc = new querieCitizen();
		try (Connection con = jdbc.getConnection()){
			try {
				PreparedStatement stmt = con.prepareStatement("UPDATE `ElectionDB`.`allTheBallotBoxs` SET `votes` = '"
						+votes+"' WHERE (`Cbox` = '"+ballotIndex+"');");

				stmt.executeUpdate();
				stmt.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	//	this function return an array of citizens from the BallotBoxNumber it got
	private ArrayList<Citizen> getArrOfVoters(Connection con, int BallotBoxNumber) {
		ArrayList<Citizen> arrVoters = new ArrayList<Citizen>();
		try (Statement stmt = con.createStatement()){
			try(ResultSet rs = stmt.executeQuery("SELECT * FROM ElectionDB.allTheBallotBoxs, ElectionDB.allTheCitizens "
					+ "WHERE ElectionDB.allTheBallotBoxs.Cbox = ElectionDB.allTheCitizens.BallotBox AND BallotBox = '"+BallotBoxNumber+"'")){
				while (rs.next()) {
					arrVoters.add(getCitizenById(con,rs.getInt(6)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrVoters;
	}

	//	this function return an array of ballotbox's with the array of Citizens in each Ballot Box
	public ArrayList<BallotBox<?>> getAllBallotBox(Connection con) {
		ArrayList<BallotBox<?>> arrBallotBox = new ArrayList<BallotBox<?>>();
		try (Statement stmt = con.createStatement()){
			try(ResultSet rs = stmt.executeQuery("SELECT * FROM ElectionDB.allTheBallotBoxs")){
				while (rs.next()) {
					ArrayList<Citizen> arrVoters = new ArrayList<Citizen>();
					BallotBox<?> theBallotBox = null;
					String thetype = rs.getString(2);
					if(thetype.equalsIgnoreCase("Citizen")) {	
						theBallotBox = new BallotBox<Citizen>(rs.getString(3),eBallotType.Citizen);
					}
					else if(thetype.equalsIgnoreCase("CoronaCitizen")) {
						theBallotBox = new BallotBox<CoronaCitizen>(rs.getString(3),eBallotType.CoronaCitizen);

					}
					else if(thetype.equalsIgnoreCase("Soldier")) {	
						theBallotBox = new BallotBox<Soldier>(rs.getString(3),eBallotType.Soldier);

					}
					else if(thetype.equalsIgnoreCase("CoronaSoldier")) {	
						theBallotBox = new BallotBox<CoronaSoldier>(rs.getString(3),eBallotType.CoronaSoldier);
					}
					theBallotBox.setballotBoxNumber(rs.getInt(1));
					theBallotBox.setnumOfVoters(rs.getInt(4));
					theBallotBox.setVotes(rs.getInt(5));


					arrVoters = getArrOfVoters(con,rs.getInt(1));		
					theBallotBox.setVoters(arrVoters);
					arrBallotBox.add(theBallotBox);	
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrBallotBox;
	}



	public static void main(String[] args){
		querieCitizen jdbc = new querieCitizen();
		try (Connection con = jdbc.getConnection()){
			System.out.println("--- ALL CITIZENS---");
			jdbc.getAllCitizens(con).forEach(System.out::println);
			System.out.println();
			System.out.println("--- ALL PARTYS---");
			jdbc.getAllPartys(con).forEach(System.out::println);
			System.out.println();
			System.out.println("--- ALL BALLOTBOXES---");
			jdbc.getAllBallotBox(con).forEach(System.out::println);
			System.out.println();




		} catch (Exception e) {
			e.printStackTrace();
		}


	}

}
