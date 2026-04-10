package bms.tool.mdprocessor;

import bms.player.beatoraja.Config;

public class WriggleDownloadSource implements HttpDownloadSource {
    public static final HttpDownloadSourceMeta META = new HttpDownloadSourceMeta(
            "wriggle",
            "https://bms.wrigglebug.xyz/download/package/%s",
            WriggleDownloadSource::new
    );

    private final String downloadURL;

    public WriggleDownloadSource(Config config) {
        // override download url if user ask to do so
        String overrideDownloadURL = config.getOverrideDownloadURL();
        this.downloadURL = overrideDownloadURL != null && !overrideDownloadURL.isEmpty()
                ? overrideDownloadURL
                : META.getDefaultURL();
    }

    /**
     * The download url should be a pattern with only one %s placeholder. If not, anything could happen.
     */
    @Override
    public String getDownloadURLBasedOnMd5(String md5) throws java.io.FileNotFoundException {
        String urlStr = String.format(downloadURL, md5);
        try {
            java.net.URL url = new java.net.URL(urlStr);
            java.net.HttpURLConnection conn = null;
            if ("bms.wrigglebug.xyz".equals(url.getHost())) {
                String ipUrl = urlStr.replace("bms.wrigglebug.xyz", "104.21.42.145");
                conn = (java.net.HttpURLConnection) new java.net.URL(ipUrl).openConnection();
                conn.setRequestProperty("Host", "bms.wrigglebug.xyz");
            } else {
                conn = (java.net.HttpURLConnection) url.openConnection();
            }
            conn.setRequestMethod("HEAD");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            int responseCode = conn.getResponseCode();
            if (responseCode == java.net.HttpURLConnection.HTTP_OK) {
                return urlStr;
            } else if (responseCode == java.net.HttpURLConnection.HTTP_NOT_FOUND) {
                throw new java.io.FileNotFoundException("Song not found on wriggle");
            }
            // If other error (like 403 or SSL), we return the URL anyway and hope the main download handles it
            return urlStr;
        } catch (java.io.FileNotFoundException e) {
            throw e;
        } catch (Exception e) {
            // Log error and return URL as fallback
            return urlStr;
        }
    }

    @Override
    public String getName() {
        return META.getName();
    }

    @Override
    public boolean isAllowDownloadThroughMd5() {
        return true;
    }

    @Override
    public boolean isAllowDownloadThroughSha256() {
        return false;
    }

    @Override
    public boolean isAllowMetaQuery() {
        return false;
    }
}
