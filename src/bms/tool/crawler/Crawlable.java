package bms.tool.crawler;

/**
 * Crawlable interface for objects that can be downloaded from a URL.
 */
public interface Crawlable {
    String getUrl();
    String getTitle();
}
