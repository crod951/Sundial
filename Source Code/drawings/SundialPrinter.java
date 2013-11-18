package drawings;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/**
 * 
 * This class takes a printerjob and bufferedImage and scales it to fit on paper
 *
 */
public class SundialPrinter implements Printable {

	private double          x, y;
	
	private int             orientation;

	private BufferedImage   image;

	public SundialPrinter(PrinterJob printJob, BufferedImage image) {
		PageFormat pageFormat = printJob.defaultPage();
		this.x = pageFormat.getImageableX() + 15; //The x coordinate
		this.y = pageFormat.getImageableY() + 5; //The y coordinate
		this.orientation = pageFormat.getOrientation();
		this.image = image;
		

    }

   @Override
   public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
	   if (pageIndex == 0) {
		   int pWidth = 0;
		   int pHeight = 0;
		   if (orientation == PageFormat.PORTRAIT) {
			   pWidth = (int) 675;
               pHeight = (int) 475;
           } else {
        	   pHeight = (int) 600;
               pWidth = (int) 375;
           }
		   g.drawImage(image, (int) x, (int) y, pWidth, pHeight, null);
		   
		   return PAGE_EXISTS;
	   } 
	   else {
		   return NO_SUCH_PAGE;
	   }
   }

}

