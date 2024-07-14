package electionProg;

import java.io.Serializable;


public class Citizen implements Comparable<Citizen>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String nameOfPerson;
	protected int id;
	protected int yearOfBirth;
	protected boolean isInInsulation;
	protected int ballotBox; // will be set later  
	protected boolean wantToVote;
	
	protected int daysSick=0;


	public Citizen(String nameOfPerson, int id, int yearOfBirth, boolean isInInsulation) {
		this.nameOfPerson = nameOfPerson;
		this.yearOfBirth = yearOfBirth;
		this.isInInsulation = isInInsulation;
		this.setId(id);
	}
	public Citizen() {
	}
	
	public void setDaysSick(int daysSick) {
		this.daysSick = daysSick;
	}
	
	public int getDaysSick() {
		return daysSick;
	}


	@Override
	public String toString() {
		if(!isInInsulation)
		return getClass().getSimpleName()+" [nameOfPerson=" + nameOfPerson + ", id=" + id + ", yearOfBirth=" + yearOfBirth
				+ ", isInInsulation=" + isInInsulation + ", ballotBox=" + ballotBox + "]\n";
		else
			return getClass().getSimpleName()+" [nameOfPerson=" + nameOfPerson + ", id=" + id + ", yearOfBirth=" + yearOfBirth
					+ ", isInInsulation=" + isInInsulation + ", ballotBox=" + ballotBox + " , daysSick="+daysSick+ "]\n";
			
	}

	public int getBallotBox() {
		return ballotBox;
	}

	public void setNameOfPerson(String nameOfPerson) {
		this.nameOfPerson = nameOfPerson;
	}

	public int getYearOfBirth() {
		return yearOfBirth;
	}

	// confirm of correct 9 digits id number
	public void setId(int id)  {
		this.id=id;
		}

	public String getIsIsolated() {
		if(isInInsulation == true)
			return "yes";
		else
			return "no";
	
	}

	public boolean isOldToVote(int CurrentYear) {
		if(CurrentYear- this.yearOfBirth >= 18) {
			return true;
		} else 
			
			return false;
		
	}
	
	
	public void setBallotBox(int ballotBox) {
	
			this.ballotBox = ballotBox;
		
	}

	public void setYearOfBirth(int yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	public void setInInsulation(boolean isInInsulation) {
		this.isInInsulation = isInInsulation;
	}

	public boolean isInInsulation() {
		return isInInsulation;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return nameOfPerson;
	}

	public boolean equals(Object obj) {
		if(!(obj instanceof Citizen))
			return false;
		else
		return id==((Citizen)obj).getId();
	}

	public int compareTo(Citizen citi) {
		if(equals(citi))
		return 0;
		return 1;
	}

}
