import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.BitSet;

/**
 * Created by Incoruptable on 2/5/2017.
 */
public class Transmitter extends Thread {
    private Socket compSocket;
    private Listener listener;
    private DataOutputStream dout;

    public Transmitter() throws IOException {
        try {
            compSocket = new Socket(Constants.SERVER_IP_ADDRESS, Constants.SERVER_PORT_2);
        } catch (ConnectException ex) {
            compSocket = new Socket(Constants.SERVER_IP_ADDRESS, Constants.SERVER_PORT_1);
        }
        compSocket.setSoTimeout(10000);
        dout = new DataOutputStream(compSocket.getOutputStream());
        dout.writeUTF("comp");
        listener = new Listener();
        listener.start();
    }

    public static void main(String[] args) {
        try {
            Thread transmitter = new Transmitter();
            transmitter.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            String previousSend = "";
            String currentSend;
            while (true) {
                currentSend = listener.getTranslator().currentCommands.toString();
                if (currentSend != null && !currentSend.equals(previousSend)) {
                    System.out.print(currentSend + "\n");
                    if (currentSend.length() == 22) {
                        dout.writeUTF(currentSend);
                        previousSend = currentSend;
                        dout.flush();
                    }
                }
                Thread.sleep(200);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private String bitSetToString(BitSet bitSet, int bitSetLength) {
        String bitSetInterpretation = bitSet.toString();
        String result = "";
        for (int i = bitSetLength - 1; i >= 0; i--) {
            if (bitSetInterpretation.contains(Integer.toString(i))) {
                result += "1";
            } else {
                result += "0";
            }
        }
        return result;
    }
}
