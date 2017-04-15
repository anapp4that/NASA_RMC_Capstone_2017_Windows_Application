

/**
 * Created by Incoruptable on 1/26/2017.
 */
public class Translator {

    volatile StringBuffer currentCommands;
    boolean inverseLeftTrigger;
    boolean inverseRightTrigger;

    public Translator() {
        currentCommands = new StringBuffer("0000000000000000000000");
        inverseLeftTrigger = false;
        inverseRightTrigger = false;
    }

    public void translateLeftBumperValue(boolean pressed) {
        inverseLeftTrigger = pressed;
    }

    public void translateRightBumperValue(boolean pressed) {
        inverseRightTrigger = pressed;
    }

    public void translateLeftTriggerValue(double value) {
        int speed = (int) (value * 100);
        String binaryRepresentation = String.format("%8s", Integer.toBinaryString(speed)).replace(' ', '0');
        if (inverseLeftTrigger) {
            binaryRepresentation += "1";
        } else {
            binaryRepresentation += "0";
        }
        currentCommands.replace(Constants.LEFT_SIDE_START, Constants.LEFT_SIDE_END + 1, binaryRepresentation);
    }

    public void translateRightTriggerValue(double value) {
        int speed = (int) (value * 100);
        String binaryRepresentation = String.format("%8s", Integer.toBinaryString(speed)).replace(' ', '0');
        if (inverseRightTrigger) {
            binaryRepresentation += "1";
        } else {
            binaryRepresentation += "0";
        }
        currentCommands.replace(Constants.RIGHT_SIDE_START, Constants.RIGHT_SIDE_END + 1, binaryRepresentation);
    }

    public void translateDrumValue(boolean pressed, boolean rotateForward) {
        String binaryRepresentation;
        if (!pressed) {
            binaryRepresentation = "00";
        } else {
            if (rotateForward) {
                binaryRepresentation = "01";
            } else {
                binaryRepresentation = "10";
            }
        }
        currentCommands.replace(Constants.DRUM_START_INDEX, Constants.DRUM_END_INDEX + 1, binaryRepresentation);
    }

    public void translateArmValue(boolean pressed, boolean down) {
        String binaryRepresentation;
        if (!pressed) {
            binaryRepresentation = "00";
        } else {
            if (down) {
                binaryRepresentation = "01";
            } else {
                binaryRepresentation = "10";
            }
        }
        currentCommands.replace(Constants.ARM_START_INDEX, Constants.ARM_END_INDEX + 1, binaryRepresentation);
    }
}
