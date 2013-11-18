package drawings;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.geom.Line2D;
import java.math.BigDecimal;
import javax.swing.JPanel;

public class GnomonDrawing extends JPanel {
	private int width;//store screen width here
	private int height;//store screen height here
	
	//The height, base, and angle of the triangle
	private double h;
	private double base;
	private double angle;
	  
	/**
   	*  This is the constructor of the DrawGnomon
   	* @param angle the angle use to calculate the height of the triangle
   	*/
	public GnomonDrawing(double angle) {
		super(new GridLayout(1, 1));
		setBackground(new java.awt.Color(255, 255, 255));
		//************************************************************Screen Resolution**************************************************************************************************
		this.width = Toolkit.getDefaultToolkit().getScreenSize().width;
		this.height = Toolkit.getDefaultToolkit().getScreenSize().height;
			
	    //*******************************************************Initializing the dimensions of the triangle***********************************************************************
		this.base = this.height * .85 - this.height * .05;
		this.h = Math.tan(Math.toRadians(angle))*(this.base);
		this.h = this.round(this.h, 2, BigDecimal.ROUND_HALF_UP);
		this.angle = angle;
			}
		  	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(Color.red);
		
		//*******************************************************************Drawing the Triangle******************************************************************************************
		g2d.draw(new Line2D.Double(this.width*.05,(this.h + (this.height*.05)),(this.base + (this.width*.05)),(this.h + (this.height*.05)) )); //This is the base of the triangle
		g2d.draw(new Line2D.Double((this.base + (this.width*.05)),(this.h + (this.height*.05)),this.width*.05,this.height*.05 )); //This is the hypotenuse of the triangle
		g2d.draw(new Line2D.Double(this.width*.05,this.height*.05,this.width*.05,(this.h + (this.height*.05)) )); //This is the height of the triangle
		  
		//Prints the latitude angle Label 
		Font f = new Font ("default", Font.BOLD, 20);
		g.setFont(f);
		g.drawString("Latitude = " + this.angle +" degrees", (int) (this.width*.05 + 10), (int) (this.h + ((this.height*.05) - 20)   ));
	}
	
	
	/**
	 * This rounds a number to two decimal places
	 * @param unrounded is the number to be rounded.
	 * @param precision the number of decimals to round to.
	 * @param roundingMode mode of rounding
	 * @return the rounded value
	 */
	public double round(double unrounded, int precision, int roundingMode)
	{
		BigDecimal bd = new BigDecimal(unrounded);
		BigDecimal rounded = bd.setScale(precision, roundingMode);
		return rounded.doubleValue();
	}

}
