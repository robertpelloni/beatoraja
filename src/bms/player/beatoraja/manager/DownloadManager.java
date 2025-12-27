package bms.player.beatoraja.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;

import bms.player.beatoraja.MainController;
import bms.player.beatoraja.song.SongData;
import bms.tool.crawler.Crawler;
import bms.tool.mdprocessor.MusicDownloadProcessor;

public class DownloadManager {

    private final MainController main;
    private MusicDownloadProcessor download;
    private Crawler crawler;

    public DownloadManager(MainController main) {
        this.main = main;
        
        if (main.getConfig().isEnableIpfs()) {
            Path ipfspath = Paths.get("ipfs").toAbsolutePath();
            if (!ipfspath.toFile().exists())
                ipfspath.toFile().mkdirs();
            List<String> roots = new ArrayList<>(Arrays.asList(main.getConfig().getBmsroot()));
            if (ipfspath.toFile().exists() && !roots.contains(ipfspath.toString())) {
                roots.add(ipfspath.toString());
                main.getConfig().setBmsroot(roots.toArray(new String[roots.size()]));
            }
            
            download = new MusicDownloadProcessor(main.getConfig().getIpfsUrl(), (md5) -> {
                SongData[] s = main.getSongDatabase().getSongDatas(md5);
                String[] result = new String[s.length];
                for(int i = 0;i < result.length;i++) {
                    result[i] = s[i].getPath();
                }
                return result;
            });
            download.start(null);
        }

        crawler = new Crawler();
    }

    public void update() {
        if(download != null && download.isDownload()){
            main.downloadIpfsMessageRenderer(download.getMessage());
        }

        if(crawler != null && crawler.isDownloading()) {
            main.downloadIpfsMessageRenderer(crawler.getMessage());
        }
        
        if (download != null && download.getDownloadpath() != null) {
            main.updateSong(download.getDownloadpath());
            download.setDownloadpath(null);
        }
        if (crawler != null && crawler.getDownloadPathResult() != null) {
            main.updateSong(crawler.getDownloadPathResult());
            crawler.clearDownloadPathResult();
        }
    }
    
    public MusicDownloadProcessor getMusicDownloadProcessor() {
        return download;
    }
    
    public Crawler getCrawler() {
        return crawler;
    }
    
    public void dispose() {
        if (download != null) {
            download.dispose();
        }
        if (crawler != null) {
            crawler.dispose();
        }
    }
}
