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

public class DialDrawing extends JPanel {
		//******************************************************************************Instance Variables*************************************************************************************
		private int width;//store screen width here
		private int height;//store screen height here
		//----------------------------------------------Coordinates for the border----------------------------------------------
		//Left side
		private double left_point_x;
		private double left_point_y1;
		private double left_point_y2;
		//right side
		private double right_point_x;
		private double right_point_y1;
		private double right_point_y2;
		//top side
		private double top_point_x1;
		private double top_point_x2;
		private double top_point_y;
		//bottom side
		private double bottom_point_x1;
		private double bottom_point_x2;
		private double bottom_point_y;
		
		private double origin; //mid of the border
		private double h; //Height of the border
		private double b; //Length of half of the border
		
		//The arrays for holding the angles of the hours and labels
		private static double[] hourLineAngles = new double[13];
		private static int[] lineLabels = new int[13];
		private static double[] lineNum = new double[13];
		//Array of booleans for determining if each hour angle is on the left or right of the dial
		private static boolean[] left_right = new boolean[13];
	   
		/**
		 * The constructor of DrawDial
		 * @param lineLabes to hold the labels for each hour
		 * @param this.hourLineAngles to hold the angles for each hour
		 */
		public DialDrawing(double[] hourLineAngles, int[] lineLabels) {
			super(new GridLayout(1, 1));
			setBackground(Color.white);
			//************************************************************Screen Resolution**************************************************************************************************
			this.width = Toolkit.getDefaultToolkit().getScreenSize().width;
			this.height = Toolkit.getDefaultToolkit().getScreenSize().height;
			//**************************************************Initializing the Coordinates of the border***********************************************************************************
			//left side
			this.left_point_x = this.width*.05;
			this.left_point_y1 = this.height*.05;
			this.left_point_y2 = this.height*.85;
			//right side
			this.right_point_x = this.width*.90;
			this.right_point_y1 = this.height*.05;
			this.right_point_y2 = this.height*.85;
			//top side
			this.top_point_x1 = this.width*.05;
			this.top_point_x2 = this.width*.90;
			this.top_point_y = this.height*.05;
			//bottom side
			this.bottom_point_x1 = this.width*.05;
			this.bottom_point_x2 = this.width*.90;
			this.bottom_point_y = this.height*.85;
			
			//*************************************************************Initializing the length and width of the border****************************************************************
			this.origin = (((this.width*.90 - this.width*.05)/2)+ this.width*.05);
			this.h = this.height * .85 - this.height * .05;
			this.b = this.origin - this.width*.05;
			
			//Initialize arrays
			for (int i = 0; i < left_right.length; i++) {
				left_right[i] = false;
			}
			this.hourLineAngles = hourLineAngles;
			this.lineLabels = lineLabels;

			//Initializing line coordinates
			//The lines are drawn inside and rectangle divided into two rectangles
			//Left square is the left part of the sundial while the right square is the right part of the sundial
			//Each square has two right triangles, and this is use to determine the height which is needed to draw the hour line
			for (int i = 0; i < this.hourLineAngles.length; i++) {
				//Hour line is on the left
				if (this.hourLineAngles[i] <= 0) {
					this.hourLineAngles[i] = 90 + this.hourLineAngles[i];
					if (this.hourLineAngles[i] >= 0) {
						//Since each hour line forms a triangle
						//We need to find the height of the triangle by using the given base and the angle
						//With the formula height = tanA*base
						//Store the height in the array lineNum
						if (this.hourLineAngles[i] <= 45) {
							this.lineNum[i] = Math.tan(Math.toRadians(this.hourLineAngles[i]))*(this.b);
							this.lineNum[i] = (this.h - this.round(this.lineNum[i], 2, BigDecimal.ROUND_HALF_UP)) + this.height*.05;
						}
						//This is the top right triangle of the square
						else if (this.hourLineAngles[i] > 45 && this.hourLineAngles[i] < 90) {
							this.lineNum[i] = Math.tan(Math.toRadians(90 - this.hourLineAngles[i]))*(this.h);
							this.lineNum[i] = (this.origin - (this.round(this.lineNum[i], 2, BigDecimal.ROUND_HALF_UP)));
						}
					}
				}
				//Hour line is on the right
				else if (this.hourLineAngles[i] > 0) {
					//Sets it to true to tell that the hour line is on the right, later use for drawing the line.
					this.left_right[i] = true;
					this.hourLineAngles[i] = 90 - this.hourLineAngles[i];
					if (this.hourLineAngles[i] >= 0) {
						if (this.hourLineAngles[i] <= 45) {
							this.lineNum[i] = Math.tan(Math.toRadians(this.hourLineAngles[i]))*(this.b);
							this.lineNum[i] = (this.h - this.round(this.lineNum[i], 2, BigDecimal.ROUND_HALF_UP)) + this.height*.05;
						}
						else if (this.hourLineAngles[i] > 45 && this.hourLineAngles[i] < 90) {
							this.lineNum[i] = Math.tan(Math.toRadians(90 - this.hourLineAngles[i]))*(this.h);
							this.lineNum[i] = (this.origin + (this.round(this.lineNum[i], 2, BigDecimal.ROUND_HALF_UP)));
						}
					}
				}
				
			}
		}
		
		public void paint(Graphics g) {
			super.paint(g);
			Font f = new Font ("default", Font.BOLD, 30);
			g.setFont(f);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setPaint(Color.BLACK);
			g.drawString("AM", (int) (this.width*.05 + 15) , (int) (this.height*.85 + 50));
			g.drawString("PM", (int) (this.width*.90 - 55) , (int) (this.height*.85 + 50));
			  
			//*************************************************************************Drawing the Border************************************************************************************
			g2d.draw(new Line2D.Double(left_point_x,left_point_y1,left_point_x,left_point_y2)); //Left_Side  
			g2d.draw(new Line2D.Double(right_point_x,right_point_y1,right_point_x,right_point_y2 )); //Right_Side 
			g2d.draw(new Line2D.Double(top_point_x1,top_point_y,top_point_x2,top_point_y));//Top_Side   
			g2d.draw(new Line2D.Double(bottom_point_x1,bottom_point_y,bottom_point_x2,bottom_point_y ));//Bottom_Side
			
			//Drawing the Lines and Labels
			//this.hourLineAngles[0...n] corresponds to lineLabels[0....n]
			//The >= 0 check, is to prevent the line from being drawn below the border
			for(int i = 0; i < this.hourLineAngles.length; i++) {
				//Hour line is on left
				if (!this.left_right[i] && this.hourLineAngles[i] != 90 && this.hourLineAngles[i] >= 0) { 
					if (this.hourLineAngles[i] <= 45) {
						g2d.draw(new Line2D.Double(this.width*.05,this.lineNum[i],this.origin,this.height*.85)); 
						g.drawString(Integer.toString(lineLabels[i]), (int) (this.width*.05 - 20) , (int) (this.lineNum[i]));
					}
					else {
						g2d.draw(new Line2D.Double(this.lineNum[i],this.height*.05,this.origin,this.height*.85)); 
						g.drawString(Integer.toString(lineLabels[i]),(int) this.lineNum[i], (int) (this.height*.05));
					}
				}
				//Hour Line is on right
				else if (this.left_right[i] && this.hourLineAngles[i] != 90 && this.hourLineAngles[i] >= 0) {
					if (this.hourLineAngles[i] <= 45) {
						g2d.draw(new Line2D.Double(this.width*.90,this.lineNum[i],this.origin,this.height*.85)); 
						g.drawString(Integer.toString(lineLabels[i]), (int) (this.width*.90), (int) (this.lineNum[i]));
					}
					else {
						g2d.draw(new Line2D.Double(this.lineNum[i],this.height*.05,this.origin,this.height*.85)); 
						g.drawString(Integer.toString(lineLabels[i]),(int) this.lineNum[i], (int) (this.height*.05));
					}
				}
				else if (this.hourLineAngles[i] == 90 && this.hourLineAngles[i] >= 0) {
					g2d.draw(new Line2D.Double(this.origin,this.height*.05,this.origin,this.height*.85)); 
					g.drawString(Integer.toString(lineLabels[i]), (int) (this.origin), (int) (this.height*.05));
				}
			}
		}
			
			
		 /**
		  * This rounds a number
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