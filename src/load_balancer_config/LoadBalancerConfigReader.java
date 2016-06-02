package load_balancer_config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by arjuna on 01/06/16.
 */
public class LoadBalancerConfigReader {
    public LoadBalancerConfig read(String path)  throws IOException {
        {
            final Properties properties = new Properties();
            properties.load(new FileInputStream(path));



            final Function<String, LoadBalancerGroup> getLbGroupFromName = (String o) ->
                    getRobinRollLoadBalancer(properties, o);


            final Map<String, LoadBalancerGroup> hosts =
                    properties.stringPropertyNames().stream()
                            .map(l -> l.split("\\."))
                            .filter(oa -> oa.length != 0)
                            .filter(oa -> oa[0].contains("group"))
                            .map(oa -> oa[0])
                            .distinct()
                            .map(getLbGroupFromName)
                            .collect(Collectors.toMap(LoadBalancerGroup::getDomain, Function.identity()));

            System.out.println("displaying domains");

            hosts.keySet().forEach(System.out::println);
            System.out.println("end displaying domain");
            return new LoadBalancerConfig(hosts);
        }
    }

    private LoadBalancerGroup getRobinRollLoadBalancer(Properties properties, String o) {
        switch (properties.getProperty(o + ".lb.method")) {
            case "robin_roller":
                return new RobinRollLoadBalancer(
                    properties.getProperty(o + ".name"),
                    properties.getProperty(o + ".members").split(","),
                    properties.getProperty(o + ".domain")
                );

            case "sticky_session":
                return new StickySessionLoadBalancer(
                    properties.getProperty(o + ".name"),
                    properties.getProperty(o + ".members").split(","),
                    properties.getProperty(o + ".domain")
                );
            default:
                throw new RuntimeException("not a possibility :" + properties.getProperty(o + ".lb.method"));
        }
    }
}
