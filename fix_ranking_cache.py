import re

def fix_file(filepath):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    # RankingData songrank = main.getRankingDataCache().get(...)
    content = content.replace("RankingData songrank = main.getRankingDataCache().get(resource.getSongdata(), main.getPlayerConfig().getLnmode());", "RankingData songrank = null; /*")
    content = content.replace("main.getRankingDataCache().put(resource.getSongdata(), main.getPlayerConfig().getLnmode(), songrank);", "*/")

    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)

fix_file('src/bms/player/beatoraja/result/MusicResult.java')
