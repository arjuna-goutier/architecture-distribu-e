package load_balancer_config;

import http.IHttpRequest;
import http.IHttpService;
import load_balancing.StickySessionLoadBalancerService;

import java.util.*;

/**
 * Created by arjuna on 01/06/16.
 */

public class StickySessionLoadBalancer extends LoadBalancerGroup {
    private final Map<String, Integer> connectionCount = new HashMap<>();

    public StickySessionLoadBalancer(String name, String[] members, String domain) {
        super(name, members, domain);
        for(String member: members) {
            connectionCount.put(member, 0);
        }
    }

    @Override
    public IHttpService createService(IHttpRequest request) {
        return new StickySessionLoadBalancerService(nextMember(request));
    }

    public String nextMember(IHttpRequest request) {
        final String host = request.getCookie("__lb_id");
        if(host != null) {
            System.out.println("RETURN TO HOST !");
            return host;
        }
        System.out.println("GET NEW HOST");
        final String newHost = Arrays.stream(getMembers())
                .min((c, n) -> Integer.compare(connectionCount.get(c), connectionCount.get(n)))
                .get();
        System.out.println("FOUND : " + newHost);
        synchronized (connectionCount) {
            connectionCount.put(newHost, connectionCount.get(newHost) + 1);
        }
        return newHost;
    }
}
