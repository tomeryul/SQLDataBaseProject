package electionProg;

public class Soldier extends Citizen implements Weaponable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean carryWeapon=false;
	public Soldier(String nameOfPerson, int id, int yearOfBirth, boolean isInInsulation) {
		super(nameOfPerson, id, yearOfBirth, isInInsulation);
		
	}
	public void setCarryWeapon(String ans) {
		if(ans.equalsIgnoreCase("yes"))
			carryWeapon=true;
	}
	
	public String getCarryWeapon() {
		if(carryWeapon == true)
			return "yes";
		else
			return 	"no";
	}
	
	@Override
	public String carryWeapon() {
		if(carryWeapon)
		return (nameOfPerson+" is carrying a weapon!\n");
		else
			return (nameOfPerson+" is not carrying a weapon!\n");
		
	}
	@Override
	public String toString() {
		return super.toString()+carryWeapon();
	}

	
	

}
