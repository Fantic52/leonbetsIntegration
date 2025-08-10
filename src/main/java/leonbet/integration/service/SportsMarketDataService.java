package leonbet.integration.service;

import leonbet.integration.client.LeonbetClient;
import leonbet.integration.model.SportMatchMarketsInfo;
import leonbet.integration.model.client.MatchMarketsBatch;
import leonbet.integration.model.client.Sport;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SportsMarketDataService {

    private static final int MAX_SPORTS_COUNT = -1;
    private static final int MAX_REGIONS_COUNT = -1;
    private static final int MAX_LEAGUES_COUNT = -1;
    private static final int MAX_MARKETS_COUNT = -1;

    private static final int THREAD_POOL_SIZE = 3;

    private final LeonbetClient leonbetClient;
    private final SimpleLogService logService = new SimpleLogService(false);
    private final ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public List<SportMatchMarketsInfo> fetchAllSportMatchMarkets() throws Exception {
        logService.info("Start fetching sports");

        var sports = leonbetClient.getSports();
        logService.info("Finish fetching sports, totalCount = %s", sports.size());

        var result = new ArrayList<SportMatchMarketsInfo>();

        int sportCount = 0;
        for (var sport : sports) {
            if (isLimitReached(sportCount++, MAX_SPORTS_COUNT)) break;
            result.addAll(processSport(sport));
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        logService.info("Finish fetching markets");
        return result;
    }

    private List<SportMatchMarketsInfo> processSport(Sport sport) throws Exception {
        logService.info("Start iteration sport %s, regionsCount = %s", sport.getName(), sport.getRegions().size());

        var result = new ArrayList<SportMatchMarketsInfo>();

        int regionCount = 0;
        for (var region : sport.getRegions()) {
            if (isLimitReached(regionCount++, MAX_REGIONS_COUNT)) break;
            result.addAll(processRegion(sport, region));
        }

        logService.info("Finish iteration sport %s", sport.getName());
        return result;
    }

    private List<SportMatchMarketsInfo> processRegion(Sport sport, Sport.Region region) throws Exception {
        logService.info("Start iteration region %s, leagueCount = %s", region.getName(), region.getLeagues().size());

        var futures = new ArrayList<Future<SportMatchMarketsInfo>>();

        int leagueCount = 0;
        for (var league : region.getLeagues()) {
            if (isLimitReached(leagueCount++, MAX_LEAGUES_COUNT)) break;
            futures.add(executor.submit(() -> processLeague(sport, league)));
        }

        var results = new ArrayList<SportMatchMarketsInfo>();
        for (Future<SportMatchMarketsInfo> future : futures) {
            try {
                results.add(future.get());
            } catch (ExecutionException e) {
                logService.error("Error fetching markets for a league", e.getCause());
            }
        }

        logService.info("Finish iteration region %s", region.getName());
        return results;
    }
    private SportMatchMarketsInfo processLeague(Sport sport, Sport.League league) throws Exception {
        logService.info("Start iteration league %s", league.getName());

        logService.info("Start fetching markets by league %s", league.getName());
        var marketsByLeague = leonbetClient.getAllMarketsByLeague(league.getId());
        logService.info("Finish fetching markets by league %s, totalCount = %s",
                league.getName(), marketsByLeague.getMatches().size());

        var mappedMatchMarkets = marketsByLeague.getMatches().stream()
                .filter(match -> match.getMarkets() != null)
                .limit(MAX_MARKETS_COUNT)
                .collect(Collectors.toMap(
                        match -> match,
                        match -> match.getMarkets()
                ));

        logService.info("Finish iteration league %s", league.getName());
        return toSportMatchMarketsInfo(sport, league, mappedMatchMarkets);
    }

    private static SportMatchMarketsInfo toSportMatchMarketsInfo(Sport sport, Sport.League league, Map<MatchMarketsBatch.MatchData, List<MatchMarketsBatch.Market>> mappedMatchMarkets) {
        return SportMatchMarketsInfo.builder()
                .name(sport.getName())
                .league(league.getName())
                .matches(mappedMatchMarkets.entrySet()
                        .stream()
                        .map(entry -> SportMatchMarketsInfo.MatchMarkets.builder()
                                .id(entry.getKey().getId())
                                .name(entry.getKey().getName())
                                .startDate(Instant.ofEpochMilli(entry.getKey().getKickoff())
                                        .atZone(ZoneId.of("UTC")))
                                .matchMarkets(entry.getValue().stream().map(market -> SportMatchMarketsInfo.Market.builder()
                                        .id(market.getId())
                                        .name(market.getName())
                                        .runners(market.getRunners().stream().map(runner -> SportMatchMarketsInfo.Runner.builder()
                                                .id(runner.getId())
                                                .name(runner.getName())
                                                .coefficient(runner.getPrice())
                                                .build()).toList())
                                        .build()).toList())
                                .build())
                        .toList())
                .build();
    }

    private static boolean isLimitReached(int currentCount, int maxCount) {
        return maxCount != -1 && currentCount >= maxCount;
    }
}
