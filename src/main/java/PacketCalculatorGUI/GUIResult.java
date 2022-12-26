package PacketCalculatorGUI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import PacketCalculator.Result;
import PacketCalculatorGUI.CierzoTheme.CierzoTheme;
import PacketCalculatorGUI.CierzoTheme.JPanelRound;

public class GUIResult {
	JFrame window;
	boolean showPadding;
	
	public GUIResult(ArrayList<Result> resultsList, boolean showPadding) {
		this.showPadding = showPadding;

		initializateComponents();

		InputStream file = getClass().getClassLoader().getResourceAsStream("icon.png");
		BufferedImage bImage;
		try {
			bImage = ImageIO.read(file);
			//set icon on JFrame menu bar, as in Windows system
			window.setIconImage(bImage);
			//set icon on system tray, as in Mac OS X system
			/*final Taskbar taskbar = Taskbar.getTaskbar();
			taskbar.setIconImage(bImage);*/
		} catch (IOException ex) {
			Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
		}

		setLayout(resultsList);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setVisible(true);
	}
	
	public void initializateComponents() {
		//To create the Frame
		window = new JFrame("Packet Calculator Result");
	}
	
	public void setLayout(ArrayList<Result> resultsList) {
		JPanel panel = new JPanel();

		int rows = resultsList.size() > 1 ? 2 : 1;
		int columns = resultsList.size() > 2 ? 2 : 1;

		GridLayout layout = new GridLayout(rows, columns);
		layout.setHgap(40);
		layout.setVgap(40);
		panel.setLayout(layout);
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		for(Result result : resultsList) {
			panel.add(generateRowResult(result));
		}
		
		window.setContentPane(panel);
		window.pack();
	}
	
	public JPanel generateRowResult(Result result) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 10, 0);
		JLabel title = new JLabel(result.getType());
		title.putClientProperty( "FlatLaf.styleClass", "h2" );
		title.setHorizontalAlignment(JLabel.CENTER);
		panel.add(title, c);

		JPanel gridPanel = new JPanel();
		GridLayout gridLayout = new GridLayout(2, 2);
		gridLayout.setHgap(10);
		gridLayout.setVgap(10);
		gridPanel.setLayout(gridLayout);

		gridPanel.add(generateCardPanel("Total bytes", String.valueOf(result.getNumBytes())));

		String cardTitle = (result.getType().equals("Ethernet") || result.getType().equals("Ethernet.1Q")) ? "Total frames" : "Total cells";
		gridPanel.add(generateCardPanel(cardTitle, String.valueOf(result.getNumFramesCells())));

		gridPanel.add(generateCardPanel("L3/L2 Efficiency", result.getL3l2Efficiency() + " %"));

		if (showPadding) {
			gridPanel.add(generateCardPanel("Bytes padding", String.valueOf(result.getNumBytesPadding())));
		}

		c.gridy = 1;
		c.insets = new Insets(0, 0, 0, 0);
		panel.add(gridPanel, c);
		return panel;
	}

	private JPanelRound generateCardPanel(String title, String value) {
		JPanelRound cardPanel = new JPanelRound();
		cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
		cardPanel.setBackground(CierzoTheme.primaryColorLight);
		cardPanel.setRound(CierzoTheme.roundedCorners);
		cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel cardTitle = new JLabel(title);
		cardTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		cardPanel.add(cardTitle);

		JLabel cardValue = new JLabel(value);
		cardValue.putClientProperty( "FlatLaf.styleClass", "h2" );
		cardValue.setAlignmentX(Component.CENTER_ALIGNMENT);
		cardPanel.add(cardValue);

		return cardPanel;
	}
}
