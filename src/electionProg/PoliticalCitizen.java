package electionProg;



public class PoliticalCitizen extends Citizen  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int voters;
	private String partyName;
	
	public PoliticalCitizen(String nameOfPerson, int id, int yearOfBirth, boolean isInInsulation,String partyName) {
		super(nameOfPerson, id, yearOfBirth, isInInsulation);
		this.partyName=partyName;
	}

	

	@Override
	public String toString() {
		return super.toString()+" voters=" + voters + ",belongs to " + partyName +"\n" ;
	}



	public String getPartyName() {
		return partyName;
	}
	
	public void AddVoters() {
		this.voters += 1;
	}


	public void setVoters(int voters) {
		this.voters = voters;
	}
	
	public int getVoters() {
		return this.voters;
	}
	
}
