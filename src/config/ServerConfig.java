package config;

import server.Host;
import threads.ThreadPool;

import java.util.Map;

/**
 * Created by arjuna on 31/05/16.
 */
public class ServerConfig implements HostGetter {
    private final Map<String, Host> hosts;
    private final String name;
    private final int port;
    private final int nbThread;
    private final int nbJob;


    public ServerConfig(Map<String, Host> hosts, String name, int port, int nbThread, int nbJob) {
        this.hosts = hosts;
        this.name = name;
        this.port = port;
        this.nbThread = nbThread;
        this.nbJob = nbJob;
    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return name;
    }


    @Override
    public Host getHost(String name) {
        System.out.println("host:" + name);
        hosts.keySet().forEach(System.out::println);
        hosts.keySet().forEach(c -> System.out.println(c.equals(name)));
        return hosts.get(name.trim());
    }

    public int getNbThread() {
        return nbThread;
    }

    public int getNbJob() {
        return nbJob;
    }

    public ThreadPool createPool() {
        return new ThreadPool(getNbJob(), getNbThread());
    }
}
