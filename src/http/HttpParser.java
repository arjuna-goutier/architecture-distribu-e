package http;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import util.TakeWhile;

public class HttpParser {
	public IHttpRequest parse(final Socket socket) throws IOException, URISyntaxException {
		final InputStream is = socket.getInputStream();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		final String raw = TakeWhile.stream(reader.lines(), l -> l.length() != 0)
				.reduce((c, n) -> c + "\n" + n)
				.get() + "\n\n";

		final String[] lines = raw.split("\\n");

		final String[] firstLineInfo =  lines[0].split(" ");
//		final String[] firstLineInfo =  reader.lines().findFirst().get().split(" ");
		
		
		final String method = firstLineInfo[0];
		final URI address = new URI(firstLineInfo[1]);

		
		System.out.println("start mapping");
		final Map<String, String> kvs = Arrays.stream(lines)
			.skip(1)
			.map(l -> l.split(":", 2))
			.collect(Collectors.toMap(kv -> kv[0].trim(), kv -> kv[1].trim()));
//		final Map<String, String> kvs = TakeWhile.stream(reader.lines(), l -> l.length() != 0)
//			.map(l -> l.split(":"))
//			.collect(Collectors.toMap(kv -> kv[0], kv -> kv[1]));
		
		System.out.println("end mapping");
		
		final String path = address.getPath();
		
		return new HttpRequest(method, path, kvs, getParameters(address), raw);
	}


	public Map<String, String> getParameters(URI address) {
		final String query = address.getQuery();
		final Map<String, String> parameters = new HashMap<>();
		
		if(query != null) {
			for (String parameter: query.split("&")) {
				String[] kv = parameter.split("=");
				if (kv.length > 1) {
					parameters.put(kv[0], kv[1]);				
				} else {
					parameters.put(kv[0], kv[0]);
				}
			}
		}
		return parameters;
	}
}