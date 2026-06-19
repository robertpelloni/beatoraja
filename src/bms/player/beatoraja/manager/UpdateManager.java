package bms.player.beatoraja.manager;

import java.util.logging.Logger;

import com.badlogic.gdx.graphics.Color;

import bms.player.beatoraja.MainController;
import bms.player.beatoraja.MessageRenderer.Message;
import bms.player.beatoraja.select.bar.TableBar;
import bms.player.beatoraja.TableData;

public class UpdateManager {

    private final MainController main;
    private UpdateThread updateSong;
    private UpdateThread downloadIpfs;

    public UpdateManager(MainController main) {
        this.main = main;
    }

    public void updateSong(String path) {
        if (updateSong == null || !updateSong.isAlive()) {
            updateSong = new SongUpdateThread(path);
            updateSong.start();
        } else {
            Logger.getGlobal().warning("楽曲更新中のため、更新要求は取り消されました");
        }
    }

    public void updateTable(TableBar reader) {
        if (updateSong == null || !updateSong.isAlive()) {
            updateSong = new TableUpdateThread(reader);
            updateSong.start();
        } else {
            Logger.getGlobal().warning("楽曲更新中のため、更新要求は取り消されました");
        }
    }

    public void downloadIpfsMessageRenderer(String message) {
        if (downloadIpfs == null || !downloadIpfs.isAlive()) {
            downloadIpfs = new DownloadMessageThread(message);
            downloadIpfs.start();
        }
    }
    
    public void poll() {
        if (updateSong != null && !updateSong.isAlive()) {
            /* updateBar(); */
            updateSong = null;
        }
    }

    abstract class UpdateThread extends Thread {

        protected String message;

        public UpdateThread(String message) {
            this.message = message;
        }
    }

    /**
     * 楽曲データベース更新用スレッド
     */
    class SongUpdateThread extends UpdateThread {

        private final String path;

        public SongUpdateThread(String path) {
            super("updating folder : " + (path == null ? "ALL" : path));
            this.path = path;
        }

        public void run() {
            /* main.getMessageRenderer().addMessage(...) */
            main.getSongDatabase().updateSongDatas(path, main.getConfig().getBmsroot(), false, main.getInfoDatabase());

        }
    }

    /**
     * 難易度表更新用スレッド
     */
    class TableUpdateThread extends UpdateThread {

        private final TableBar accessor;

        public TableUpdateThread(TableBar bar) {
            super("updating table : " + bar.getAccessor().name);
            accessor = bar;
        }

        public void run() {
            /* main.getMessageRenderer().addMessage(...) */
            TableData td = accessor.getAccessor().read();
            if (td != null) {
                accessor.getAccessor().write(td);
                accessor.setTableData(td);
            }

        }
    }

    class DownloadMessageThread extends UpdateThread {
        public DownloadMessageThread(String message) {
            super(message);
        }

        public void run() {
            /* main.getMessageRenderer().addMessage(...) */
            while (main.getMusicDownloadProcessor() != null && main.getMusicDownloadProcessor().isDownload() && main.getMusicDownloadProcessor().getMessage() != null) {

                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
