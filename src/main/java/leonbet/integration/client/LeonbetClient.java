package leonbet.integration.client;

import leonbet.integration.model.client.MatchMarketsBatch;
import leonbet.integration.model.client.Sport;

import java.util.List;

public interface LeonbetClient {

    List<Sport> getSports() throws Exception;

    MatchMarketsBatch getAllMarketsByLeague(Long league) throws Exception;
}