import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by Incoruptable on 2/5/2017.
 */
public class Transmitter extends Thread {
    private ServerSocket serverSocket;
    private Listener listener;

    public Transmitter(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
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
                System.out.print("Waiting for RaspberryPi Connection");
                Socket raspberryPi = serverSocket.accept();

                System.out.println("Just connected to " + raspberryPi.getRemoteSocketAddress());

                byte[] previousSend = null;
                byte[] currentSend;
                while (true) {
                    DataOutputStream out = new DataOutputStream(raspberryPi.getOutputStream());
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
