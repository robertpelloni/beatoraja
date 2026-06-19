package bms.player.beatoraja.result;

import bms.player.beatoraja.skin.*;

public class CourseResultSkin extends Skin {

	private int ranktime;

	public CourseResultSkin(SkinHeader header) {
		super(bms.player.beatoraja.Resolution.FULLHD, bms.player.beatoraja.Resolution.FULLHD);
	}

	public int getRankTime() {
		return ranktime;
	}

	public void setRankTime(int ranktime) {
		this.ranktime = ranktime;
	}

}
