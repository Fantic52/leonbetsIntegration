package leonbet.integration.model.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MatchMarketsBatch {

//    private boolean enabled;
//    private int totalCount;
    @JsonProperty("events")
    private List<MatchData> matches;

    @Data
    public static class MatchData {
        private long id;
//        private String _s;
        private String name;
//        private String nameDefault;
//        private List<Competitor> competitors;
        private long kickoff;
//        private long lastUpdated;
//        private League league;
//        private String betline;
//        private boolean open;
//        private String status;
//        private boolean nativeFlag;
//        private String widgetId;
//        private String widgetType;
//        private boolean widgetVirtual;
//        private String url;
//        private LiveStatus liveStatus;
//        private String matchPhase;
//        private boolean hasMarketWithZeroMargin;
//        private int runnersCount;
        private List<Market> markets;
    }

//    @Data
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public static class Competitor {
//        private long id;
//        private String name;
//        private String homeAway;
//        private String logoSource;
//        private String logo;
//    }

//    @Data
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public static class League {
//        private long id;
//        private Sport sport;
//        private String name;
//        private String nameDefault;
//        private String url;
//        private int weight;
//        private int prematch;
//        private int inplay;
//        private int outright;
//        private boolean top;
//        private boolean hasZeroMarginEvents;
//        private int topOrder;
//        private Region region;
//        private String logoSource;
//        private String logoUrl;
//    }

//    @Data
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public static class Sport {
//        private long id;
//        private String name;
//        private int weight;
//        private String family;
//        private List<Market> mainMarkets;
//        private boolean virtual;
//        private String url;
//    }

    @Data
    public static class Market {
        private long id;
//        private String typeTag;
        private String name;
//        private long marketTypeId;
//        private boolean open;
//        private boolean hasZeroMargin;
//        private boolean primary;
//        private int cols;
        private List<Runner> runners;
//        private Map<String, Object> specifiers;
//        private List<String> selectionTypes;
    }

    @Data
    public static class Runner {
        private long id;
        private String name;
//        private boolean open;
//        private int r;
//        private int c;
//        private List<String> tags;
        private float price;
//        private String priceStr;
    }

//    @Data
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public static class Region {
//        private long id;
//        private String name;
//        private String nameDefault;
//        private String family;
//        private String url;
//    }

//    @Data
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public static class LiveStatus {
//        private String stage;
//        private String score;
//        private String detailedPhase;
//        private String setScores;
//        private String progress;
//        private long createAt;
//        private FullProgress fullProgress;
//        private boolean progressSetupEnabled;
//        private int periodDurationMin;
//        private int numberOfPeriods;
//        private int additionalPeriodDurationMin;
//        private int numberOfAdditionalPeriods;
//        private boolean infiniteOvertime;
//        private Statistics homeStatistics;
//        private Statistics awayStatistics;
//    }
//
//    public static class FullProgress {
//        private Time time;
//        private String timeDirection;
//    }
//
//    public static class Time {
//        private int minutes;
//        private int seconds;
//    }
//
//    public static class Statistics {
//        private int corners;
//        private int yellowCards;
//        private int redCards;
//        private int yellowRedCards;
//    }
}

