package load_balancing;

import http.IHttpRequest;
import http.IHttpResponse;
import http.IHttpService;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

/**
 * Created by arjuna on 01/06/16.
 */
public class RobinRollLoadBalancerService implements IHttpService {
    private String redirectTo;

    public RobinRollLoadBalancerService(String redirectTo) {
        this.redirectTo = redirectTo;
    }

    private void redirect(InputStream is, OutputStream os) throws IOException {
        int n;
        byte[] buffer = new byte[1024];
        while((n = is.read(buffer)) > -1) {
            os.write(buffer, 0, n);
        }
        os.close();
    }

    @Override
    public void service(IHttpRequest request, IHttpResponse response) {
        try {
            String[] tmp = redirectTo.split(":");
            System.out.println(redirectTo);
            Socket socket = new Socket(tmp[0], Integer.parseInt(tmp[1]));
            OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
            writer.write(request.getRaw());
            writer.flush();

            OutputStream out = response.getOutputStream();
            InputStream in = socket.getInputStream();
            redirect(in, out);
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
