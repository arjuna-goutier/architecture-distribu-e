package server;

import config.HostGetter;

import java.net.Socket;

/**
 * Created by arjuna on 01/06/16.
 */
public interface JobFactory {
    Runnable make(Socket socket);
}