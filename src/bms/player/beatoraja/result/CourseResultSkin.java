package bms.player.beatoraja.result;

import bms.player.beatoraja.skin.*;

public class CourseResultSkin extends Skin {

	private int ranktime;

	/*public public CourseResultSkin(SkinHeader header) {*/
	public CourseResultSkin(bms.player.beatoraja.skin.SkinHeader header) { super(null, null); this.header = header;
		/*super(header);*/
	}

	public int getRankTime() {
		return ranktime;
	}

	public void setRankTime(int ranktime) {
		this.ranktime = ranktime;
	}

}
