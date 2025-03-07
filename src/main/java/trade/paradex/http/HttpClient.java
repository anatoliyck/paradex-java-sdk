package trade.paradex.http;

import trade.paradex.http.response.HttpResponse;

import java.io.Closeable;
import java.util.Map;

public interface HttpClient extends Closeable {

    HttpResponse get(String url, Map<String, String> headers, Map<String, String> queryParams);

    HttpResponse post(String url, Map<String, String> headers, String body);

    HttpResponse put(String url, Map<String, String> headers, String body);

    HttpResponse delete(String url, Map<String, String> header, Map<String, String> queryParams);

}
