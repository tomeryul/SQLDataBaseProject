package electionProg;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

import electionProg.BallotBox.eBallotType;
import electionProg.PoliticalParty.epoliticalSide;
import queries.stage1.*;

public class Main {

	@SuppressWarnings("unchecked")
	public static void main(String[] args)
			throws TooYoungExcepction, NotValidIdException, InputMismatchException, FileNotFoundException, ClassNotFoundException, IOException {

		Scanner s = new Scanner(System.in);
		boolean fleg = true;
		ElectorsNote election1;
		String loadAns="";
		while(!loadAns.equalsIgnoreCase("yes") &&!loadAns.equalsIgnoreCase("no")) {
			System.out.println("do you want to load election from existing file? (yes/no)\t");
			loadAns=s.next();
			
		}
		
		if(loadAns.equalsIgnoreCase("yes"))
			election1=readFromFile();
		else {
			querieCitizen.deleteAll();
			int electionYear=-1; // default , will change eventually
			while(fleg) {
				try {
					System.out.println("Enter year of election:\t");
					electionYear = s.nextInt();
					fleg=false;
				}
				catch (InputMismatchException e) {
					System.out.println("must be a digit -> Please try again\n ");
					s.nextLine(); 
					fleg=true;

				}
			}


			election1 = new ElectorsNote(electionYear);
			fleg = false;
			BallotBox<Citizen> b1 = new BallotBox<Citizen>("matan",eBallotType.Citizen);
			BallotBox <CoronaCitizen>b2 = new BallotBox<CoronaCitizen>("bneyBrak",eBallotType.CoronaCitizen);
			BallotBox<Soldier> b3 = new BallotBox<Soldier>("elat",eBallotType.Soldier);
			BallotBox<CoronaSoldier> b4 = new BallotBox<CoronaSoldier>("gaza",eBallotType.CoronaSoldier);
			election1.addBallot(b1);
			election1.addBallot(b2);
			election1.addBallot(b3);
			election1.addBallot(b4);
			Citizen citi1 = new Citizen("tomer", 123000000, 1996, false);
			Soldier citi2 = new Soldier("ohad", 915764700, 2001, false);
			CoronaCitizen citi3 = new CoronaCitizen("omer", 209006535, 1997, true);
			CoronaSoldier citi4 = new CoronaSoldier("yoav", 915764707, 2002, true);
			Citizen citi5 = new Citizen("ofir", 123987645, 1996, false);
			election1.addCitizens(citi1);
			election1.addCitizens(citi2);
			election1.addCitizens(citi3);
			election1.addCitizens(citi4);
			election1.addCitizens(citi5);
			PoliticalParty party1 = new PoliticalParty("Likud", epoliticalSide.valueOf("LEFT"), 1950);
			PoliticalParty party2 = new PoliticalParty("Yesh_Atid", epoliticalSide.valueOf("CENTER"), 1970);
			PoliticalParty party3 = new PoliticalParty("National_Unity", epoliticalSide.valueOf("RIGHT"), 2012);
			election1.addParty(party1);
			election1.addParty(party2);
			election1.addParty(party3);
			PoliticalCitizen poliCitizen1 = new PoliticalCitizen("noam", 123456789, 1996, false, "Likud");
			PoliticalCitizen poliCitizen2 = new PoliticalCitizen("roee", 123456777, 1997, false, "Yesh_Atid");
			PoliticalCitizen poliCitizen3 = new PoliticalCitizen("gilad", 123456788, 1997, true, "National_Unity");
			election1.addPoliticalCitizen(poliCitizen1);
			election1.addPoliticalCitizen(poliCitizen2);
			election1.addPoliticalCitizen(poliCitizen3);
		}


		int choise;
		boolean exit = false;

		do {
			System.out.println("Please select an option:\r\n" + "	1 - Add a ballot box \r\n"
					+ "	2 - Add a citizen \r\n" + "	3 - Add a political party \r\n"
					+ "	4 - Add a citizen as an politician of a political party \r\n"
					+ "	5 - Show all ballot boxes\r\n" + "	6 - Show all citizens \r\n"
					+ "	7 - Show all political partys \r\n" + "	8 – Vote time\r\n"
					+ "	9 – Election result \r\n" + "	10 - Exit\r\n" + "");

			try {
				choise = s.nextInt();


				switch (choise) {

				case 1: // done
					System.out.println("you chose to add a ballot box!");
					boolean endBallotCreation = true;
					System.out.println("enter address of ballotbox:\t");
					String ballotAddress = s.next();
					int ballotType;
					do {
						System.out.println("ballot menu:\n1-regular\n2-corona\n3-soldier\n4-soldier corona");
						try {
							ballotType = s.nextInt();
							switch (ballotType) {
							case 1:
								System.out.println("you chose regular ballot!");
								BallotBox <Citizen>regB = new BallotBox<Citizen>(ballotAddress,eBallotType.Citizen);
								election1.addBallot(regB);
								endBallotCreation = false;
								break;
							case 2:
								System.out.println("you chose corona ballot!");
								BallotBox <CoronaCitizen>covB = new BallotBox<CoronaCitizen>(ballotAddress,eBallotType.CoronaCitizen);
								election1.addBallot(covB);
								endBallotCreation = false;
								break;
							case 3:
								System.out.println("you chose soldier ballot!");
								BallotBox <Soldier>solB = new BallotBox<Soldier>(ballotAddress,eBallotType.Soldier);
								election1.addBallot(solB);
								endBallotCreation = false;
								break;
							case 4:
								System.out.println("you chose soldier corona ballot!");
								BallotBox<CoronaSoldier> solCB = new BallotBox<CoronaSoldier>(ballotAddress,eBallotType.CoronaSoldier);
								election1.addBallot(solCB);
								endBallotCreation = false;
								break;

							}
						} catch (InputMismatchException e) {
							System.out.println("must be a digit from 1 to 4-> Please try again\n ");
							s.nextLine(); // Clean buffer
						}

					} while (endBallotCreation);
					break;

					///////////// 2////////////2////////////2////////////2////////////2////////////2///////////////////////

				case 2: // done
					Citizen citizen;

					System.out.println("Enter name for your citizen :");
					String name = s.next();

					System.out.println("Enter id for your citizen :");
					try {
						String tempId = s.next();
						if (tempId.length() != 9)
							throw new NotValidIdException();
						int id = Integer.parseInt(tempId);
						System.out.println("Enter the year of birth of your citizen :");
						int yearOfBirth = s.nextInt();

						String isInInsulation = " ";
						while (!isInInsulation.equalsIgnoreCase("yes") && !isInInsulation.equalsIgnoreCase("no")) {
							System.out.println("Please tell us id your citizen is in insulation? (yes/no):");
							isInInsulation = s.next();
						}
						boolean quarantine;
						if (isInInsulation.equalsIgnoreCase("yes"))
							quarantine = true;
						else
							quarantine = false;

						if (yearOfBirth >= election1.getYear()-21 && yearOfBirth <= election1.getYear()-18) { 
							String weaponAns = "";
							while (!weaponAns.equalsIgnoreCase("no") && !weaponAns.equalsIgnoreCase("yes")) {
								System.out.println("do you carry weapon?\t");
								weaponAns = s.next();
							}if (quarantine) {
								citizen = new CoronaSoldier(name, id, yearOfBirth, quarantine);
								getSickDays(s, citizen);
							}
							else
								citizen = new Soldier(name, id, yearOfBirth, quarantine);

							((Soldier) citizen).setCarryWeapon(weaponAns);
							election1.addCitizens(citizen);
						} else {if (quarantine) {
							citizen = new CoronaCitizen(name, id, yearOfBirth, quarantine);
							getSickDays(s, citizen);
						}
						else
							citizen = new Citizen(name, id, yearOfBirth, quarantine);

						election1.addCitizens(citizen);
						}
					} catch (NotValidIdException e) {
						System.out.println(e.getMessage() + "-> Please try again\n");
					} catch (InputMismatchException e) {
						System.out.println("must be a digit -> Please try again\n ");
						s.nextLine(); // Clean buffer
					} catch (TooYoungExcepction e) {
						System.out.print(e.getMessage() + "-> Please try again\n");
					}
					break;


					///////////// 3////////////3////////////3////////////3////////////3////////////3///////////////////////

				case 3: // done
					System.out.println("you chose to add party!");
					System.out.println("please enter name of party:\t");
					String partyName = s.next();
					int yearOfEstablish = -1; // default
					while (yearOfEstablish < 1948 || yearOfEstablish > Calendar.getInstance().get(Calendar.YEAR)) {
						System.out.println("please enter year of establishment:\t");
						yearOfEstablish = s.nextInt();
					}
					String wing = " ";
					do {
						System.out.println("please enter wing of party (left,center,right)");
						wing = s.next();
					} while (!wing.equalsIgnoreCase("center") && !wing.equalsIgnoreCase("right")
							&& !wing.equalsIgnoreCase("left"));
					epoliticalSide side = epoliticalSide.valueOf(wing.toUpperCase());
					PoliticalParty party = new PoliticalParty(partyName, side, yearOfEstablish);
					election1.addParty(party);

					break;

					///////////// 4////////////4////////////4////////////4////////////4////////////4///////////////////////

				case 4: // done
					System.out.println("you chose to add politicalCitizen!");
					System.out.println("Enter name for your political citizen :");
					String politicalName = s.next();

					System.out.println("Enter id for your political citizen :");
					int politicalId = s.nextInt();
					System.out.println("Enter the year of birth of your political citizen :");
					int politicalYearOfBirth = s.nextInt();
					String politicalIsInInsulation = " ";
					while (!politicalIsInInsulation.equalsIgnoreCase("yes")
							&& !politicalIsInInsulation.equalsIgnoreCase("no")) {
						System.out.println("Please tell us id your political citizen is in insulation? (yes/no):");
						politicalIsInInsulation = s.next();
					}
					boolean politicalQuarantine;
					if (politicalIsInInsulation.equalsIgnoreCase("yes"))
						politicalQuarantine = true;
					else
						politicalQuarantine = false;
					String partyChoice = " "; // default
					while (!election1.isExistName(partyChoice)) {
						System.out.println("Please choose party from the list:\n");
						election1.showPartyList();
						partyChoice = s.next();
					}
					PoliticalCitizen poliCitizen = new PoliticalCitizen(politicalName, politicalId,
							politicalYearOfBirth, politicalQuarantine, partyChoice);
					if (politicalQuarantine)
						getSickDays(s, poliCitizen);
					election1.addPoliticalCitizen(poliCitizen);
					break;

					//////////////// 5////////////5////////////5////////////5////////////5////////////5///////////////////////

				case 5: // done
					election1.showBallots();

					System.out.println();
					break;

					///////////// 6////////////6////////////6////////////6////////////6////////////9///////////////////////

				case 6: // done
					election1.showCitizens();
					break;

					///////////// 7////////////7////////////7////////////7////////////7////////////7///////////////////////

				case 7: // done
					election1.showParties();
					break;

					///////////// 8////////////8////////////8////////////8////////////8////////////8///////////////////////

				case 8: // done
					election1.beginElection();
					election1.setAllPartyVotesSizes(election1.getNumOfparties());
					BallotBox <?>tempBallot;
					ArrayList<Citizen> voters;
					String answer;
					boolean endAnswer;
					for (int i = 0; i < election1.getNumOfBallots(); i++) {
						tempBallot = election1.getAllBallots().get(i);
						System.out.println("ballot " + tempBallot.getBallotBoxNumber() + "-\n");
						voters = (ArrayList<Citizen>) tempBallot.getAllowedVoters();
						for (int j = 0; j < tempBallot.getNumOfVoters(); j++) {
							if (voters.get(j) instanceof Soldier)
								System.out.println(((Soldier) voters.get(j)).carryWeapon());
							endAnswer = false;
							do {
								System.out.println(voters.get(j).getName() + " ,do you want to vote? (yes/no):\t");
								answer = s.next();
								if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("no"))
									endAnswer = true;
							} while (!endAnswer);
							if (answer.equalsIgnoreCase("no"))
								System.out.println(voters.get(j).getName()
										+ " voting is important! ,come back and vote next time!");
							else { // case voter decides to vote!
								String partyVote;
								do {
									System.out.println("choose a party to vote to from the list:\n");
									election1.showPartyList();
									partyVote = s.next();
								} while (partyVote == null || !election1.isExistName(partyVote));
								int partyIndex = election1.getPartyIndex(partyVote);
								ArrayList<Integer> listId = getAllPoly(partyVote,election1);////////////new///////////////
								for(int index = 0; index < listId.size();index+=2) {
									querieCitizen.AddVoteToCitizen(listId.get(index), listId.get(index+1));
								}
								
								tempBallot.uppVote(partyIndex); // upps ballot votes, partyvotes
								election1.uppPartyVotes(partyIndex); // upp party numOfVotes
							}
						}
					}
					break;

					///////////// 9////////////9////////////9////////////9////////////9////////////9///////////////////////
				case 9: // done
					if (!election1.isElectionOn())
						election1.setAllPartyVotesSizes(election1.getNumOfparties());
					election1.showResultsOfElection();

					break;

					///////////// 10////////////10////////////10////////////10////////////10////////////10/////////////////

				case 10:

					saveToFile(election1);
					System.out.println("File was saved successfully...\nThat is all for now, see you later!");
					exit = true;
					break;

				}

			}catch (InputMismatchException e) {
				System.out.println("must be a digit -> Please try again\n ");
				s.nextLine(); // Clean buffer
			}

		} while (!exit);






		s.close();


	}

	public static ArrayList<Integer> getAllPoly(String name,ElectorsNote election1) {
		ArrayList<Integer> mylist = new ArrayList<Integer>();
		Set<Citizen> allCiti = election1.getArryaOfCitizens();
		for(int i = 0; i < allCiti.size(); i++) {
			if(allCiti.get(i) instanceof PoliticalCitizen) {
				if (((PoliticalCitizen) allCiti.get(i)).getPartyName().equalsIgnoreCase(name)) {
					((PoliticalCitizen) allCiti.get(i)).AddVoters();
					mylist.add(allCiti.get(i).getId());
					mylist.add((((PoliticalCitizen) allCiti.get(i)).getVoters()));
				}
			}
		}
		return mylist;
	}

	private static void getSickDays(Scanner s, Citizen citi) {
		int sickDays = -1;
		while (sickDays < 0) {
			System.out.println("how many days are you sick?\t");
			sickDays = s.nextInt();
		}
		citi.setDaysSick(sickDays);

	}

	public static void saveToFile(ElectorsNote election1)
			throws FileNotFoundException, IOException {
		ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("1.dat"));
		o.writeObject(election1);
		o.close();
	}

	public static ElectorsNote readFromFile() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream i = new ObjectInputStream(new FileInputStream("1.dat"));
		ElectorsNote election1 = (ElectorsNote) i.readObject();
		i.close();
		return election1;
	}

}
