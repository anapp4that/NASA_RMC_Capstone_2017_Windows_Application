import ch.aplu.xboxcontroller.XboxController;
import ch.aplu.xboxcontroller.XboxControllerAdapter;

import javax.swing.*;

// YOU WILL NEED TO INSTALL THE API FROM APLU.CH. THE LINK IS POSTED IN GROUPME. FOLLOW THE INSTALLATION INSTRUCTIONS ON THE WEBSITE
//TRY USING THE TRIGGERS OF AN XBOX CONTROLLER. YOU SHOULD FEEL BOTH VIBRATION AND THE CONSOLE WILL PRINT OUT VALUES.

public class Listener extends Thread {
    private volatile Translator translator;
    private XboxController xc;

    public Listener() {
        xc = new XboxController();
        translator = new Translator();
        if (!xc.isConnected()) {
            JOptionPane.showMessageDialog(null,
                    "Xbox controller not connected.",
                    "Fatal error",
                    JOptionPane.ERROR_MESSAGE);
            xc.release();
            return;
        }

        xc.addXboxControllerListener(new XboxControllerAdapter() {
            public void leftTrigger(double value) {
                translator.translateLeftTriggerValue(value);
            }

            public void leftShoulder(boolean pressed) {
                translator.translateLeftBumperValue(pressed);
            }

            public void rightTrigger(double value) {
                translator.translateRightTriggerValue(value);
            }

            public void buttonA(boolean pressed) {
                translator.translateDrumValue(pressed, true);
            }

            public void buttonB(boolean pressed) {
                translator.translateDrumValue(pressed, false);
            }

            public void buttonX(boolean pressed) {
                translator.translateArmValue(pressed, true);
            }

            public void buttonY(boolean pressed) {
                translator.translateArmValue(pressed, false);
            }

            public void rightShoulder(boolean pressed) {
                translator.translateRightBumperValue(pressed);
            }

            public void dpad(int direction, boolean pressed) {

                if (direction == 2) {
                    translator.translateRightBumperValue(pressed);
                } else if (direction == 4) {
                    translator.translateRightBumperValue(pressed);
                    translator.translateLeftBumperValue(pressed);
                } else if (direction == 6) {
                    translator.translateLeftBumperValue(pressed);
                }
                if (pressed) {
                    translator.translateLeftTriggerValue(1.0);
                    translator.translateRightTriggerValue(1.0);
                } else {
                    translator.translateLeftTriggerValue(0.0);
                    translator.translateRightTriggerValue(0.0);
                }
            }
        });


    }

    public Translator getTranslator() {
        return translator;
    }


}