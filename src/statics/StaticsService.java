package statics;
import com.sun.istack.internal.Nullable;
import http.IHttpRequest;
import http.IHttpResponse;
import http.IHttpService;
import server.Host;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;


public class StaticsService implements IHttpService {
    public static final Charset charset = Charset.forName("UTF-8");
    private final Host host;

	public StaticsService(Host host) {
		super();
        this.host = host;
	}


	public void service(IHttpRequest request, IHttpResponse response) {
        System.out.println("response stated");

        String url = request.getAbsolutePath();

        System.out.println(host.getName());
        System.out.println(host.getDirectory());
        Path path = Paths.get(url.replace(host.getName(), host.getDirectory()).trim());

        //imprimez l'entete, ou l'affichage de navigation
        System.out.println("this asked path is : " + path.toString());
        File file = new File(path.toString());

        if (!file.exists()) {
            System.out.println("file not found");
            notFound(response);
            return;
        }

        if (file.isDirectory()) {
            System.out.println("file is directory");
            writeChildren(file.list(), response, path, host.getDirectory().equals(path.toString()));
            return;
        }

        if (file.isFile()) {
            System.out.println("file is file");

            sendFile(path, response);
            return;
        }
    }


    public void sendFile(Path path, IHttpResponse response) {
        try (OutputStream out = response.getOutputStream()) {
            writeHeader(out, path);
            Files.copy(path, out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	public void writeChildren(String[] children, IHttpResponse response, Path path, boolean isRoot) {
		try (Writer out = response.getWriter()) {
			writeHeader(out, path);
            out.write("<a href=\"..\">parent</a>\n");
			for (String child : children) {
                System.out.println("child : " + child);
                out.write("<a href=\"" + (isRoot ? "" : (path.getFileName() +"/")) + child + "\">" + child + "</a>\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void notFound(IHttpResponse response) {
		try (Writer out = response.getWriter()) {
			out.write("HTTP/1.x 404 Not Found" + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



    public void writeHeader(Writer writer, Path path) throws IOException {
        for(String line : getHeaders(path)) {
            writer.write(line + "\n");
        }
        writer.write("\n");
    }

    public void writeHeader(OutputStream outputStream, Path path) throws IOException {
        for(String line : getHeaders(path)) {
            outputStream.write((line + "\n").getBytes(charset));
        }
        outputStream.write("\n".getBytes(charset));
    }

    public String[] getHeaders(@Nullable Path path) throws IOException {
        return new String[] {
            "HTTP/1.x 200 OK",
            "Date:" + LocalDateTime.now().toString(),
            "Content-Type:" + (new File(path.toString()).isFile() ? Files.probeContentType(path) : "text/html")
        };
    }

}
