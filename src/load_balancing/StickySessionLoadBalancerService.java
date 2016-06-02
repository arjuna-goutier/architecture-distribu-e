package load_balancing;

import http.IHttpRequest;
import http.IHttpResponse;
import http.IHttpService;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by arjuna on 01/06/16.
 */
public class StickySessionLoadBalancerService implements IHttpService {
    private final String redirectTo;

    public StickySessionLoadBalancerService(String redirectTo) {
        this.redirectTo = redirectTo;
    }
    private final byte newline = (byte) '\n';
    private void redirect(BufferedInputStream is, BufferedOutputStream os) throws IOException {
//        os.write(new BufferedReader(new InputStreamReader(is)).readLine().getBytes("UTF-8"));
/*
        os.write(("SET-COOKIE:" + "__lb_id=" + redirectTo + "\n").getBytes("UTF-8"));
        //write cookie
        System.out.println(redirectTo);
        os.flush();
        byte[] buffer = new byte[1024];
        int n;
        Stream.
        while((n = is.read(buffer)) > -1) {
            os.write(buffer, 0, n);
            os.flush();
        }
        os.flush();
        os.close();*/
    }
    @Override
    public void service(IHttpRequest request, IHttpResponse response) {
            try {
                String[] tmp = redirectTo.split(":");
                System.out.println(redirectTo);
                Socket socket = new Socket(tmp[0], tmp.length > 1 ? Integer.parseInt(tmp[1]) : 80);
                OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
                writer.write(request.getRaw());
                writer.flush();

                OutputStream out = response.getOutputStream();
                InputStream in = socket.getInputStream();
                redirect(new BufferedInputStream(in), new BufferedOutputStream(out));
                writer.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
