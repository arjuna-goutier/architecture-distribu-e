package load_balancer_config;

import http.IHttpRequest;
import http.IHttpService;

import java.util.Arrays;

/**
 * Created by arjuna on 01/06/16.
 */
public abstract class LoadBalancerGroup {
    private final String name;
    private final String[] members;
    private final String domain;
    public LoadBalancerGroup(String name, String[] members, String domain) {
        super();
        this.name = name;
        this.members = members;
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    protected String getDomain() {
        return domain;
    }

    public String[] getMembers() {
        return members;
    }

    public abstract IHttpService createService(IHttpRequest request);
}
