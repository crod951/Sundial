package drawings;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class DrawingFrame extends JFrame implements ActionListener {
	
	private JTabbedPane tab;
	private JMenuItem open = new JMenuItem("Open");
	private JMenuItem print = new JMenuItem("Print");
	private JMenuItem save = new JMenuItem("Save");	
	private JMenuItem cancel = new JMenuItem("Cancel");

	public DrawingFrame(GnomonDrawing gnomon, DialDrawing dial) {
			
			//JFrame F = new JFrame("The Sundial");
			tab = new JTabbedPane();
			JMenuBar menubar = new JMenuBar();
			JMenu menu1 = new JMenu ("File");
			
			//add the menu functions to the listener
			open.addActionListener(this);
			print.addActionListener(this);
			save.addActionListener(this);
			cancel.addActionListener(this);
			
			//Add the menu items in the menu
			menu1.add(open);
			menu1.add(print);
			menu1.add(save);
			menu1.add(cancel);
			
			//Add the menu in the menu bar
			menubar.add(menu1);
			
			tab.add("Gnomon", gnomon);
		    tab.add("Sundial", dial);
		    this.add(tab);
			
			this.setJMenuBar(menubar);
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		    this.setSize(500, 750);
		    this.setVisible(true);
	}
		
	public void actionPerformed(ActionEvent E) {
		
		if(E.getSource() == open) {
			JFileChooser F = new JFileChooser(".");
			F.showOpenDialog(null);
		}
		if(E.getSource() == print) {
			//To be Implemented
		}
		if(E.getSource() == save) {
		   //To be Implemented
		}
		if(E.getSource() == cancel) {
			System.exit(0);
		}
	}

}
