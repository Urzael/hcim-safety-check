# HCIM Safety Check

Hello! This is my first plugin. It's designed for people like me who don't have the best memory, and don't wanna always lookup if the activity they're doing will demote them to a regular ironmeme.

I have no coding knowledge, so I referenced plugins such as Fake Iron Icon, Quest Helper and Dink, as well as mejrs's map for region ID's along with Claude AI to make this.
Thank you to TheStonedTurtle for giving me the idea to reference other plugins when I asked him for help.

<img width="800" height="450" alt="hcimsc" src="https://github.com/user-attachments/assets/3d77b8a2-6b32-4ae1-a69d-50b7b741ca1c" />

The following areas are implemented, but may be inaccurate:

- Player Owned Homes
- Fight Pit (there's a single tile directly outside the pit, next to the fire barrier inside the waiting room that will register as safe.)
- Camelot Training Room
- Magic Training Arena bone zone
- Galvek replay (only counts as safe once Dragon Slayer II is completed)
- Glough MM2 replay (only counts as safe once Monkey Madness II is completed)
- Chambers of Xeric
- PVM Arena
- Zulrah (with Elite Diary resurrection) - The diary and daily resurrection checks are based on how Dink detects them. I do not have an account with the Elite Western Provinces diary completed, so I have not been able to test this one. If someone would be willing to test, feel free to open an issue on my github to let me know.
- Dream World during Lunar Diplomacy (only counts as safe while the quest is not completed)
- Koschei the Deathless during The Fremennik Trials (only counts as safe while the quest is not completed)

My HCIM RSN is 2d8, so feel free to laugh at me if you see me around, or when I die to a tree!

## Credits

- [Fake Iron Icon](https://github.com/thatgamerblue/runelite-plugins) by thatgamerblue: the chat icon replacement approach is adapted from this plugin.
- [Quest Helper](https://github.com/Zoinkwiz/quest-helper) by Zoinkwiz: the quest-completion check is adapted from it, and the Lunar Diplomacy dream and Fremennik Trials Koschei room zones come from its quest helpers.
- [Dink](https://github.com/pajlads/DinkPlugin) by pajlads: the Zulrah resurrection check and several region IDs were cross-checked against its safe-death detection.
- Region IDs were read from the [OSRS interactive map](https://mejrs.github.io/osrs) by mejrs, and in-game using the developer tools of [RuneLite](https://runelite.net/)
- [OSRS Wiki](https://oldschool.runescape.wiki/w/Ironman_Mode#Hardcore_Ironman) for a list of known safe areas.
