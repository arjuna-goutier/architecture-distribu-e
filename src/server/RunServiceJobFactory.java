package server;

import http.HttpParser;

import java.net.Socket;

/**
 * Created by arjuna on 01/06/16.
 */
public class RunServiceJobFactory implements JobFactory{
    private final ServiceFactory serviceFactory;
    private  final HttpParser parser;

    public RunServiceJobFactory(ServiceFactory serviceFactory, HttpParser parser) {
        this.serviceFactory = serviceFactory;
        this.parser = parser;
    }

    @Override
    public Runnable make(Socket socket) {
        System.out.println("test");

        return new RunServiceJob(serviceFactory, socket, parser);
    }
}
