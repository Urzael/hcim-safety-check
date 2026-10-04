package com.hcimsafetycheck;
import java.util.Set;
import java.util.function.Predicate;
import net.runelite.api.Client;
import net.runelite.api.Quest;
import net.runelite.api.ScriptID;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.gameval.VarbitID;
public enum SafeArea {
	BARBARIAN_ASSAULT("Barbarian Assault", Set.of(7508, 7509), true),
	CAMELOT_TRAINING_ROOM("Camelot Training Room", Set.of(11062), false, new int[]{2752, 2764, 3502, 3513, 2}),
	CASTLE_WARS("Castle Wars", Set.of(9520, 9620)),   // 9620 = tunnels?
	CHAMBERS_OF_XERIC("Chambers of Xeric",
			Set.of(12889, 13145, 13401,                                  // y = 89 (Great Olm and upper rooms)
					13141, 13397, 13140, 13396, 13139, 13395,             // y = 85, 84, 83
					13138, 13394, 13137, 13393, 13136),                   // y = 82, 81, 80
			true),
	CLAN_WARS("Clan Wars",
			Set.of(12623, 12622, 12621,              // Turrets, Clan Cup Arena
					13135, 13134, 13133, 13390,       // Wasteland, Plateau, Ethereal
					13647, 13646, 13645, 13644,       // Forsaken Quarry, Sylvan Glade, Ghastly Swamp, Northleach Quell
					13131, 13130, 13387, 13386,       // Free-for-all
					13643, 13642, 13641,              // Lumbridge Castle, Classic
					13898, 14154, 13899, 13900, 14155, 14156)),   // Falador Park (spans several regions)
	// Zones from Quest Helper's LunarDiplomacy
	DREAM_WORLD("Dream World (Lunar Diplomacy)", Set.of(6991, 7247), true, new int[]{1730, 1840, 5056, 5115, 2},
			client -> !isQuestFinished(client, Quest.LUNAR_DIPLOMACY)),
	EMIRS_ARENA("Emir's Arena", Set.of(13362)),
	FISHING_TRAWLER("Fishing Trawler", Set.of(7499, 7755, 8011)),
	// Zone from Quest Helper's TheFremennikTrials
	FREMENNIK_TRIALS("Fremennik Trials (Koschei)", Set.of(10653), false, new int[]{2641, 2672, 10064, 10099, 2},
			client -> !isQuestFinished(client, Quest.THE_FREMENNIK_TRIALS)),
	INFERNO("Inferno", Set.of(9043), true),
	LAST_MAN_STANDING("Last Man Standing",
			Set.of(13918, 13919, 13920, 14174, 14175, 14176, 14430, 14431, 14432,   // Wild Varrock
					13658, 13659, 13914, 13915, 13660, 13916)),                                     // Deserted Island
	// Bone zone (graveyard) only
	MAGIC_TRAINING_ARENA("Magic Training Arena (Graveyard)", Set.of(13462), false, new int[]{3344, 3383, 9620, 9658, 1}),
	NIGHTMARE_ZONE("Nightmare Zone", Set.of(9033), true),
	PEST_CONTROL("Pest Control", Set.of(10536)),
	PLAYER_OWNED_HOUSE("Player-owned House", Set.of(7513, 7769, 7257, 7514, 7770, 8025, 8026), true),
	PVM_ARENA("PvM Arena", Set.of(6729)),
	SOUL_WARS("Soul Wars", Set.of(8493, 8748, 8749, 9005, 7773, 8029, 8285), true),
	FIGHT_CAVE("TzHaar Fight Cave", Set.of(9551), true),
	// Bounds exclude the waiting area, which shares region 9552: {minX, maxX, minY, maxY}
	FIGHT_PIT("TzHaar Fight Pit", Set.of(9552), false, new int[]{2371, 2425, 5124, 5169}),
	GALVEK_REPLAY("Galvek Replay", Set.of(6486, 6487, 6488, 6742, 6743, 6744, 6745, 6489), true, null,
			client -> isQuestFinished(client, Quest.DRAGON_SLAYER_II)),
	GLOUGH_REPLAY("Glough Replay", Set.of(8280, 8536), true, null,
			client -> isQuestFinished(client, Quest.MONKEY_MADNESS_II)),
	ROGUES_DEN_MAZE("Rogues' Den (Maze)", Set.of(11854, 11855, 12110, 12111)),
	// Resurrection-used varbit 4565 and the region IDs match the Dink plugin (pajlads/DinkPlugin)
	ZULRAH("Zulrah", Set.of(9007, 9008), true, null,
			client -> client.getVarbitValue(VarbitID.WESTERN_ELITE_REWARD) > 0
					&& client.getVarbitValue(4565) == 0);
	// PvM Arena may be wrong, I don't have an account with access to verify.
	private final String displayName;
	private final Set<Integer> regionIds;
	private final boolean instanceOnly;
	private final int[] bounds;                  // {minX, maxX, minY, maxY, optional plane}, or null
	private final Predicate<Client> condition;   // extra requirement, or null
	SafeArea(String displayName, Set<Integer> regionIds) {
		this(displayName, regionIds, false, null, null);
	}
	SafeArea(String displayName, Set<Integer> regionIds, boolean instanceOnly) {
		this(displayName, regionIds, instanceOnly, null, null);
	}
	SafeArea(String displayName, Set<Integer> regionIds, boolean instanceOnly, int[] bounds) {
		this(displayName, regionIds, instanceOnly, bounds, null);
	}
	SafeArea(String displayName, Set<Integer> regionIds, boolean instanceOnly,
			 int[] bounds, Predicate<Client> condition) {
		this.displayName = displayName;
		this.regionIds = regionIds;
		this.instanceOnly = instanceOnly;
		this.bounds = bounds;
		this.condition = condition;
	}
	public boolean matches(WorldPoint wp, boolean inInstance, Client client) {
		if (!regionIds.contains(wp.getRegionID()) || (instanceOnly && !inInstance)) {
			return false;
		}
		if (bounds != null) {
			if (!(wp.getX() >= bounds[0] && wp.getX() <= bounds[1]
					&& wp.getY() >= bounds[2] && wp.getY() <= bounds[3])) {
				return false;
			}
			// optional 5th value: required plane
			if (bounds.length > 4 && wp.getPlane() != bounds[4]) {
				return false;
			}
		}
		// checked last, so any lookup only runs when everything else already matches
		return condition == null || condition.test(client);
	}

	// Quest-state check adapted from Quest Helper (QuestHelperQuest.java)
	// Copyright (c) 2020, Zoinkwiz. BSD 2-Clause License.
	// https://github.com/Zoinkwiz/quest-helper
	private static boolean isQuestFinished(Client client, Quest quest) {
		client.runScript(ScriptID.QUEST_STATUS_GET, quest.getId());
		return client.getIntStack()[0] == 2;
	}
	@Override
	public String toString() {
		return displayName;
	}
	public static boolean isSafe(WorldPoint wp, boolean inInstance, Client client) {
		for (SafeArea area : values()) {
			if (area.matches(wp, inInstance, client)) {
				return true;
			}
		}
		return false;
	}
}