package server;

import http.IHttpRequest;
import http.IHttpService;

/**
 * Created by arjuna on 01/06/16.
 */
public interface ServiceFactory {
    IHttpService make(IHttpRequest request);
}
