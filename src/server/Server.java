package server;
import config.ServerConfig;
import threads.ThreadPool;

import java.io.IOException;
import java.net.ServerSocket;


public class Server implements Runnable {
	private final ServerConfig config;
	private final ThreadPool threadPool;
	private final JobFactory jobFactory;


	public Server(final ServerConfig config, JobFactory jobFactory) {
		this.config = config;
		this.jobFactory = jobFactory;
		this.threadPool = config.createPool();
	}
	
	public void launchServer() throws IOException {
		threadPool.start();
		try (final ServerSocket serverSocket = new ServerSocket(config.getPort())) {
			while(true) {
				try {
					threadPool.addJob(jobFactory.make(serverSocket.accept()));
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void run() {
		try {
			launchServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
