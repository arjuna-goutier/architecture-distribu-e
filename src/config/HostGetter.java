package config;

import server.Host;

/**
 * Created by arjuna on 01/06/16.
 */
public interface HostGetter {
    Host getHost(String name);
}
