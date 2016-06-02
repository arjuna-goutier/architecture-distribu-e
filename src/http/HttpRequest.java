package http;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;


public class HttpRequest implements IHttpRequest {
	private String path;
	private final String method;
	private final Map<String, String> headers;
	private final Map<String, String> parameters;
	private Map<String,String> cookies;
	private final String raw;

	public HttpRequest(String method, String path, Map<String, String> headers, Map<String, String> parameters, String raw) {
		this.method = method;
		this.headers = headers;
		this.path = path;
		this.parameters = parameters;
		this.raw = raw;
	}



	public Object getParameter(String name) {
		return parameters.get(name);
	}

	public String[] getParametersNames() {
		return (String[]) parameters.keySet().toArray();
	}

	public String getMethod() {
		return method;
	}

	@Override
	public String getRelativePath() {
		return path;
	}

	@Override
	public String getAbsolutePath() {
		return getHeader("Host") + path;
	}


	@Override
	public String getHeader(String name) {
		return headers.get(name);
	}


	@Override
	public String[] getHeaderName() {
		return (String[]) headers.keySet().toArray();
	}

	@Override
	public String getRaw() {
//        StringBuilder builder = new StringBuilder();
//        builder.append(getMethod()).append(" ").append(getRelativePath());
//        if (! parameters.isEmpty()) {
//            StringJoiner joiner = new StringJoiner("&", "?", "");
//            for(String name: parameters.keySet()) {
//                joiner.add(name + "=" + getParameter(name));
//            }
//            builder.append(joiner);
//        }
//        builder.append("\n");
//        for(String name: headers.keySet()) {
//            builder.append(name).append(":").append(getHeader(name) + "\n");
//        }
//        builder.append("\n");
//        System.out.println(raw);
//        System.out.println(builder.toString());
//        return builder.toString();
        return raw;
	}

	public String getCookie(String name) {
		return getCookies().get(name);
	}
	
	public String[] getCookiesNames() {
		return (String[]) getCookies().keySet().toArray();
	}
	
	private Map<String, String> getCookies() {
		if(cookies != null)
			return cookies;
		if(getHeader("Cookie") != null) {
			cookies = Arrays.stream(getHeader("Cookie").split(";"))
					.map(c -> c.split("="))
					.collect(Collectors.toMap(
									kv -> kv[0].trim(),
									kv -> kv[1].trim(),
									(a1, a2) -> a1
						)
					);
		} else {
			cookies = new HashMap<>();
		}

		return cookies;
	}
}