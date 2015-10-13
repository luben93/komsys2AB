package start;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by luben on 2015-10-13.
 */
public class TcpListner {
    private ServerSocket serverSocket;

    public TcpListner(int port) throws IOException {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            System.exit(-1);
        }
    }

}
