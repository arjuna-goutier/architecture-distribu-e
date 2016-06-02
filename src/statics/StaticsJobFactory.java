package statics;

import config.HostGetter;
import http.IHttpRequest;
import http.IHttpService;
import server.ServiceFactory;

/**
 * Created by arjuna on 01/06/16.
 */
public class StaticsJobFactory implements ServiceFactory {
    private final HostGetter hosts;

    public StaticsJobFactory(HostGetter hosts) {
        this.hosts = hosts;
    }

    @Override
    public IHttpService make(IHttpRequest request) {
        return new StaticsService(hosts.getHost(request.getHeader("Host")));
    }
}
