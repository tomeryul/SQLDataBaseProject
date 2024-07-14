package electionProg;

import java.util.ArrayList;
import java.io.Serializable;




public class PoliticalParty implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nameOfParty;
	
	public enum epoliticalSide {
		RIGHT, LEFT, CENTER
	};	
	
	private epoliticalSide politicalSide;
	private int dateOfEstablishment;
	private ArrayList<PoliticalCitizen> partyElectors;
	private int numOfVotes;

	public PoliticalParty(String nameOfParty, epoliticalSide politicalSide,int date) {
		this.dateOfEstablishment = date ;
		this.nameOfParty = nameOfParty;
		this.politicalSide = politicalSide;
		this.partyElectors = new ArrayList<PoliticalCitizen>();
		this.numOfVotes = 0;

	}

	public String getNameOfParty() {
		return nameOfParty;
	}

	public String getStrPoliticalSide() {
		if(this.politicalSide == epoliticalSide.RIGHT) {
			return "RIGHT";
		}
		
		else if(this.politicalSide == epoliticalSide.LEFT) {
			return "LEFT";
		}
		
		return "CENTER";
		
	}
	public static epoliticalSide getEnumPoliticalSide(String side) {
		if (side == "RIGHT") {
			return epoliticalSide.RIGHT;
		}
		
		else if (side == "LEFT") {
			return epoliticalSide.LEFT;
		}
		return epoliticalSide.CENTER;
	}
	
	public epoliticalSide getPoliticalSide() {
		return politicalSide;
	}

	public int getDateOfEstablishment() {
		return dateOfEstablishment;
	}

	public ArrayList<PoliticalCitizen> getPartyElectors() {
		return partyElectors;
	}

	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append("politicalParty [nameOfParty=" + nameOfParty + ", politicalSide=" + politicalSide
				+ ", dateOfEstablishment=" + dateOfEstablishment + ", partyElectors=\n\n");
		for (int i = 0; i < partyElectors.size(); i++) {
			sb.append(partyElectors.get(i));
		}
		return sb.toString();
	}
	
	public String partyInfo() {
		return "politicalParty [nameOfParty=" + nameOfParty + ", politicalSide=" + politicalSide
				+ ", dateOfEstablishment=" + dateOfEstablishment;
	}

	public boolean addElector(PoliticalCitizen poliCitizen) {
		partyElectors.add(poliCitizen);
		return true;
	}

	public void uppVotes() {
		numOfVotes++;
		
	}
	
	public int getNumOfVoters() {
		return this.numOfVotes;
	}
	
	public void setNumOfVotes(int votes) {
		this.numOfVotes = votes;
		
	}
	
	public void setPartyElectors(ArrayList<PoliticalCitizen> PartyElectors) {
		this.partyElectors = PartyElectors;
	}

}
