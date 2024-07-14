package electionProg;

import java.io.Serializable;
import java.util.ArrayList;

public class Set <T extends Comparable<Citizen>>implements Serializable{

  
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		protected ArrayList<Citizen> list;

	   
	    public Set() {
	        list = new ArrayList<Citizen>();
	    }

	    // you can add an object
	    public Boolean add(Citizen obj) {
	        for (int k=0;k<list.size();k++) {
	            if (list.get(k).equals(obj))
	                return false;
	        }
	        list.add(obj);
	        return true;
	    }

	    public Citizen get(int index) {
	        return list.get(index);
	    }
	    public void showList() {
	    	for (int i = 0; i < list.size(); i++) 
	    		System.out.println(list.get(i));	
	    }

		public int size() {
			return list.size();
		}
		
		public ArrayList<Citizen> getList(){
			return list;
		}
	}


