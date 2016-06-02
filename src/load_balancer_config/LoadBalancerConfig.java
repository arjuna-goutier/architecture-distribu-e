package load_balancer_config;

import java.util.Map;

/**
 * Created by arjuna on 01/06/16.
 */
public class LoadBalancerConfig {
    public final Map<String, LoadBalancerGroup> groups;

    public LoadBalancerConfig(Map<String, LoadBalancerGroup> groups) {
        this.groups = groups;
    }

    public LoadBalancerGroup getGroup(String name) {
        System.out.println(name);
        return groups.get(name.trim());
    }


}
