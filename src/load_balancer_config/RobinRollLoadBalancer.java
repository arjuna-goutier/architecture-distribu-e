package load_balancer_config;

import http.IHttpRequest;
import http.IHttpService;
import load_balancing.RobinRollLoadBalancerService;

/**
 * Created by arjuna on 01/06/16.
 */
public class RobinRollLoadBalancer extends LoadBalancerGroup {
    private int next = 0;

    public RobinRollLoadBalancer(String name, String[] members, String domain) {
        super(name, members, domain);
    }

    public String nextMember() {
        return getMembers()[next++ % getMembers().length];
    }

    @Override
    public IHttpService createService(IHttpRequest request) {
        return new RobinRollLoadBalancerService(nextMember());
    }
}
