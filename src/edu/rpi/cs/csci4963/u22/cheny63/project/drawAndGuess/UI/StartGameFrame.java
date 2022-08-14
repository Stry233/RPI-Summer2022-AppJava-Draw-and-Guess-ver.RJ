package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.tools.ImageUtility;


class startGameBackground extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image backgroundImage;
	private Dimension curWindowSize;

	  public startGameBackground(String fileName, Dimension screenSize) throws IOException {
		this.curWindowSize = new Dimension((int)screenSize.getWidth(), 
							 (int)(screenSize.getWidth())/16*9);
		this.setBackground(new Color(32, 130, 147));
		backgroundImage = ImageIO.read(new File(fileName));
	  }

	  public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    // Draw the background image.
	    g.drawImage(ImageUtility.resizeIcon(backgroundImage, curWindowSize), 0, 0, this);
	  }
}

public class StartGameFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	// param for setting maximum size
	private GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
	private GraphicsDevice device = graphics.getDefaultScreenDevice();
	private Action actionExit;
	private Action actionHost;
	private Action actionClient;
	
	
	private void initAction() {
		this.actionExit = new AbstractAction("Exit") {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				// controller.onClosing();
                System.exit(0);
			}
		};
		this.actionHost = new AbstractAction("Host") {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				// StartGameFrame.this.dispose();
	    	}
		};
    	this.actionClient = new AbstractAction("Client") {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				// StartGameFrame.this.dispose();
	    	}
		};
			
	}
	/**
	 * main GUI generation function
	 * @throws IOException 
	 * @throws FontFormatException 
	 */
	private void generateGUI() throws FontFormatException, IOException {
		// get current window size and basic background
		this.getContentPane().add(new startGameBackground("./res/gui/startGameScreen/bg.png", 
				                  Toolkit.getDefaultToolkit().getScreenSize()));
		
		initAction();
		// start arrange
		this.setBackground(new Color(32, 130, 147));
		JPanel operations = new JPanel();
		operations.setLayout(new GridBagLayout());
	    GridBagConstraints gridConstant = new GridBagConstraints();
	    gridConstant.gridy = 0;
	    gridConstant.weighty = 1;
	    operations.add(new JLabel(new ImageIcon("./res/gui/startGameScreen/logo.png")), gridConstant);
	    gridConstant.gridy = 1;
	    gridConstant.weighty = 0.2;
		operations.add(new PixelatedButton("CREATE a ROOM", this.actionHost), gridConstant);
		gridConstant.gridy = 2;
		operations.add(new PixelatedButton("ENTER a ROOM", this.actionClient), gridConstant);
		gridConstant.gridy = 3;
		operations.add(new PixelatedButton("EXIT", this.actionExit), gridConstant);
		gridConstant.gridy = 4;
		operations.add(new JSeparator(JSeparator.VERTICAL), gridConstant);
		operations.setBackground(new Color(32, 130, 147));
		operations.setOpaque(true);
		
		this.add(operations);
        //Display the window.       
		device.setFullScreenWindow(this);
		this.setLocationRelativeTo(null); // set window centre
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	/**
	 * constructor: will generate a init setting panel
	 *  for users to select modes
	 */
	public StartGameFrame() {
		super("Start a game - Draw and Guess");
		try {
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			generateGUI();
		} catch (Exception e) { // case: cannot get resource
			JOptionPane.showMessageDialog(this, 
			"Fail to load game resource: please check resource", 
			"Oops...", JOptionPane.ERROR_MESSAGE);
		}
	}
}