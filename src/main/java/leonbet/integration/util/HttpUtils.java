package leonbet.integration.util;

import okhttp3.Request;

import java.util.Map;

public class HttpUtils {

    public static Request buildGetRequest(String url, Map<String, String> headers) {
        var builder = new Request.Builder().url(url);

        for (var entry : headers.entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }

        return builder.build();
    }
}