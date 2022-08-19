package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.font.GlyphVector;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.awt.geom.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.tools.StringUtil;

public class VerticalTimerPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Font goreRegular;
	
	// Timer component
    public boolean end;
    private String timerInfo;
    
    public VerticalTimerPanel() throws FontFormatException, IOException {
		goreRegular = Font.createFont(Font.TRUETYPE_FONT, new File("./res/gui/font/Gore Regular.otf"));
		this.setBackground(new Color(32, 130, 147));
        this.timerInfo = "HOLD";
    }

    @Override
    public Dimension getPreferredSize() {
    	return new Dimension(220, 70);
    }
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        goreRegular  = goreRegular.deriveFont(Font.PLAIN, 250);
        
        // Define rendering hint, font name, font style and font size
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(goreRegular);
        g2.setColor(Color.WHITE);
        

        // ACTIVATE ANTIALIASING AND FRACTIONAL METRICS
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        // CREATE GLYPHVECTOR FROM TEXT, CREATE PRELIMINARY SHAPE FOR COORDINATE CALCULATION, CALC COORDINATES
        final GlyphVector gv = this.goreRegular.createGlyphVector(g2.getFontRenderContext(), this.timerInfo);
        final Rectangle2D stringBoundsForPosition = gv.getOutline().getBounds2D();
        final double xForShapeCreation = (stringBoundsForPosition.getWidth()) / 2d;
        final double yForShapeCreation = (stringBoundsForPosition.getHeight()) / 2d;
        
        
        // Rotate 90 degree to make a vertical text
        g2.rotate(Math.toRadians(-90));
        g2.drawString(timerInfo, (int)(-xForShapeCreation*2 - 20), 
        						(int)(yForShapeCreation*2 + 20));
        this.repaint();
        this.revalidate();
    }
    
    /**
     *  Helper function update time
     * @param timeInterval the selected 
     */
    public void updateTime(int timeInterval) {
    	int min = timeInterval/60;
        int sec = timeInterval-(min*60);
        timerInfo = String.format("%s:%s",String.valueOf(min), String.valueOf(sec));
        this.repaint();
        this.revalidate();
    }
}