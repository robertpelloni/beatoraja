package bms.player.beatoraja.pattern;

import bms.model.*;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import com.badlogic.gdx.utils.IntArray;

/**
 * レーン単位でノーツを入れ替えるオプション
 *
 * @author exch
 */
public abstract class LaneShuffleModifier extends PatternModifier {

	/**
	 * 各レーンの移動先
	 */
	private int[] random;
	/**
	 * 変更レーンにスクラッチレーンを含むか
	 */
	public final boolean isScratchLaneModify;
	
	public final boolean showShufflePattern;

	public LaneShuffleModifier(int player, boolean isScratchLaneModify, boolean showShufflePattern) {
		super(player);
		this.isScratchLaneModify = isScratchLaneModify;
		this.showShufflePattern = showShufflePattern;
	}
	
	protected abstract int[] makeRandom(int[] keys, BMSModel model);

	@Override
	public void modify(BMSModel model) {
		Mode mode = model.getMode();
		final int[] keys = getKeys(mode, player, isScratchLaneModify);
		if(keys.length == 0) {
			return;
		}
		random = makeRandom(keys, model);
		final int lanes = model.getMode().key;
		final Note[] notes = new Note[lanes];
		final Note[] hnotes = new Note[lanes];
		final boolean[] clone = new boolean[lanes];
		TimeLine[] timelines = model.getAllTimeLines();
		for (int index = 0; index < timelines.length; index++) {
			final TimeLine tl = timelines[index];
			if (tl.existNote() || tl.existHiddenNote()) {
				for (int i = 0; i < lanes; i++) {
					notes[i] = tl.getNote(i);
					hnotes[i] = tl.getHiddenNote(i);
					clone[i] = false;
				}
				for (int i = 0; i < lanes; i++) {
					final int mod = i < random.length ? random[i] : i;
					if (clone[mod]) {
						if (notes[mod] != null) {
							if (notes[mod] instanceof LongNote && ((LongNote) notes[mod]).isEnd()) {
								for (int j = index - 1; j >= 0; j--) {
									if (((LongNote) notes[mod]).getPair().getSection() == timelines[j].getSection()) {
										LongNote ln = (LongNote) timelines[j].getNote(i);
										tl.setNote(i, ln.getPair());
//										System.out.println(ln.toString() + " : " + ln.getPair().toString() + " == "
//												+ ((LongNote) notes[mod]).getPair().toString() + " : "
//												+ notes[mod].toString());
										break;
									}
								}
							} else {
								tl.setNote(i, (Note) notes[mod].clone());
							}
						} else {
							tl.setNote(i, null);
						}
						if (hnotes[mod] != null) {
							tl.setHiddenNote(i, (Note) hnotes[mod].clone());
						} else {
							tl.setHiddenNote(i, null);
						}
					} else {
						tl.setNote(i, notes[mod]);
						tl.setHiddenNote(i, hnotes[mod]);
						clone[mod] = true;
					}
				}
			}
		}
	}

	public boolean isToDisplay() {
		return showShufflePattern;
	}

	public int[] getRandomPattern(Mode mode) {
		int keys = mode.key / mode.player;
		int[] repr = new int[keys];
		if(showShufflePattern && random != null) {
			// Ensure random array is large enough
			if (random.length >= keys * (player + 1)) {
				if (mode.scratchKey.length > 0 && !isScratchLaneModify) { // BEAT-*K
					System.arraycopy(random, keys * player, repr, 0, keys - 1);
					// For scratch key, we generally don't shuffle it if !isScratchLaneModify
					// But we should check if scratch key is valid
					if (player < mode.scratchKey.length) {
						repr[keys - 1] = mode.scratchKey[player];
					}
				} else {
					System.arraycopy(random, keys * player, repr, 0, keys);
				}
			}
		}
		return repr;
	}
	
	public static class LaneMirrorShuffleModifier extends LaneShuffleModifier {

		public LaneMirrorShuffleModifier(int player, boolean isScratchLaneModify) {
			super(player, isScratchLaneModify, false);
			setAssistLevel(isScratchLaneModify ? AssistLevel.LIGHT_ASSIST : AssistLevel.NONE);
		}
		
		protected int[] makeRandom(int[] keys, BMSModel model) {
			int[] result = IntStream.range(0, model.getMode().key).toArray();
			for (int lane = 0; lane < keys.length; lane++) {
				result[keys[lane]] = keys[keys.length - 1 - lane];
			}
			return result;
		}	
	}

	public static class LaneMRanModifier extends LaneShuffleModifier {

		private int[] basePattern; // The pattern from the other side (e.g., 1P's random)

		public LaneMRanModifier(int player, int[] basePattern, boolean isScratchLaneModify) {
			super(player, isScratchLaneModify, true);
			this.basePattern = basePattern;
			setAssistLevel(isScratchLaneModify ? AssistLevel.LIGHT_ASSIST : AssistLevel.NONE);
		}

		protected int[] makeRandom(int[] keys, BMSModel model) {
			int[] result = IntStream.range(0, model.getMode().key).toArray();

			// We expect basePattern to be the full lane array from the other player (which might have been randomized)
			// But PatternModifier usually generates a mapping.
			// If basePattern represents the mapping for the other side, e.g. basePattern[src] = dest
			// M-RAN means: The player gets the MIRRORED version of the OTHER player's Random.
			// So if 1P has Random: [2, 0, 1, 3] (Lane 0->2, 1->0...)
			// 2P (M-RAN) should be that pattern mirrored.
			// Mirroring operation: result[keys[i]] = keys[keys.length - 1 - (mapped_index_of_i_in_base)] ?
			// Simpler: Take the base pattern, apply Mirror to it.

			// However, PatternModifier's makeRandom returns an array where arr[original_lane] = new_lane.
			// basePattern is assumed to be in the same format.
			// Let's mirror the *destination* lanes of the base pattern.

			// keys array contains the lane indices for THIS player.
			// The basePattern likely corresponds to the OTHER player's keys.
			// We need to map 1P keys to 2P keys (or vice versa) to apply the logic.
			// Assuming keys are 0-6 for 1P, 7-13 for 2P (in 14K mode).

			// If this modifier is for 2P, keys are [7,8,9,10,11,12,13].
			// basePattern is from 1P: [0,1,2,3,4,5,6] mapped to something.

			// But wait, PatternModifier logic in BMSPlayer applies modifiers independently.
			// We need to inject the 1P result into 2P modifier.

			// If we assume standard 7K Mirror logic: result[keys[i]] = keys[len-1-i].
			// Random logic: result[keys[i]] = keys[shuffled_i].

			// M-RAN logic:
			// 1P: Randomly shuffled. Let's say lane 0 -> 2, 1 -> 5, etc.
			// 2P: Mirror of 1P. So 2P lane 0 corresponds to 1P lane 0's mirrored position?
			// Actually M-RAN description: "ON side gets the mirrored version of the same random pattern".
			// So if 1P Random map is: 1->3, 2->1, 3->2...
			// 2P M-RAN map should be: 1->(Mirror(3)), 2->(Mirror(1))... ?
			// No, usually it means the resulting chart structure.
			// Random shuffles lanes.
			// 1P: Lane 1 has "Sound A". Random moves it to Key 3.
			// 2P: Lane 1 (same sound/chart if DP Battle/DBM) moves to Key (Mirror(3)).

			// If DP, charts are different.
			// "M-RAN is only applicable when two people are playing at the same time" (or DP Battle?).
			// "they will receive the same random, but mirrored on the 2p side."

			// If basePattern is the shuffle map of the *partner*:
			// partner_map[i] = dest_lane_index_relative_to_partner
			// my_map[i] = mirror(dest_lane_index_relative_to_partner)_relative_to_me

			// Let's implement relative mapping.
			int len = keys.length;
			if (basePattern == null || basePattern.length != len) {
				// Fallback to Mirror if no base pattern or mismatch
				for (int lane = 0; lane < len; lane++) {
					result[keys[lane]] = keys[len - 1 - lane];
				}
				return result;
			}

			// Mirror the destination index
			// dest_index = basePattern[i] (which is 0..len-1)
			// mirrored_dest = len - 1 - dest_index
			for (int i = 0; i < len; i++) {
				// We assume basePattern is just the relative shuffle indices (0 to len-1)
				// If basePattern are absolute lane numbers, we need to normalize.
				// Let's assume basePattern passed in is normalized 0..len-1.
				int dest = basePattern[i];
				int mirroredDest = len - 1 - dest;
				result[keys[i]] = keys[mirroredDest];
			}
			return result;
		}
	}

	public static class LaneRotateShuffleModifier extends LaneShuffleModifier {

		public LaneRotateShuffleModifier(int player, boolean isScratchLaneModify) {
			super(player, isScratchLaneModify, true);
			setAssistLevel(isScratchLaneModify ? AssistLevel.LIGHT_ASSIST : AssistLevel.NONE);
		}
		
		protected int[] makeRandom(int[] keys, BMSModel model) {
			java.util.Random rand = new java.util.Random(getSeed());
			final boolean inc = (rand.nextInt(2) == 1);
			final int start = rand.nextInt(keys.length - 1) + (inc ? 1 : 0);
			int[] result = IntStream.range(0, model.getMode().key).toArray();
			for (int lane = 0, rlane = start; lane < keys.length; lane++) {
				result[keys[lane]] = keys[rlane];
				rlane = inc ? (rlane + 1) % keys.length : (rlane + keys.length - 1) % keys.length;
			}
			return result;
		}	
	}

	public static class LaneRandomShuffleModifier extends LaneShuffleModifier {

		public LaneRandomShuffleModifier(int player, boolean isScratchLaneModify) {
			super(player, isScratchLaneModify, true);
			setAssistLevel(isScratchLaneModify ? AssistLevel.LIGHT_ASSIST : AssistLevel.NONE);
		}
		
		protected int[] makeRandom(int[] keys, BMSModel model) {
			java.util.Random rand = new java.util.Random(getSeed());
			IntArray l = new IntArray(keys);
			int[] result = IntStream.range(0, model.getMode().key).toArray();
			for (int lane = 0; lane < keys.length; lane++) {
				int r = rand.nextInt(l.size);
				result[keys[lane]] = l.get(r);
				l.removeIndex(r);
			}
			return result;
		}	
	}

	public static class PlayerFlipModifier extends LaneShuffleModifier {

		public PlayerFlipModifier() {
			super(0, true, false);
			setAssistLevel(AssistLevel.NONE);
		}
		
		protected int[] makeRandom(int[] keys, BMSModel model) {
			int[] result = IntStream.range(0, model.getMode().key).toArray();
			if (model.getMode().player == 2) {
				for (int i = 0; i < result.length; i++) {
					result[i] = (i + result.length / 2) % result.length;
				}
			}
			return result;
		}	
	}

	public static class PlayerBattleModifier extends LaneShuffleModifier {

		public PlayerBattleModifier() {
			super(0, true, false);
			setAssistLevel(AssistLevel.ASSIST);
		}
		
		protected int[] makeRandom(int[] keys, BMSModel model) {
			if (model.getMode().player == 1) {
				return new int[0];
			} else {
				int[] result = new int[keys.length * 2];
				System.arraycopy(keys, 0, result, 0, keys.length);
				System.arraycopy(keys, 0, result, keys.length, keys.length);
				setAssistLevel(AssistLevel.ASSIST);
				return result;
			}
		}	
	}

	public static class LaneCrossShuffleModifier extends LaneShuffleModifier {

		public LaneCrossShuffleModifier(int player, boolean isScratchLaneModify) {
			super(player, isScratchLaneModify, true);
			setAssistLevel(AssistLevel.LIGHT_ASSIST);
		}
		
		protected int[] makeRandom(int[] keys, BMSModel model) {
			int[] result = IntStream.range(0, model.getMode().key).toArray();
			for (int i = 0; i < keys.length / 2 - 1; i += 2) {
				result[keys[i]] = keys[i + 1];
				result[keys[i + 1]] = keys[i];
				result[keys[keys.length - i - 1]] = keys[keys.length - i - 2];
				result[keys[keys.length - i - 2]] = keys[keys.length - i - 1];
			}
			return result;
		}	
	}

	public static class LanePlayableRandomShuffleModifier extends LaneShuffleModifier {

		public LanePlayableRandomShuffleModifier(int player, boolean isScratchLaneModify) {
			super(player, isScratchLaneModify, true);
			setAssistLevel(AssistLevel.LIGHT_ASSIST);
		}
		
		protected int[] makeRandom(int[] keys, BMSModel model) {
			// 無理押しが来ないようにLaneShuffleをかける(ただし正規鏡を除く)。無理押しが来ない譜面が存在しない場合は正規か鏡でランダム
			Mode mode = model.getMode();
			int lanes = mode.key;
			int[] ln = new int[lanes];
			int[] endLnNoteTime = new int[lanes];
			int max = 0;
			for (int key : keys) {
				max = Math.max(max, key);
			}
			boolean isImpossible = false; //7個押し以上が存在するかどうか
			Set<Integer> originalPatternList = new HashSet<>(); //3個押し以上の同時押しパターンのセット
			Arrays.fill(ln, -1);
			Arrays.fill(endLnNoteTime, -1);

			//3個押し以上の同時押しパターンのリストを作る
			for (TimeLine tl : model.getAllTimeLines()) {
				if (tl.existNote()) {
					//LN
					for (int i = 0; i < lanes; i++) {
						Note n = tl.getNote(i);
						if (n instanceof LongNote ln2) {
							if (ln2.isEnd() && tl.getTime() == endLnNoteTime[i]) {
								ln[i] = -1;
								endLnNoteTime[i] = -1;
							} else {
								ln[i] = i;
								if (!ln2.isEnd()) {
									endLnNoteTime[i] = ln2.getPair().getTime();
								}
							}
						}
					}
					//通常ノート
					List<Integer> noteLane = new ArrayList<>(keys.length);
					for (int i = 0; i < lanes; i++) {
						Note n = tl.getNote(i);
						if (n != null && n instanceof NormalNote || ln[i] != -1) {
							noteLane.add(i);
						}
					}
					//7個押し以上が一つでも存在すれば無理押しが来ない譜面は存在しない
					if (noteLane.size() >= 7) {
						isImpossible = true;
						break;
					} else if (noteLane.size() >= 3) {
						int pattern = 0;
						for (Integer i : noteLane) {
							pattern += (int) Math.pow(2, i);
						}
						originalPatternList.add(pattern);
					}
				}
			}

			List<List<Integer>> kouhoPatternList = new ArrayList<>(); //無理押しが来ない譜面のリスト
			if (!isImpossible) {
				kouhoPatternList = searchForNoMurioshiLaneCombinations(originalPatternList, keys);
			}

			Logger.getGlobal().info("無理押し無し譜面数 : "+(kouhoPatternList.size()));

			int[] result = new int[9];
			if (kouhoPatternList.size() > 0) {
				int r = (int) (Math.random() * kouhoPatternList.size());
				for (int i = 0; i < 9; i++) {
					result[kouhoPatternList.get(r).get(i)] = i;
				}
			//無理押しが来ない譜面が存在しない場合は正規か鏡でランダム
			} else {
				int mirror = (int) (Math.random() * 2);
				for (int i = 0; i < 9; i++) {
					result[i] = mirror == 0 ? i : 8 - i;
				}
			}
			return result;
		}

		private List<List<Integer>> searchForNoMurioshiLaneCombinations(Set<Integer> originalPatternList, int[] keys) {
			List<List<Integer>> noMurioshiLaneCombinations = new ArrayList<>(); // 無理押しが来ない譜面のリスト
			List<Integer> tempPattern = new ArrayList<>(keys.length);
			int[] indexes = new int[9];
			int[] laneNumbers = new int[9];
			for (int i = 0; i < 9; i++) {
				laneNumbers[i] = i;
				indexes[i] = 0;
			}

			List<List<Integer>> murioshiChords = Arrays.asList(
					Arrays.asList(1, 4, 7),
					Arrays.asList(1, 4, 8),
					Arrays.asList(1, 4, 9),
					Arrays.asList(1, 5, 8),
					Arrays.asList(1, 5, 9),
					Arrays.asList(1, 6, 9),
					Arrays.asList(2, 5, 8),
					Arrays.asList(2, 5, 9),
					Arrays.asList(2, 6, 9),
					Arrays.asList(3, 6, 9)
			);

			int i = 0;
			while (i < 9) {
				if (indexes[i] < i) {
					swap(laneNumbers, i % 2 == 0 ? 0 : indexes[i], i);

					boolean murioshiFlag = false;
					for (Integer pattern : originalPatternList) {
						tempPattern.clear();
						for (int j = 0; j < 9; j++) {
							if (((int) (pattern / Math.pow(2, j)) % 2) == 1) {
								tempPattern.add(laneNumbers[j] + 1);
							}
						}

						murioshiFlag = murioshiChords.stream().anyMatch(tempPattern::containsAll);
						if (murioshiFlag) {
							break;
						}
					}
					if (!murioshiFlag) {
						List<Integer> randomCombination = new ArrayList<>();
						for (int j = 0; j < 9; j++) {
							randomCombination.add(laneNumbers[j]);
						}
						noMurioshiLaneCombinations.add(randomCombination);
					}

					indexes[i]++;
					i = 0;
				} else {
					indexes[i] = 0;
					i++;
				}
			}

			noMurioshiLaneCombinations.remove(Arrays.asList(8, 7, 6, 5, 4, 3, 2, 1, 0));
			return noMurioshiLaneCombinations;
		}
		
		private void swap(int[] input, int a, int b) {
			int tmp = input[a];
			input[a] = input[b];
			input[b] = tmp;
		}
	}
}

