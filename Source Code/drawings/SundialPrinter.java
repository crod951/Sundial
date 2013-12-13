package drawings;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/**
 * 
 * This class takes bufferedImage and scales it to fit on paper
 * @original Author MadProgrammer-stacksoverflow
 * @Edited by team_bobal
 */
public class SundialPrinter {
	//Instance Variable
	private static BufferedImage image;
	
	//Constructor
	public SundialPrinter(BufferedImage image) {
		this.image = image;
		
		System.out.println(image.getWidth() + "x" + image.getHeight());

		PrinterJob pj = PrinterJob.getPrinterJob();
		if (pj.printDialog()) {
		    PageFormat pf = pj.defaultPage();
		    Paper paper = pf.getPaper();
		    //8.5x11 paper size
		    double width = fromCMToPPI(27.94);
		    double height = fromCMToPPI(20.32);
		    paper.setSize(width, height);
		    paper.setImageableArea(
		                    fromCMToPPI(0.1),
		                    fromCMToPPI(0.1),
		                    width - fromCMToPPI(0.1),
		                    height - fromCMToPPI(0.1));
		    pf.setOrientation(PageFormat.LANDSCAPE);
		    pf.setPaper(paper);
		    PageFormat validatePage = pj.validatePage(pf);
		    System.out.println("Valid- " + dump(validatePage));
		    pj.setPrintable(new MyPrintable(), validatePage);
		    try {
		        pj.print();
		    } catch (PrinterException ex) {
		        ex.printStackTrace();
		    }
		}
	}
	
	/**
	 * Converts Pixels to cm
	 * @param dpi
	 * @return
	 */
	private static double fromPPItoCM(double dpi) {
	    return dpi / 72 / 0.393700787;
	}
	
	/**
	 * Convers cm to pixels
	 * @param cm
	 * @return
	 */
	private static double fromCMToPPI(double cm) {
	    return toPPI(cm * 0.393700787);
	}
	
	private static double toPPI(double inch) {
	    return inch * 72d;
	}
	
	private static String dump(Paper paper) {
	    StringBuilder sb = new StringBuilder(64);
	    sb.append(paper.getWidth()).append("x").append(paper.getHeight())
	                    .append("/").append(paper.getImageableX()).append("x").
	                    append(paper.getImageableY()).append(" - ").append(paper
	                    .getImageableWidth()).append("x").append(paper.getImageableHeight());
	    return sb.toString();
	}
	
	private static String dump(PageFormat pf) {
	    Paper paper = pf.getPaper();
	    return dump(paper);
	}
	
	private class MyPrintable implements Printable {
	    @Override
	    public int print(Graphics graphics, PageFormat pageFormat,
	                                     int pageIndex) throws PrinterException {
	        System.out.println(pageIndex);
	        int result = NO_SUCH_PAGE;
	        if (pageIndex < 1) {
	            Graphics2D g2d = (Graphics2D) graphics;
	            System.out.println("[Print] " + dump(pageFormat));
	            double width = pageFormat.getImageableWidth();
	            double height = pageFormat.getImageableHeight();
	
	            System.out.println("Print Size = " + fromPPItoCM(width) + "x" + fromPPItoCM(height));
	            g2d.translate((int) pageFormat.getImageableX()-20,
	                            (int) pageFormat.getImageableY()+75);
	            Image scaled = null;
	            if (width > height) {
	                scaled = image.getScaledInstance((int)Math.round(width), -1, Image.SCALE_SMOOTH);
	            } else {
	                scaled = image.getScaledInstance(-1, (int)Math.round(height), Image.SCALE_SMOOTH);
	            }
	            g2d.drawImage(scaled, 0, 0, null);
	            result = PAGE_EXISTS;
	        }
	        return result;
	    }
	}

}
