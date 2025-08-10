package leonbet.integration.model.client;

import lombok.Data;

import java.util.List;

@Data
public class Sport {
    private long id;
//    private String _s;
    private String name;
//    private int weight;
//    private String family;
    private List<Region> regions;

    @Data
    public static class Region {
        private long id;
        private String name;
//        private String nameDefault;
//        private String family;
//        private String url;
        private List<League> leagues;
    }

    @Data
    public static class League {
        private long id;
        private String name;
//        private String nameDefault;
//        private String url;
//        private int weight;
//        private int prematch;
//        private int inplay;
//        private int outright;
//        private boolean top;
//        private int topOrder;
//        private boolean hasZeroMarginEvents;
//        private String logoUrl;
//        private String background;
    }
}
