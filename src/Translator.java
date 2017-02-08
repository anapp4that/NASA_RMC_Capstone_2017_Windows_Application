import java.util.BitSet;

/**
 * Created by Incoruptable on 1/26/2017.
 */
public class Translator {

    public volatile BitSet bitArray;
    private boolean inverseLeftTrigger;
    private boolean inverseRightTrigger;

    public Translator() {
        inverseLeftTrigger = false;
        inverseRightTrigger = false;
        bitArray = new BitSet(11);
    }

    public void translateLeftBumperValue(boolean pressed) {
        inverseLeftTrigger = pressed;
        bitArray.set(Constants.LEFT_SIDE_DIRECTION_INDEX, pressed);
    }

    public void translateLeftTriggerValue(double value) {
        bitArray.set(Constants.LEFT_SIDE_DIRECTION_INDEX, inverseLeftTrigger);
        bitArray.set(Constants.LEFT_SIDE_SPEED_START_INDEX, Constants.LEFT_SIDE_SPEED_END_INDEX + 1, false);
        System.out.print(value);
        if (value == 1) {
            bitArray.set(Constants.LEFT_SIDE_SPEED_START_INDEX, true);
            bitArray.set(Constants.LEFT_SIDE_SPEED_END_INDEX, true);
        } else if (value >= .8) {
            bitArray.set(Constants.LEFT_SIDE_SPEED_START_INDEX, true);
        } else if (value >= .6) {
            bitArray.set(2, Constants.LEFT_SIDE_SPEED_END_INDEX + 1, true);
        } else if (value >= .4) {
            bitArray.set(2, true);
        } else if (value >= .2) {
            bitArray.set(Constants.LEFT_SIDE_SPEED_END_INDEX, true);
        }

        System.out.print(bitArray.toString());
        System.out.println();

    }

    public void translateRightTriggerValue(double value) {
        bitArray.set(Constants.RIGHT_SIDE_DIRECTION_INDEX, inverseRightTrigger);
        bitArray.set(Constants.RIGHT_SIDE_SPEED_START_INDEX, Constants.RIGHT_SIDE_SPEED_END_INDEX + 1, false);
        System.out.print(value);
        if (value == 1) {
            bitArray.set(Constants.RIGHT_SIDE_SPEED_START_INDEX, true);
            bitArray.set(Constants.RIGHT_SIDE_SPEED_END_INDEX, true);
        } else if (value >= .8) {
            bitArray.set(Constants.RIGHT_SIDE_SPEED_START_INDEX, true);
        } else if (value >= .6) {
            bitArray.set(7, Constants.RIGHT_SIDE_SPEED_END_INDEX + 1, true);
        } else if (value >= .4) {
            bitArray.set(7, true);
        } else if (value >= .2) {
            bitArray.set(Constants.RIGHT_SIDE_SPEED_END_INDEX, true);
        }

        System.out.print(bitArray.toString());
        System.out.println();
    }

    public void translateRightBumperValue(boolean pressed) {
        inverseRightTrigger = pressed;
        bitArray.set(Constants.RIGHT_SIDE_DIRECTION_INDEX, pressed);
    }
}
