package trade.paradex.http;

import lombok.SneakyThrows;
import trade.paradex.exception.ParadexAPIException;
import trade.paradex.http.response.HttpResponse;
import trade.paradex.utils.GZIPUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

public class DefaultHttpClient implements HttpClient {

    private static final java.net.http.HttpClient DEFAULT_HTTP_CLIENT = java.net.http.HttpClient.newBuilder()
            .version(java.net.http.HttpClient.Version.HTTP_1_1)
            .followRedirects(java.net.http.HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(2))
            .build();

    private final java.net.http.HttpClient httpClient;

    public DefaultHttpClient() {
        this(DEFAULT_HTTP_CLIENT);
    }

    public DefaultHttpClient(java.net.http.HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public HttpResponse get(String url, Map<String, String> headers, Map<String, String> queryParams) {
        String urlWithParams = url + encodeQueryParam(queryParams);
        HttpRequest.Builder request = HttpRequest.newBuilder()
                .uri(URI.create(urlWithParams))
                .GET()
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json; charset=utf-8");
        if (headers != null && !headers.isEmpty()) {
            request.headers(headersToArray(headers));
        }

        HttpRequest httpRequest = request.build();
        return doRequest(httpRequest);
    }

    @Override
    public HttpResponse post(String url, Map<String, String> headers, String body) {
        HttpRequest.BodyPublisher bodyPublisher = body != null && !body.isBlank()
                ? HttpRequest.BodyPublishers.ofString(body)
                : HttpRequest.BodyPublishers.noBody();
        HttpRequest.Builder request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(bodyPublisher)
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json; charset=utf-8");
        if (headers != null && !headers.isEmpty()) {
            request.headers(headersToArray(headers));
        }

        HttpRequest httpRequest = request.build();
        return doRequest(httpRequest);
    }

    @Override
    public HttpResponse put(String url, Map<String, String> headers, String body) {
        HttpRequest.BodyPublisher bodyPublisher = body != null && !body.isBlank()
                ? HttpRequest.BodyPublishers.ofString(body)
                : HttpRequest.BodyPublishers.noBody();
        HttpRequest.Builder request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .PUT(bodyPublisher)
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json; charset=utf-8");
        if (headers != null && !headers.isEmpty()) {
            request.headers(headersToArray(headers));
        }

        HttpRequest httpRequest = request.build();
        return doRequest(httpRequest);
    }

    @Override
    public HttpResponse delete(String url, Map<String, String> headers, Map<String, String> queryParams) {
        String urlWithParams = url + encodeQueryParam(queryParams);
        HttpRequest.Builder request = HttpRequest.newBuilder()
                .uri(URI.create(urlWithParams))
                .DELETE()
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json; charset=utf-8");
        if (headers != null && !headers.isEmpty()) {
            request.headers(headersToArray(headers));
        }

        HttpRequest httpRequest = request.build();
        return doRequest(httpRequest);
    }

    private HttpResponse doRequest(HttpRequest request) {
        try {
            java.net.http.HttpResponse<byte[]> response = httpClient.send(request, java.net.http.HttpResponse.BodyHandlers.ofByteArray());
            String content = getContent(response);
            return new HttpResponse(response.statusCode(), content);
        } catch (IOException | InterruptedException e) {
            throw new ParadexAPIException(null, "Unchecked IO exception: " + e.getMessage());
        }
    }

    @SneakyThrows
    public String getContent(java.net.http.HttpResponse<byte[]> response) {
        Optional<String> encoding = response.headers().firstValue("Content-Encoding");
        byte[] body = response.body();
        String bodyAsStr;
        if (encoding.isPresent() && encoding.get().equals("gzip")) {
            bodyAsStr = GZIPUtils.decode(body);
        } else {
            bodyAsStr = new String(body, StandardCharsets.UTF_8);
        }

        return bodyAsStr;
    }

    private String[] headersToArray(Map<String, String> headers) {
        String[] headersArray = new String[headers.size() * 2];
        int i = 0;
        for (var entry : headers.entrySet()) {
            headersArray[i++] = entry.getKey();
            headersArray[i++] = entry.getValue();
        }
        return headersArray;
    }

    private String encodeQueryParam(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder("?");
        for (var entry : params.entrySet()) {
            if (entry.getValue() != null) {
                sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8)).append("&");
            }
        }
        sb.deleteCharAt(sb.length() - 1); // delete last ampersand

        return sb.toString();
    }

    @Override
    public void close() {
    }
}
