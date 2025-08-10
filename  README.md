## Docs:
The service works out of the box without requiring any additional setup or configuration.
If you experience connectivity issues, try enabling a VPN.

## SportMarketDataService:
- It's possible to enable logs, just to change **new SimpleLogService(false);** here passed parameter to **true**
- For testing purpose it's possible to change count of any nested for (sports->region->league->markets)

| Constant            | Description                                         | Default Value |
| ------------------- |-----------------------------------------------------|---------------|
| `MAX_SPORTS_COUNT`  | Max number of sports to fetch (`-1` = unlimited)    | `-1`          |
| `MAX_REGIONS_COUNT` | Max number of regions per sport (`-1` = unlimited)  | `-1`          |
| `MAX_LEAGUES_COUNT` | Max number of leagues per region (`-1` = unlimited) | `-1`          |
| `MAX_MARKETS_COUNT` | Max number of markets per league (`-1` = unlimited) | `2`           |
| `THREAD_POOL_SIZE`  | Number of threads for concurrent fetching           | `3`           |
