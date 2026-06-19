import os
import re

def fix_file(filepath):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    # main.getIRStatus() arrays no longer exist. MainController now has getIRConnection() returning IRConnection.
    # We will stub these out for now since IRStatus logic was refactored.
    # IRStatus[] ir = main.getIRStatus() -> IRConnection ir = main.getIRConnection();

    # In MusicResult.java:
    content = content.replace("main.getIRStatus().length > 0", "main.getIRConnection() != null")

    # In BarManager.java:
    content = content.replace("select.main.getIRStatus().length > 0", "select.main.getIRConnection() != null")
    content = content.replace("select.main.getIRStatus()[0].connection", "select.main.getIRConnection()")

    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)

for root, _, files in os.walk('src/bms/player/beatoraja'):
    for file in files:
        if file.endswith('.java'):
            fix_file(os.path.join(root, file))

print("Fixed IRStatus")
