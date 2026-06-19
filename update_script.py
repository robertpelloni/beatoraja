import os
import re

def process_file(filepath, callback):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()
    new_content = callback(content)
    if content != new_content:
        with open(filepath, 'w', encoding='utf-8') as f:
            f.write(new_content)

def handle_controlkeys(content):
    content = re.sub(r'import bms\.player\.beatoraja\.input\.ControlKeys;', r'import bms.player.beatoraja.input.KeyBoardInputProcesseor.ControlKeys;', content)
    content = re.sub(r'ControlKeys\.', r'KeyBoardInputProcesseor.ControlKeys.', content)
    content = re.sub(r'KeyBoardInputProcesseor\.KeyBoardInputProcesseor\.ControlKeys', r'KeyBoardInputProcesseor.ControlKeys', content)
    return content

for root, dirs, files in os.walk('src/'):
    for file in files:
        if file.endswith('.java'):
            process_file(os.path.join(root, file), handle_controlkeys)
