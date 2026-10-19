package bms.player.beatoraja.result;

import bms.player.beatoraja.skin.*;

/**
 * リサルトスキン
 */
public class MusicResultSkin extends Skin {

	private int ranktime;

	/*public public MusicResultSkin(SkinHeader header) {*/
	public MusicResultSkin(bms.player.beatoraja.skin.SkinHeader header) { super(null, null); this.header = header;
		/*super(header);*/
	}

	public int getRankTime() {
		return ranktime;
	}

	public void setRankTime(int ranktime) {
		this.ranktime = ranktime;
	}

}
