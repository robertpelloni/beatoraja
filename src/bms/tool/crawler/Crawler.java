package bms.tool.crawler;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Logger;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

public class Crawler {

    private Deque<Crawlable> commands = new ConcurrentLinkedDeque<>();
    private CrawlerDaemonThread daemon;
    private volatile String message = "";
    private volatile String downloadPathResult;
    private volatile boolean isDownloading = false;

    public void start(Crawlable item) {
        if (daemon == null || !daemon.isAlive()) {
            daemon = new CrawlerDaemonThread();
            daemon.start();
        }
        if (item != null) {
            commands.add(item);
        }
    }

    public void dispose() {
        if (daemon != null && daemon.isAlive()) {
            daemon.dispose = true;
            try {
                daemon.interrupt();
                daemon.join(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isDownloading() {
        return isDownloading;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public String getDownloadPathResult() {
        return downloadPathResult;
    }

    public void clearDownloadPathResult() {
        this.downloadPathResult = null;
    }

    class CrawlerDaemonThread extends Thread {
        boolean dispose = false;

        @Override
        public void run() {
            while (!dispose) {
                if (!commands.isEmpty()) {
                    isDownloading = true;
                    Crawlable item = commands.removeFirst();
                    processItem(item);
                    isDownloading = false;
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // ignore
                    }
                }
            }
        }

        private void processItem(Crawlable item) {
            String urlStr = item.getUrl();
            if (urlStr == null || urlStr.isEmpty()) return;

            Logger.getGlobal().info("Crawler: Starting download for " + item.getTitle() + " from " + urlStr);
            setMessage("Downloading " + item.getTitle() + "...");

            // Determine filename from URL or title
            String fileName = urlStr.substring(urlStr.lastIndexOf('/') + 1);
            if (fileName.isEmpty() || fileName.contains("?")) {
                fileName = "download_" + System.currentTimeMillis() + ".tmp";
            }

            Path downloadDir = Paths.get("Downloads");
            if (!Files.exists(downloadDir)) {
                try {
                    Files.createDirectories(downloadDir);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }

            Path targetFile = downloadDir.resolve(fileName);

            try {
                downloadFile(urlStr, targetFile);
                setMessage("Extracting " + item.getTitle() + "...");
                String extractedPath = extractArchive(targetFile);
                if (extractedPath != null) {
                    downloadPathResult = extractedPath;
                    Logger.getGlobal().info("Crawler: Download and extraction complete: " + extractedPath);
                    setMessage("Download complete: " + item.getTitle());
                } else {
                    Logger.getGlobal().warning("Crawler: Failed to extract or unknown format: " + targetFile);
                    setMessage("Extraction failed: " + item.getTitle());
                }

                // Cleanup archive if extracted
                if (extractedPath != null) {
                    Files.deleteIfExists(targetFile);
                }

            } catch (Exception e) {
                e.printStackTrace();
                Logger.getGlobal().severe("Crawler: Error downloading " + item.getTitle() + ": " + e.getMessage());
                setMessage("Error: " + e.getMessage());
            }
        }

        private void downloadFile(String urlStr, Path target) throws IOException {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "beatoraja-crawler");

            try (InputStream in = new BufferedInputStream(conn.getInputStream());
                 OutputStream out = new FileOutputStream(target.toFile())) {

                byte[] data = new byte[8192];
                int count;
                long total = 0;
                while ((count = in.read(data)) != -1) {
                    out.write(data, 0, count);
                    total += count;
                    if (total % (1024 * 1024) == 0) {
                        // Update progress occasionally if needed
                    }
                }
            }
        }

        private String extractArchive(Path archivePath) {
            String fileName = archivePath.getFileName().toString().toLowerCase();
            String outputDirName = fileName.contains(".") ? fileName.substring(0, fileName.lastIndexOf('.')) : fileName;
            Path outputDir = archivePath.getParent().resolve(outputDirName);

            try {
                if (!Files.exists(outputDir)) {
                    Files.createDirectories(outputDir);
                }

                if (fileName.endsWith(".zip")) {
                    try (InputStream fis = Files.newInputStream(archivePath);
                         InputStream bis = new BufferedInputStream(fis);
                         ArchiveInputStream ais = new ArchiveStreamFactory().createArchiveInputStream(ArchiveStreamFactory.ZIP, bis)) {
                        extract(ais, outputDir);
                        return outputDir.toAbsolutePath().toString();
                    }
                } else if (fileName.endsWith(".tar") || fileName.endsWith(".tar.gz") || fileName.endsWith(".tgz")) {
                     InputStream fis = Files.newInputStream(archivePath);
                     InputStream bis = new BufferedInputStream(fis);
                     if (fileName.endsWith(".gz") || fileName.endsWith(".tgz")) {
                         bis = new GzipCompressorInputStream(bis);
                     }
                     try (ArchiveInputStream ais = new ArchiveStreamFactory().createArchiveInputStream(ArchiveStreamFactory.TAR, bis)) {
                         extract(ais, outputDir);
                         return outputDir.toAbsolutePath().toString();
                     }
                } else if (fileName.endsWith(".7z")) {
                    try (SevenZFile sevenZFile = new SevenZFile(archivePath.toFile())) {
                        SevenZArchiveEntry entry;
                        while ((entry = sevenZFile.getNextEntry()) != null) {
                            if (entry.isDirectory()) {
                                continue;
                            }
                            File curfile = new File(outputDir.toFile(), entry.getName());
                            if (!curfile.getCanonicalPath().startsWith(outputDir.toAbsolutePath().toFile().getCanonicalPath())) {
                                throw new IOException("Zip Slip vulnerability detected: " + entry.getName());
                            }
                            File parent = curfile.getParentFile();
                            if (!parent.exists()) {
                                parent.mkdirs();
                            }
                            try (FileOutputStream out = new FileOutputStream(curfile)) {
                                byte[] content = new byte[(int) entry.getSize()];
                                sevenZFile.read(content, 0, content.length);
                                out.write(content);
                            }
                        }
                        return outputDir.toAbsolutePath().toString();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private void extract(ArchiveInputStream ais, Path outputDir) throws IOException {
            ArchiveEntry entry;
            while ((entry = ais.getNextEntry()) != null) {
                if (!ais.canReadEntryData(entry)) {
                    continue;
                }
                File f = new File(outputDir.toFile(), entry.getName());
                if (!f.getCanonicalPath().startsWith(outputDir.toAbsolutePath().toFile().getCanonicalPath())) {
                    throw new IOException("Zip Slip vulnerability detected: " + entry.getName());
                }
                if (entry.isDirectory()) {
                    if (!f.isDirectory() && !f.mkdirs()) {
                        throw new IOException("Failed to create directory " + f);
                    }
                } else {
                    File parent = f.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }
                    try (OutputStream o = Files.newOutputStream(f.toPath())) {
                        IOUtils.copy(ais, o);
                    }
                }
            }
        }
    }
}
