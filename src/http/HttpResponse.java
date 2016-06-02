package http;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;


public class HttpResponse implements IHttpResponse {

	private final Socket socket;
	
	public HttpResponse(final Socket socket) {
		this.socket = socket;
	}

	public Writer getWriter() {
		try {
			return new OutputStreamWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public OutputStream getOutputStream() {
		try {
			return socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setMethod(final String method) {
		// TODO Auto-generated method stub setMethod
	}

	public void setCookie(final String name, final String value) {
		// TODO set Cookie
		try {
			getWriter().write("SET-COOKIE:" + name + "=" + value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
