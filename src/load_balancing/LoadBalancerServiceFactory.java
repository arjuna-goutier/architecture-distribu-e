package load_balancing;

import http.IHttpRequest;
import http.IHttpService;
import load_balancer_config.LoadBalancerConfig;
import server.ServiceFactory;

/**
 * Created by arjuna on 01/06/16.
 */
public class LoadBalancerServiceFactory implements ServiceFactory {
    private final LoadBalancerConfig lbConfig;

    public LoadBalancerServiceFactory(LoadBalancerConfig lbConfig) {
        this.lbConfig = lbConfig;
    }

    @Override
    public IHttpService make(IHttpRequest request) {
        return lbConfig.getGroup(request.getHeader("Host")).createService(request);
    }
}
