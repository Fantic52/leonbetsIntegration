package leonbet.integration.client.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import leonbet.integration.client.LeonbetClient;
import leonbet.integration.model.client.MatchMarketsBatch;
import leonbet.integration.model.client.Sport;
import leonbet.integration.parser.GeneralParser;
import leonbet.integration.util.HttpUtils;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.util.List;
import java.util.Map;

public class LeonbetClientImpl implements LeonbetClient {

    public static final String BASE_LEONBET_URL = "https://leonbets.com/api-2/betline";
    public static final String GET_SPORTS_URL = "/sports?ctag=ru-RU";
    public static final String GET_ALL_MARKETS_URL = "/events/all?league_id=%s&ctag=ru-RU&vtag=9c2cd386-31e1-4ce9-a140-28e9b63a9300";

    private static final Map<String, String> DEFAULT_HEADERS = Map.of(
            "Accept", "application/json",
            "accept-language","u-RU,ru;q=0.7,ru;"
    );

    @Override
    public List<Sport> getSports() throws Exception {
        var json = getSportsJson();
        return GeneralParser.parseList(json, new TypeReference<>() {});
    }

    private String getSportsJson() throws Exception {
        var url = BASE_LEONBET_URL + GET_SPORTS_URL;
        var request = HttpUtils.buildGetRequest(url, DEFAULT_HEADERS);

        try (var response = new OkHttpClient().newCall(request).execute()) {
            return extractBody(response);
        }
    }

    @Override
    public MatchMarketsBatch getAllMarketsByLeague(Long leagueId) throws Exception {
        var json = getAllMarkersByLeagueJson(leagueId);
        return GeneralParser.parseObject(json, new TypeReference<>() {});
    }

    private String getAllMarkersByLeagueJson(Long leagueId) throws Exception {
        var url = BASE_LEONBET_URL + GET_ALL_MARKETS_URL.formatted(leagueId.toString());
        var request = HttpUtils.buildGetRequest(url, DEFAULT_HEADERS);

        try (var response = new OkHttpClient().newCall(request).execute()) {
            return extractBody(response);
        }
    }

    private static String extractBody(Response response) throws Exception {
        if (!response.isSuccessful()) {
            throw new RuntimeException("HTTP error: " + response.code() + " " + response.body().string());
        }
        if (response.body() == null) {
            throw new RuntimeException("Empty response body");
        }
        return response.body().string();
    }
}