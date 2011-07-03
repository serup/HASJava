/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HyrevognAdministrationSystem;

import java.util.Enumeration;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

/**
 * @author serup
 * 
 * This is a template version of Mikhail's example, it will be used to build HAS 
 * in steps - first main UI then dialogs
 */
public class HASCanvas extends Canvas implements CommandListener {
    /***************************************************************************
    ** Configuration
    ***************************************************************************/
    
    private static final boolean USE_DRIFT = false;
    private static final boolean USE_ERASE = false;
    

    /***************************************************************************
    ** Constructor and misc
    ***************************************************************************/
    
    private MIDlet parent;  // To call notifyDestroyed()
    private Drifter drifter;

    /**
     * constructor
     */
    public HASCanvas(MIDlet parent) {
        this.parent = parent;
        if (USE_DRIFT) {
            drifter = new Drifter(circles, this);
            new Timer().schedule(drifter, 200, 200);
        }

        try {
            // Set up this canvas to listen to command events
            setCommandListener(this);
            // Add the Exit command
            addCommand(new Command("Exit", Command.EXIT, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when action should be handled
     */
    public void commandAction(Command command, Displayable displayable) {
        if (command.getCommandType() == Command.EXIT)
            parent.notifyDestroyed();
    }

    
    /***************************************************************************
    ** Circle class
    ***************************************************************************/
    
    private class Circle {
        int x;
        int y;
        int r;
        Circle(int x, int y, int r) {
            this.x = x;
            this.y = y;
            this.r = r;
        }
    };
    
    
    /***************************************************************************
    ** Drawing routines
    ***************************************************************************/
    
    private Vector circles = new Vector();
    
    /**
     * paint
     */
    public void paint(Graphics g) {
        if (USE_ERASE) {
            g.setColor(0xFFFFFF);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(0);
        }
        
        g.drawString("Press a key", 0, 0, Graphics.TOP | Graphics.LEFT);
        for (Enumeration e = circles.elements();
             e.hasMoreElements();
             ) {
            Circle c = (Circle)e.nextElement();
            g.drawArc(c.x, c.y, c.r, c.r, 0, 360);
        }
        
        g.drawRect(0, 0, 100, 100);
    }

    /**
     * Called when a key is pressed.
     */
    protected void keyPressed(int keyCode) {
        Random rand = new Random();
        Circle c = new Circle(rand.nextInt(getWidth()), rand.nextInt(getHeight()), rand.nextInt(10)+ 10);
        circles.addElement(c);
        repaint();
    }

    /**
     * Called when the pointer is pressed.
     */
    protected void pointerPressed(int x, int y) {
        Random rand = new Random();
        Circle c = new Circle(x, y, rand.nextInt(10)+ 10);
        circles.addElement(c);
        repaint();
    }
    
    
    /***************************************************************************
    ** Drift
    ***************************************************************************/
    
    class Drifter extends TimerTask {
        private Vector circles;
        private Canvas parent;
        
        Drifter(Vector v, Canvas c) {
            circles = v;
            parent = c;
        }
    
        
        public void run() {
            for (Enumeration e = circles.elements();
                 e.hasMoreElements();
                 ) {
                Circle c = (Circle)e.nextElement();
                c.x += 2;
                if (c.x > getWidth()) {
                    circles.removeElement(c);
                }
            }
            
            parent.repaint();
        }
    };
}
