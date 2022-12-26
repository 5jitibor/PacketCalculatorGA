package PacketCalculatorGA;

import javax.swing.SwingUtilities;

import PacketCalculatorCLI.CLI;
import PacketCalculatorGUI.CierzoTheme.*;
import PacketCalculatorGUI.GUI;

public class PacketCalculator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			if(args.length<1) {
				CierzoTheme.setup();

				SwingUtilities.invokeLater(new Runnable() {
		            public void run() {
						try {
							 @SuppressWarnings("unused")
							GUI a = new GUI();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		        });
			}
			else {
				CLI.executeConsole(args);
			}
			
		
	}

}
