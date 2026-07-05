import re

with open('src/bms/player/beatoraja/input/BMSPlayerInputProcessor.java', 'r', encoding='utf-8') as f:
    content = f.read()

# Add getKeyState(int)
content = re.sub(
    r"public boolean\[\] getNumberState\(\) \{",
    r"public boolean getKeyState(int key) { return getNumberState()[key]; }\n\n\tpublic boolean[] getNumberState() {",
    content
)

# Fix missing resetKeyChangedTime
content = re.sub(
    r"public long\[\] getTime\(\) \{",
    r"public boolean resetKeyChangedTime(int key) { long[] t = getTime(); if(t[key] == Long.MIN_VALUE) return False; t[key] = Long.MIN_VALUE; return True; }\n\n\tpublic long[] getTime() {",
    content
)

with open('src/bms/player/beatoraja/input/BMSPlayerInputProcessor.java', 'w', encoding='utf-8') as f:
    f.write(content)
