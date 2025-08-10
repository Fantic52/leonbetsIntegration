package leonbet.integration.service;

public record SimpleLogService(boolean enabled) {

    public void info(String message, Object... args) {
        if (enabled) {
            System.out.printf(message + "%n", args);
        }
    }

    public void error(String message, Throwable t) {
        if (enabled) {
            System.err.printf(message + ": %s%n", t.getMessage());
            t.printStackTrace(System.err);
        }
    }
}