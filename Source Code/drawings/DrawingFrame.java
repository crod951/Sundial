package drawings;

import javax.imageio.ImageIO;
import javax.swing.*;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class DrawingFrame extends JFrame implements ActionListener {
	
	//Instance Variables
	//Tabpane, menu items, gnomon, dial, width, height
	private JTabbedPane tab;
	private JMenuItem print = new JMenuItem("Print");
	private JMenuItem save = new JMenuItem("Save");	
	private JMenuItem exit = new JMenuItem("Exit");
	private GnomonDrawing gnomon;
	private DialDrawing dial;
	private int width;
	private int height;

	public DrawingFrame(GnomonDrawing gnomon, DialDrawing dial) {
		 	
			//Initiliazing variables
			this.gnomon = gnomon;
			this.dial = dial;
			
			//Getting the width and height of screen
			this.width = Toolkit.getDefaultToolkit().getScreenSize().width;
			this.height = Toolkit.getDefaultToolkit().getScreenSize().height;
			
			//Creating tabpane, menu bar, and file menu
			tab = new JTabbedPane();
			JMenuBar menubar = new JMenuBar();
			JMenu menu1 = new JMenu ("File");
			
			
			//Adding Panels in tab
			tab.addTab("Gnomon", this.gnomon);
			tab.addTab("Sundial", this.dial);
		    
			
			
			//add the menu functions to the listener
			print.addActionListener(this);
			save.addActionListener(this);
			exit.addActionListener(this);
			
			//Add the menu items in the menu
			menu1.add(print);
			menu1.add(save);
			menu1.add(exit);
			//Add the menu in the menu bar
			menubar.add(menu1);
			
			//Add the menubar in the frame
			this.setJMenuBar(menubar);
			//Add the tabs in the frame
			this.setContentPane(tab);
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		    this.setSize(this.width, this.height);
		    this.setVisible(true);
	}
	
	/**
	 * Events
	 */
	public void actionPerformed(ActionEvent E) {
		//Prints the currently selected panels
		if(E.getSource() == print) {
			//Get the currently selected tab panel and send it to bufferedImage
			BufferedImage image = getScreenShot(tab.getSelectedComponent());
			new SundialPrinter(image);
			

		}
		
		//Saves the two Panels as PNG in the project directory
		if(E.getSource() == save) {
		   //To be Implemented
			try {
				getSaveSnapShot(this.gnomon, "gnomon.png");
				getSaveSnapShot(this.dial, "dial.png");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//Closes the program
		if(E.getSource() == exit) {
			System.exit(0);
		}
	}
	
	/**
	 * This is a method for turning a component into image
	 * @param component to be created as image
	 * @return the image
	 */
    public static BufferedImage getScreenShot(Component component) {

        BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
        // paints into image's Graphics
        component.paint(image.getGraphics());
        return image;
    }
    
    /**
     * This is a method for saving a component as images in png format
     * @param component to be printed
     * @param fileName of the image
     * @throws Exception if something goes wrong
     */
    public static void getSaveSnapShot(Component component, String fileName) throws Exception {
        BufferedImage img = getScreenShot(component);
        // write the captured image as a png
        ImageIO.write(img, "png", new File(fileName));
    }

}
