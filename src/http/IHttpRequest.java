package http;

public interface IHttpRequest {
	Object getParameter(String name);
	String[] getParametersNames();
	String getMethod();
	String getRelativePath();
	String getAbsolutePath();
	String getHeader(String name);
	String[] getHeaderName();
	String getCookie(String name);
	String getRaw();
}
