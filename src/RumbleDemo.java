import ch.aplu.xboxcontroller.XboxController;
import ch.aplu.xboxcontroller.XboxControllerAdapter;

import javax.swing.*;

// YOU WILL NEED TO INSTALL THE API FROM APLU.CH. THE LINK IS POSTED IN GROUPME. FOLLOW THE INSTALLATION INSTRUCTIONS ON THE WEBSITE
//TRY USING THE TRIGGERS OF AN XBOX CONTROLLER. YOU SHOULD FEEL BOTH VIBRATION AND THE CONSOLE WILL PRINT OUT VALUES.

public class RumbleDemo {
    private XboxController xc;
    private int leftVibrate = 0;
    private int rightVibrate = 0;

    public RumbleDemo() {
        xc = new XboxController();

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
                printValues(value);
                leftVibrate = (int) (65535 * value * value);
                xc.vibrate(leftVibrate, rightVibrate);
            }

            public void rightTrigger(double value) {
                printValues(value);
                rightVibrate = (int) (65535 * value * value);
                xc.vibrate(leftVibrate, rightVibrate);
            }
        });

        JOptionPane.showMessageDialog(null,
                "Xbox controller connected.\n" +
                        "Press left or right trigger, Ok to quit.",
                "RumbleDemo V1.0 (www.aplu.ch)",
                JOptionPane.PLAIN_MESSAGE);

        xc.release();
        System.exit(0);
    }

    public static void main(String[] args) {
        new RumbleDemo();
    }

    public void printValues(double value) {
        System.out.print("value = " + value + "\n");
    }
}