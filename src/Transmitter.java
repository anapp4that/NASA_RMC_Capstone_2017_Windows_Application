import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by Incoruptable on 2/5/2017.
 */
public class Transmitter extends Thread {
    private Socket compSocket;
    private Listener listener;

    public Transmitter(int port) throws IOException {
        compSocket = new Socket(Constants.SERVER_IP_ADDRESS, Constants.SERVER_PORT);
        compSocket.setSoTimeout(10000);
        DataOutputStream dout = new DataOutputStream(compSocket.getOutputStream());
        dout.writeUTF("comp");
        listener = new Listener();
        listener.start();
    }

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        try {
            Thread transmitter = new Transmitter(port);
            transmitter.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                System.out.println("Just connected to " + compSocket.getRemoteSocketAddress());

                byte[] previousSend = null;
                byte[] currentSend;
                while (true) {
                    DataOutputStream out = new DataOutputStream(compSocket.getOutputStream());
                    currentSend = listener.getTranslator().bitArray.toByteArray();
                    if (currentSend != null && !currentSend.equals(previousSend)) {
                        if (currentSend.length < 3) {
                            out.writeInt(currentSend.length);
                        } else {
                            out.flush();
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            continue;
                        }
                        out.write(currentSend);
                        previousSend = currentSend;
                        out.flush();
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            } catch (SocketTimeoutException s) {
                System.out.print("Socket timed out!");
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

        }

    }
}
