package http;
import java.io.OutputStream;
import java.io.Writer;


public interface IHttpResponse {
	Writer getWriter();
	OutputStream getOutputStream();
	void setMethod(String method);
	void setCookie(String name, String value);
}
