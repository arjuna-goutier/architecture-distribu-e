import config.ServerConfig;
import config.ServerConfigReader;
import http.HttpParser;
import load_balancer_config.LoadBalancerConfig;
import load_balancer_config.LoadBalancerConfigReader;
import load_balancing.LoadBalancerServiceFactory;
import server.RunServiceJobFactory;
import server.Server;
import statics.StaticsJobFactory;

import java.io.IOException;

public class Main {
    public static final String CONFIG_PATH = "/home/arjuna/IdeaProjects/architecture/server.config";
    public static final String LB_CONFIG_PATH = "/home/arjuna/IdeaProjects/architecture/loader.config";
    public static final String CONFIG_PATH_2 = "/home/arjuna/IdeaProjects/architecture/server2.config";
    public static final String CONFIG_PATH_3 = "/home/arjuna/IdeaProjects/architecture/server3.config";


    public static void main(String[] args) throws IOException {
        ServerConfig config = new ServerConfigReader().read(CONFIG_PATH);
        ServerConfig config2 = new ServerConfigReader().read(CONFIG_PATH_2);
        ServerConfig config3 = new ServerConfigReader().read(CONFIG_PATH_3);

        LoadBalancerConfig lbConfig = new LoadBalancerConfigReader().read(LB_CONFIG_PATH);

        new Thread(new Server(config, new RunServiceJobFactory(new LoadBalancerServiceFactory(lbConfig), new HttpParser()))).start();
        new Thread(new Server(config2, new RunServiceJobFactory(new StaticsJobFactory(config2), new HttpParser()))).start();
        new Thread(new Server(config3, new RunServiceJobFactory(new StaticsJobFactory(config3), new HttpParser()))).start();
    }
}
