package config;

import server.Host;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by arjuna on 31/05/16.
 */
public class ServerConfigReader {

    public ServerConfig read(String path) throws IOException {
        final Properties properties = new Properties();
        properties.load(new FileInputStream(path));
        for(Object props : properties.keySet()) {
            System.out.println(props + " -> " + properties.get(props));
        }

        final Function<String,Host> getHostFromName = (String o) ->
                new Host(properties.getProperty(o + ".name"), properties.getProperty(o + ".base"));

        final int port = Integer.parseInt(properties.getProperty("server.port"));
        final int nbThread = Integer.parseInt(properties.getProperty("thread_pool.workerNumber"));
        final int nbJob = Integer.parseInt(properties.getProperty("thread_pool.maxJobNumber"));

        final String serverName = properties.getProperty("server.name");

        final Map<String, Host> hosts =
                properties.stringPropertyNames().stream()
                .map(l -> l.split("\\."))
                .filter(oa -> oa.length != 0)
                .filter(oa -> oa[0].contains("vhost"))
                .map(oa -> oa[0])
                .distinct()
                .map(getHostFromName)
                .collect(Collectors.toMap(Host::getName, Function.identity()));

        for (String hostnames : hosts.keySet()) {
            System.out.println(hostnames + " -> " + hosts.get(hostnames));
        }

        System.out.println("finished telling hosts");
        return new ServerConfig(hosts, serverName, port, nbThread, nbJob);
    }
}
