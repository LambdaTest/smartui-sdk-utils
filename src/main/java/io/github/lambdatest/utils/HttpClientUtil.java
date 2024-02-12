package io.github.lambdatest.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import io.github.lambdatest.constants.Constants;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import org.json.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonElement;


public class HttpClientUtil {
    private final CloseableHttpClient httpClient;
    private Logger log;

    public HttpClientUtil() {
        this.httpClient = HttpClients.createDefault();
        this.log = LoggerUtil.createLogger("lambdatest-java-sdk");
    }

    public String request(String url, String method, String data) throws IOException {
        if (Constants.RequestMethods.POST.equalsIgnoreCase(method)) {
            return post(url, data);
        } else if (Constants.RequestMethods.GET.equalsIgnoreCase(method)) {
            return get(url);
        } else {
            throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
    }

    private String get(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            checkResponseStatus(response);
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
        }
    }

    private String post(String url, String data) throws IOException {
        HttpPost request = new HttpPost(url);
        request.setEntity(new StringEntity(data, StandardCharsets.UTF_8));
        request.setHeader("Content-type", "application/json");
    
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            String responseString = entity != null ? EntityUtils.toString(entity) : null;
    
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                // Request was successful
                return responseString;
            } else {
                // Request failed, attempt to parse error message from response
                try {
                    JsonElement element = new JsonParser().parse(responseString);
                    if (element.isJsonObject()) {
                        JsonObject jsonResponse = element.getAsJsonObject();
                        if (jsonResponse.has("error") && jsonResponse.get("error").isJsonObject()) {
                            JsonObject errorObject = jsonResponse.getAsJsonObject("error");
                            if (errorObject.has("message")) {
                                String errorMessage = errorObject.get("message").getAsString();
                                log.severe(String.format(Constants.Errors.POST_SNAPSHOT_FAILED, errorMessage));
                            }
                        }
                    }
                } catch (JsonSyntaxException e) {
                    throw new IOException("Failed to parse error response: " + responseString, e);
                }
                throw new IOException("Unexpected status code: " + statusCode);
            }
        }
    }
    
    private void checkResponseStatus(HttpResponse response) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            throw new IOException("Request failed with status code: " + statusCode + " and response: " + response);
        }
    }


    public String isSmartUIRunning() throws IOException {
        return request(SmartUIUtil.getSmartUIServerAddress() + Constants.SmartUIRoutes.SMARTUI_HEALTHCHECK_ROUTE, Constants.RequestMethods.GET, null);
    }

    public String fetchDOMSerializer() throws IOException {
        return request(SmartUIUtil.getSmartUIServerAddress() + Constants.SmartUIRoutes.SMARTUI_DOMSERIALIZER_ROUTE, Constants.RequestMethods.GET, null);
    }

    public String postSnapshot(String data) throws IOException {
        return request(SmartUIUtil.getSmartUIServerAddress() + Constants.SmartUIRoutes.SMARTUI_SNPASHOT_ROUTE, Constants.RequestMethods.POST, data);
    }


}