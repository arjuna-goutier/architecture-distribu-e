package server;

public class Host {
	private final String name;
	private final String directory;
	
	public Host(String name, String directory) {
		super();
		this.name = name;
		this.directory = directory;
	}
	
	public String getDirectory() {
		return directory;
	}
	
	public String getName() {
		return name;
	}
}
