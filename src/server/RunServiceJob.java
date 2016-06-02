package server;

import config.HostGetter;
import http.HttpParser;
import http.HttpResponse;
import http.IHttpRequest;
import http.IHttpResponse;
import server.ServiceFactory;
import statics.StaticsService;

import java.net.Socket;

/**
 * Created by arjuna on 01/06/16.
 */
public class RunServiceJob implements Runnable{
    private final ServiceFactory factory;
    private final Socket accept;
    private final HttpParser parser;

    public RunServiceJob(ServiceFactory factory, Socket accept, HttpParser parser) {
        this.factory = factory;
        this.accept = accept;
        this.parser = parser;
    }


    @Override
    public void run() {
        try (final Socket socket = accept) {
            System.out.println("test");
            final IHttpRequest request = parser.parse(socket);
            final IHttpResponse response = new HttpResponse(socket);


            factory.make(request).service(request,response);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
