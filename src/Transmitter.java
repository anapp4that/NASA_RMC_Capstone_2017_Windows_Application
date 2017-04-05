import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.BitSet;
import java.util.stream.IntStream;

import static javax.swing.UIManager.get;

/**
 * Created by Incoruptable on 2/5/2017.
 */
public class Transmitter extends Thread {
    private Socket compSocket;
    private Listener listener;
    private PrintWriter printWriter;

    public Transmitter() throws IOException {
        compSocket = new Socket(Constants.SERVER_IP_ADDRESS, Constants.SERVER_PORT);
        compSocket.setSoTimeout(10000);
        DataOutputStream dout = new DataOutputStream(compSocket.getOutputStream());
        printWriter = new PrintWriter(dout, true);
        printWriter.println("comp");
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
        String previousSend = "";
        String currentSend;
        while (true) {
            BitSet bitArray = listener.getTranslator().bitArray;
            currentSend = bitSetToString(bitArray);
            if (currentSend != null && !currentSend.equals(previousSend)) {
                if (currentSend.length() == 13) {
                    printWriter.write(currentSend);
                    previousSend = currentSend;
                } else {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    continue;
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private String bitSetToString(BitSet bitSet){
        final StringBuilder buffer = new StringBuilder(13);
        IntStream.range(0, 13).mapToObj(i -> get(i) ? '1' : '0').forEach(buffer::append);
        return buffer.toString();
    }
}
