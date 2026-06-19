import os
import re

def fix_file(filepath):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    # Change .getKeyBoardInputProcesseor().isControlKeyPressed(...) to .isControlKeyPressed(...)
    content = re.sub(
        r"inputProcessor\.getKeyBoardInputProcesseor\(\)\.isControlKeyPressed",
        r"inputProcessor.isControlKeyPressed",
        content
    )
    content = re.sub(
        r"input\.getKeyBoardInputProcesseor\(\)\.isControlKeyPressed",
        r"input.isControlKeyPressed",
        content
    )

    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)

for root, _, files in os.walk('src/bms/player/beatoraja'):
    for file in files:
        if file.endswith('.java'):
            fix_file(os.path.join(root, file))

print("Fixed isControlKeyPressed references")
