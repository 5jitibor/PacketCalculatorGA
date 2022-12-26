package PacketCalculatorGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;

import Commons.Bytes;
import PacketCalculator.Calculator;
import PacketCalculator.TypeProtocol;

public class GUI {
	
	JFrame window;

	private JLabel appTitle;
	private JLabel descriptionText;

	private JTextField numBytesText;
	private JComboBox<String> sizeComboBox;
	private JToggleButton aal5oatmToggleButton;
	private JToggleButton aal34oatmToggleButton;
	private JToggleButton ethernetToggleButton;
	private JToggleButton ethernet1QToggleButton;
	private JButton buttonResult;
	
	JToggleButton yesPadding = new JToggleButton("Yes");
	JToggleButton noPadding = new JToggleButton("No");
	
	
	public GUI() {
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
		setLayout();
		listeners();
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setVisible(true);
	}
	
	public void initializateComponents() {
   		//To create the Frame
		window = new JFrame("Packet Calculator");
		appTitle = new JLabel("Packet Calculator");
		appTitle.putClientProperty( "FlatLaf.styleClass", "h00" );
		descriptionText = new JLabel("<html>Introduce the following data to calculate the number of bytes it will be sent</html>");
		descriptionText.setPreferredSize(new Dimension(400, 50));
		descriptionText.putClientProperty( "FlatLaf.styleClass", "large" );
		numBytesText = new JTextField(10);
		numBytesText.setHorizontalAlignment(JTextField.RIGHT);
		numBytesText.setFont(new Font("Poppins", Font.PLAIN, 15));
		sizeComboBox = new JComboBox<String>(Bytes.size);
		sizeComboBox.setFont(new Font("Poppins", Font.PLAIN, 15));
		buttonResult = new JButton("Get results");
		aal5oatmToggleButton = new JToggleButton("AAL5oATM");
		aal34oatmToggleButton = new JToggleButton("AAL3/4oATM");
		ethernetToggleButton = new JToggleButton("Ethernet");
		ethernet1QToggleButton = new JToggleButton("Ethernet.1Q");
		yesPadding = new JToggleButton("Yes");
		yesPadding.setSelected(true);
		noPadding = new JToggleButton("No");
	 }
	
	public void  setLayout() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.gridy = 0;
		panel.add(generateTitleAndDescription(), c);

		c.gridy = 1;
		c.insets = new Insets(10, 0, 0, 0);
		panel.add(generateNumberOfBytesTextField(), c);

		c.gridy = 2;
		panel.add(generateTypeOptions(), c);

		c.gridy = 3;
		panel.add(generatePaddingSelection(), c);

		c.gridy = 4;
		panel.add(generateButtonRow(), c);
		
		window.setContentPane(panel);
		window.pack();
	}

	public JPanel generateTitleAndDescription() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(40, 0, 0, 0);
		panel.add(appTitle, c);

		c.gridy = 1;
		c.insets = new Insets(10, 0, 0, 0);
		panel.add(descriptionText, c);

		c.gridy = 2;
		c.insets = new Insets(30, 0, 20, 0);
		panel.add(new JSeparator(), c);
		return panel;
	}

	public JPanel generateNumberOfBytesTextField() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(0, 0, 10, 0);
		JLabel numBytesTag = new JLabel("Number of bytes");
		numBytesTag.putClientProperty( "FlatLaf.styleClass", "h2" );
		panel.add(numBytesTag, c);

		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 0.9;
		c.insets = new Insets(0, 0, 0, 0);
		panel.add(numBytesText, c);

		c.gridx = 1;
		c.weightx = 0.1;
		c.insets = new Insets(0, 10, 0, 0);
		panel.add(sizeComboBox, c);
		return panel;
	       
	}
	
	public JPanel generateButtonRow() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 1));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		panel.add(buttonResult);
		return panel;
	       
	}
	
	public JPanel generateTypeOptions() {
		JPanel panel = new JPanel();

		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(20, 0, 10, 0);
		JLabel protocolsTag = new JLabel("Protocols");
		protocolsTag.putClientProperty( "FlatLaf.styleClass", "h2" );
		panel.add(protocolsTag, c);


		JPanel panelProtocols = new JPanel();
		GridLayout layout = new GridLayout(2, 2);
		layout.setHgap(10);
		layout.setVgap(10);
		panelProtocols.setLayout(layout);

		panelProtocols.add(aal5oatmToggleButton);
		panelProtocols.add(aal34oatmToggleButton);
		panelProtocols.add(ethernetToggleButton);
		panelProtocols.add(ethernet1QToggleButton);

		c.gridy = 1;
		c.insets = new Insets(0, 0, 0, 0);
		panel.add(panelProtocols, c);
		return panel;
	}
	
	public JPanel generatePaddingSelection() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(20, 0, 10, 0);
		JLabel paddingTag = new JLabel("Include padding");
		paddingTag.putClientProperty( "FlatLaf.styleClass", "h2" );
		panel.add(paddingTag, c);

		ButtonGroup group = new ButtonGroup();
	    group.add(yesPadding);
	    group.add(noPadding);

		c.gridy = 1;
		c.weightx = 1;
		c.gridwidth = 1;
		c.insets = new Insets(0, 0, 10, 5);
		panel.add(noPadding, c);

		c.gridx = 1;
		c.weightx = 1;
		c.insets = new Insets(0, 5, 10, 0);
		panel.add(yesPadding, c);
	    return panel;
	}
	
	
	public void listeners() {
		buttonResult.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isPadding = yesPadding.isSelected();
				ArrayList<TypeProtocol> listProtocols = new ArrayList<>();
				if(aal5oatmToggleButton.isSelected()) {
					listProtocols.add(TypeProtocol.AAL5oATM);
				}
				if(aal34oatmToggleButton.isSelected()) {
					listProtocols.add(TypeProtocol.AAL34oATM);
				}
				if(ethernetToggleButton.isSelected()) {
					listProtocols.add(TypeProtocol.Ethernet);
				}
				if(ethernet1QToggleButton.isSelected()) {
					listProtocols.add(TypeProtocol.Ethernet1Q);
				}
				BigInteger bytes = Bytes.getnumBytes(numBytesText.getText()+sizeComboBox.getSelectedItem());
				
				if (!bytes.equals(BigInteger.valueOf(-1)) && listProtocols.size() > 0)
		        {
					GUIResult resultGUI = new GUIResult(Calculator.calculateResults(listProtocols, bytes, isPadding), isPadding);
		        }
		        else {
		            if (bytes.equals(BigInteger.valueOf(-1))){
		            	JOptionPane.showMessageDialog(window, 	"ERROR: Bytes number not valid", "ERROR",JOptionPane.ERROR_MESSAGE);
		            }
		            if (listProtocols.size() == 0) {
		            	JOptionPane.showMessageDialog(window, "ERROR: You must select at least one protocol", "ERROR", JOptionPane.ERROR_MESSAGE);
		            }

		        }
				
				
	            
	            
			}
		});
		
	}
	

}
