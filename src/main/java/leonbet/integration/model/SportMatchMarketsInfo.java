package leonbet.integration.model;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
public class SportMatchMarketsInfo {
    String name;
    String league;
    List<MatchMarkets> matches;

    @Data
    @Builder
    public static class MatchMarkets {
        Long id;
        String name;
        ZonedDateTime startDate;
        List<Market> matchMarkets;
    }

    @Data
    @Builder
    public static class Market {
        Long id;
        String name;
        List<Runner> runners;
    }

    @Data
    @Builder
    public static class Runner {
        Long id;
        String name;
        Float coefficient;
    }
}
