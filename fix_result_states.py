import os
import re

def fix_file(filepath):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    content = content.replace("MainStateType.MUSICSELECT", "MainController.STATE_SELECTMUSIC")
    content = content.replace("MainStateType.PLAY", "MainController.STATE_PLAYBMS")
    content = content.replace("MainStateType.COURSERESULT", "MainController.STATE_COURSERESULT")
    content = content.replace("MainStateType.EXIT", "MainController.STATE_EXIT")

    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)

for root, _, files in os.walk('src/bms/player/beatoraja/result'):
    for file in files:
        if file.endswith('.java'):
            fix_file(os.path.join(root, file))

print("Fixed state references in result/")
