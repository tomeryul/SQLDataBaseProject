package electionProg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import queries.stage1.querieCitizen;




public class BallotBox<T extends Citizen>  implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public enum eBallotType {Citizen,Soldier,CoronaCitizen,CoronaSoldier}
	
	protected eBallotType type;
	protected static int counterBox = 1;
	protected int ballotBoxNumber;
	protected String address;
	protected ArrayList<T> allowedVoters;
	protected int numOfVoters = 0; // default 0
	protected float percentage; // relevant from case 8
	protected int votes = 0; // default 0
	protected ArrayList<Integer> partyVotes; // relevant from case 8 -> size of numOfParties

	
	public BallotBox(String address,eBallotType type) {
		this.ballotBoxNumber = counterBox++;
		this.address = address;
		this.allowedVoters = new ArrayList<T>();
		this.partyVotes=new ArrayList<Integer>();
		this.type=type;
	

	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getClass().getSimpleName()+" "+getType().name() + " [ballotBoxNumber=" + ballotBoxNumber + ", address=" + address
				+ ", allowedVoters=\n\n");
		for (int i = 0; i < allowedVoters.size(); i++)
			sb.append(allowedVoters.get(i));

		return sb.toString();
	}
	
	public int getballotBoxNumber() {
		return this.ballotBoxNumber;
	}
	 public String getaddress() {
		 return this.address;
	 }
	
	public String getStrBallotType() {
		if(this.type == eBallotType.Citizen) {
			return "Citizen";
		}
		
		else if(this.type == eBallotType.CoronaCitizen) {
			return "CoronaCitizen";
		}
		else if(this.type == eBallotType.CoronaSoldier) {
			return "CoronaSoldier";
		}
		
		return "Soldier";
		
	}
	
	public static eBallotType getEnumBallotType(String side) {
		if (side == "Citizen") {
			return eBallotType.Citizen;
		}
		
		else if (side == "CoronaCitizen") {
			return eBallotType.CoronaCitizen;
		}
		else if (side == "CoronaSoldier") {
			return eBallotType.CoronaSoldier;
		}
		return eBallotType.Soldier;
	}
	

	@SuppressWarnings("unchecked")
	public void setVoters(ArrayList<Citizen> arrVoters) {
		this.allowedVoters = (ArrayList<T>) arrVoters;
	}
	
	public void setballotBoxNumber(int ballotBoxNumber) {
		this.ballotBoxNumber = ballotBoxNumber;
	}
	
	public void setnumOfVoters(int numOfVoters) {
		this.numOfVoters = numOfVoters;
	}
	
	public void setVotes(int votes) {
		this.votes = votes;
	}
	
	public int getVotes() {
		return votes;
	}
	
	public ArrayList<Integer> getPartyVotes() {
		return partyVotes;
	}

	public int getNumOfVoters() {
		return numOfVoters;
	}
	
	public ArrayList<T> getAllowedVoters(){
		return allowedVoters;
	}
	
	public void setPartyVotesSize(int size) {
		for (int i = 0; i < size; i++) {
			partyVotes.add(0);
		}
	}
	

	public eBallotType getType() {
		return type;
	}
	

	public void newArrayOfCitizenInBallotBox(Citizen[] allowedVoters) {
		Arrays.copyOf(allowedVoters, allowedVoters.length * 2);
	}

	public boolean wantToVote(Citizen allowedVoters) {
		for (int i = 0; i < this.allowedVoters.size(); i++) {
			System.out.println();
		}
		return false;
	}

	public int getBallotBoxNumber() {
		return ballotBoxNumber;
	}


	@SuppressWarnings("unchecked")
	public boolean addCitizenToBallot(Citizen newCitizen) {
		allowedVoters.add((T) newCitizen);
		numOfVoters++;
		querieCitizen.AddVoterToBallotBox(this.ballotBoxNumber, numOfVoters);
		return true;
	}
	
	public void uppVote(int partyIndex) {
		votes++;
		querieCitizen.uppVoteToBallotBox(this.ballotBoxNumber, votes);
		partyVotes.set(partyIndex, partyVotes.get(partyIndex)+1);
	}
	
	public void setPercentage() {
		percentage=((float) votes/numOfVoters)*100;
		
	}
	public float getPercentage() {
		
		return percentage;
	}

}
