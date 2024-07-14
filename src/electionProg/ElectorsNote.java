package electionProg;

import java.io.Serializable;
import java.util.ArrayList;

import electionProg.BallotBox.eBallotType;
import queries.stage1.querieCitizen;

public class ElectorsNote implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Set<Citizen> arryaOfCitizens;
	private ArrayList<BallotBox<?>> allBallots;
	private ArrayList<PoliticalParty> allParties;
	private int numOfCitizens;
	private int numOfparties;
	private int numOfBallot;
	private int year;
	private boolean electionOn=false;
	public static int currentYear;

	public ElectorsNote(int year) {
		this.arryaOfCitizens = new Set<Citizen>();
		this.allBallots = new ArrayList<BallotBox<?>>();
		this.allParties = new ArrayList<PoliticalParty>();
		this.year = year;
		currentYear=year;
	}

	public void beginElection() {
		electionOn=true;
	}

	public boolean isElectionOn() {
		return electionOn;
	}

	public void showCitizens() {
		System.out.println("Showing citizens:\n");
		for (int i = 0; i < numOfCitizens; i++) {
			System.out.println(arryaOfCitizens.get(i));
		}
	}

	public boolean addParty(PoliticalParty party) {
		if (isExistName(party.getNameOfParty())) {
			System.out.println(party.getNameOfParty() + " already exists!");
			return false;
		}
		querieCitizen.addParty(party);
		allParties.add(party);
		numOfparties++;
		System.out.println("The party was added!");
		return true;
	}

	public void showParties() {
		System.out.println("Showing parties:\n");
		for (int i = 0; i < numOfparties; i++) {
			System.out.println(allParties.get(i));
		}
	}

	public boolean isExistName(String nameOfParty) {
		for (int i = 0; i < numOfparties; i++) {
			if (allParties.get(i).getNameOfParty().equals(nameOfParty))
				return true;
		}
		return false;
	}

	public void showBallots() {
		System.out.println("Showing all ballots:\n");
		for (int i = 0; i < numOfBallot; i++) {
			System.out.println(allBallots.get(i));
		}
	}

	public boolean addBallot(BallotBox<?> ballot) {
		querieCitizen.addBallotBox(ballot);
		allBallots.add(ballot);
		numOfBallot++;
		System.out.println("The ballot was added!");
		return true;
	}

	public boolean addCitizens(Citizen newCitizen) throws TooYoungExcepction {

		if (!newCitizen.isOldToVote(year)) {
			System.out.print(newCitizen.getName());
			throw new TooYoungExcepction();

		}	
		int ballotIndex = (int) (Math.random() * numOfBallot);
		while (!matchCitizenAndBallot(allBallots.get(ballotIndex),newCitizen))
			ballotIndex = (int) (Math.random() * numOfBallot);
		newCitizen.setBallotBox(allBallots.get(ballotIndex).getBallotBoxNumber());
		allBallots.get(ballotIndex).addCitizenToBallot(newCitizen);
		if(arryaOfCitizens.add(newCitizen)) {
			querieCitizen.addCitizen(newCitizen);/////////////////insert to the SQL table///////////////
			if(newCitizen instanceof Soldier) {
				querieCitizen.addSoldie(newCitizen);
			}
			if(newCitizen instanceof PoliticalCitizen) {
				querieCitizen.addPoliticalCitizen(newCitizen);
			}
			numOfCitizens++;
			System.out.println("citizen was added!");
			return true;
		}
		else {
			System.out.println("citizen was not added!");
			return false;
		}

	}

	private boolean matchCitizenAndBallot(BallotBox<?>ballot, Citizen newCitizen) {
		if(ballot.getType()==eBallotType.CoronaCitizen && (newCitizen instanceof Soldier || !newCitizen.isInInsulation()))
			return false;

		else if(ballot.getType()==eBallotType.CoronaSoldier && !(newCitizen instanceof CoronaSoldier))
			return false;

		else if(ballot.getType()==eBallotType.Soldier && (!(newCitizen instanceof Soldier)||newCitizen.isInInsulation()))
			return false;

		else if(ballot.getType()==eBallotType.Citizen && (newCitizen.isInInsulation() || (newCitizen instanceof Soldier)))
			return false;

		return true;
	}




	public Set<Citizen> getArryaOfCitizens() {
		return arryaOfCitizens;
	}

	public int getNumOfparties() {
		return numOfparties;
	}

	public int getNumOfBallots() {
		return numOfBallot;
	}

	public ArrayList<BallotBox<?>> getAllBallots() {
		return allBallots;
	}

	public void setArryaOfCitizens(Set<Citizen> arryaOfCitizens) {
		this.arryaOfCitizens = arryaOfCitizens;
	}

	public void setAllPartyVotesSizes(int size) {
		for (int i = 0; i < numOfBallot; i++) {
			allBallots.get(i).setPartyVotesSize(size);
		}
	}

	public void showPartyList() {
		System.out.println("List of partyies:\n");
		for (int i = 0; i < numOfparties; i++) {
			System.out.println(allParties.get(i).partyInfo());
		}

	}

	public boolean addPoliticalCitizen(PoliticalCitizen poliCitizen) throws TooYoungExcepction {
		if(!addCitizens(poliCitizen))
			return false;
		for (int i = 0; i < numOfparties; i++) {
			if (allParties.get(i).getNameOfParty().equalsIgnoreCase(poliCitizen.getPartyName())) {
				allParties.get(i).addElector(poliCitizen);
				return true;
			}
		}
		return false; // if we made error..

	}

	public int getPartyIndex(String partyVote) {
		for (int i = 0; i < numOfparties; i++) {
			if (allParties.get(i).getNameOfParty().equalsIgnoreCase(partyVote))
				return i;
		}
		return -1; // error mark
	}

	public void uppPartyVotes(int partyIndex) {
		allParties.get(partyIndex).uppVotes();
		querieCitizen.AddVoteToParty(allParties.get(partyIndex),allParties.get(partyIndex).getNumOfVoters());

	}

	public void showResultsOfElection() {
		setPercentages();
		System.out.println("Showing Results: ");
		for (int i = 0; i < numOfBallot; i++) {
			System.out.println("\nballot " + allBallots.get(i).getBallotBoxNumber() + "- total votes: "
					+ allBallots.get(i).getVotes() + " ,with vote percentage of " + allBallots.get(i).getPercentage() + "\n");
			for (int j = 0; j < numOfparties; j++) {
				System.out.println(allParties.get(j).getNameOfParty() + "- votes " + allBallots.get(i).getPartyVotes().get(j));
			}
		}
	}

	private void setPercentages() {
		for (int i = 0; i < numOfBallot; i++)
			allBallots.get(i).setPercentage();

	}
	public int getYear() {
		return year;
	}
}
