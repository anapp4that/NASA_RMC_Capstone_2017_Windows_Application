import ch.aplu.xboxcontroller.XboxController;
import ch.aplu.xboxcontroller.XboxControllerAdapter;

import javax.swing.*;

// YOU WILL NEED TO INSTALL THE API FROM APLU.CH. THE LINK IS POSTED IN GROUPME. FOLLOW THE INSTALLATION INSTRUCTIONS ON THE WEBSITE
//TRY USING THE TRIGGERS OF AN XBOX CONTROLLER. YOU SHOULD FEEL BOTH VIBRATION AND THE CONSOLE WILL PRINT OUT VALUES.

public class Listener extends Thread {
    private volatile Translator translator;
    private XboxController xc;
    private int leftVibrate = 0;
    private int rightVibrate = 0;

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

            public void rightShoulder(boolean pressed) {
                translator.translateRightBumperValue(pressed);
            }

            //Directional Pad
            //0->North = Forward: both wheels = 01111
            //4->South = Backward: both wheels = 11111
            //2->East = Right: left wheel = 01111 and right wheel = 11111
            //6->West = Left: left wheel = 11111 and right wheel = 01111
            public void dpad(int direction, boolean pressed) {
                //Forward
                translator.translateLeftTriggerValue(1);
                translator.translateRightTriggerValue(1);

                //Backward
                translator.translateLeftBumperValue(true);
                translator.translateRightBumperValue(true);
                translator.translateLeftTriggerValue(1);
                translator.translateRightTriggerValue(1);

                //Right
                translator.translateRightBumperValue(true);
                translator.translateRightTriggerValue(1);
                translator.translateLeftTriggerValue(1);

                //Left
                translator.translateLeftBumperValue(true);
                translator.translateLeftTriggerValue(1);
                translator.translateRightTriggerValue(1);
            }
        });


    }

    public Translator getTranslator() {
        return translator;
    }


}