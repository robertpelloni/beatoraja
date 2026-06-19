import os
import re

def fix_file(filepath):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    # fix input.getKeyState(int) -> input.getNumberState()[int] OR actually we can just add getKeyState(int) to BMSPlayerInputProcessor!
    # Wait, BMSPlayerInputProcessor had getKeyState(int). Let's see if we can just re-add getKeyState(int) to it.

    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)
