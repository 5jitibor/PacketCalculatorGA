package PacketCalculatorGUI.CierzoTheme;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.InsetsUIResource;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CierzoTheme extends FlatLightLaf {
	public static final String NAME = "CierzoTheme";

	// Colors
	public static final ColorUIResource primaryColor = new ColorUIResource(0x3955d1);
	public static final ColorUIResource primaryColorLight = new ColorUIResource(0xeceef8);
	public static final ColorUIResource secondaryColor = new ColorUIResource(0xd94270);
	public static final ColorUIResource white = new ColorUIResource(0xffffff);
	public static final ColorUIResource black = new ColorUIResource(0x000000);

	public static final int roundedCorners = 20;

	public CierzoTheme() {
		// Fonts
		try {
			GraphicsEnvironment ge =
					GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("fonts/Recoleta-Regular.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("fonts/Recoleta-SemiBold.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("fonts/Poppins-Regular.ttf")));
		} catch (IOException |FontFormatException e) {
			//Handle exception
		}

		// Background
		UIManager.put( "Panel.background", new ColorUIResource(0xfffefe));

		UIManager.put( "Button.arc", roundedCorners );
		UIManager.put( "Component.arc", roundedCorners );
		UIManager.put( "ProgressBar.arc", roundedCorners );
		UIManager.put( "TextComponent.arc", roundedCorners );

		UIManager.put( "defaultFont", new FontUIResource("Poppins", Font.PLAIN, 13));
		UIManager.put( "h00.font", new FontUIResource("Recoleta-SemiBold", Font.PLAIN, 39));
		UIManager.put( "h0.font", new FontUIResource("Recoleta", Font.PLAIN, 33));
		UIManager.put( "h1.font", new FontUIResource( "Recoleta", Font.PLAIN, 27));
		UIManager.put( "h2.font", new FontUIResource("Recoleta", Font.PLAIN, 21));
		UIManager.put( "Large.font", new FontUIResource("Recoleta", Font.PLAIN, 20));

		UIManager.put( "Button.foreground", white );
		UIManager.put( "Button.background", secondaryColor );
		UIManager.put( "Button.focusedBackground", secondaryColor.brighter() );
		UIManager.put( "Button.margin", new InsetsUIResource(10, 15, 10, 15));
		UIManager.put( "Button.borderWidth", 0 );
		UIManager.put( "Button.default.borderWidth", 1 );
		UIManager.put( "Button.disabledBackground", white.darker() );

		UIManager.put( "ToggleButton.background", primaryColorLight );
		UIManager.put( "ToggleButton.selectedBackground", primaryColor );
		UIManager.put( "ToggleButton.selectedForeground", white );
		UIManager.put( "ToggleButton.pressedForeground", black );
		UIManager.put( "ToggleButton.margin", new InsetsUIResource(15, 15, 15, 15));

		UIManager.put( "TextField.margin", new InsetsUIResource(10, 15, 10, 15));
		UIManager.put( "ComboBox.padding", new InsetsUIResource(10, 15, 10, 15));
	}

	public static boolean setup() {
		return setup( new CierzoTheme() );
	}

	public static void installLafInfo() {
		installLafInfo( NAME, CierzoTheme.class );
	}

	@Override
	public String getName() {
		return NAME;
	}
}
