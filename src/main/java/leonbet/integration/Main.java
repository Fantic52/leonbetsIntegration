package leonbet.integration;

import leonbet.integration.client.impl.LeonbetClientImpl;
import leonbet.integration.model.SportMatchMarketsInfo;
import leonbet.integration.service.SportsMarketDataService;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        var service = new SportsMarketDataService(new LeonbetClientImpl());
        var sportMatchMarkets = service.fetchAllSportMatchMarkets();
        printSportMatchesList(sportMatchMarkets);
    }

    public static void printSportMatchesList(List<SportMatchMarketsInfo> infos) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'");

        for (SportMatchMarketsInfo info : infos) {
            System.out.println(info.getName() + ", " + info.getLeague());
            for (SportMatchMarketsInfo.MatchMarkets match : info.getMatches()) {
                System.out.println("\t" + match.getName() + ", "
                        + match.getStartDate().format(formatter) + ", "
                        + match.getId());
                for (SportMatchMarketsInfo.Market market : match.getMatchMarkets()) {
                    System.out.println("\t\t" + market.getName());
                    for (SportMatchMarketsInfo.Runner runner : market.getRunners()) {
                        System.out.println("\t\t\t"
                                + runner.getName() + ", "
                                + runner.getCoefficient() + ", "
                                + runner.getId());
                    }
                }
            }
        }
    }

}