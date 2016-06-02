package server;

import http.IHttpRequest;
import http.IHttpService;
import server.ServiceFactory;

/**
 * Created by arjuna on 01/06/16.
 */
public class SimpleServiceFactory implements ServiceFactory {
    private final Class<? extends IHttpService> service;

    public SimpleServiceFactory(Class<? extends IHttpService> service) {
        this.service = service;
    }

    @Override
    public IHttpService make(IHttpRequest request) {
        try {
            return service.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
