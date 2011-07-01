/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HyrevognAdministrationSystem;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.*;

/**
 * @author serup
 */
public class HAS extends MIDlet {

    public void startApp() {
        Display.getDisplay(this).setCurrent(new HASCanvas(this));
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
}
