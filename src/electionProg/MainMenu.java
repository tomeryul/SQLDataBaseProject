package electionProg;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;

import javax.swing.JOptionPane;

public class MainMenu {

	public static void main(String[] args) throws InputMismatchException, TooYoungExcepction, NotValidIdException, FileNotFoundException, ClassNotFoundException, IOException {
		int opt;
		boolean programOver = false;
		while(!programOver) {
			opt=Integer.parseInt(JOptionPane.showInputDialog("Options:\n"
					+ "1- Console Main\n"
					+ "2- Graphic Main\n"
					+ "3- Exit\n"));
			switch (opt) {
			case 1:
				JOptionPane.showMessageDialog(null, "Console Main");
				Main.main(args);
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "Graphic Main");
				MainUi.main(args);
				break;
			case 3:
				JOptionPane.showMessageDialog(null, "Bye!");
				programOver=!programOver;
				break;

			default:
				JOptionPane.showMessageDialog(null, "wrong input, try again!");
				break;
			}

		}
	}

}
