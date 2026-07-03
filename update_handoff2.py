with open("HANDOFF.md", "r") as f:
    content = f.read()

if "ArrowVortex" not in content:
    content += "\n## Submodules & Forward-Merge Pending\n"
    content += "- Scanned for pending changes across submodules (ArrowVortex and hymnmania). Note: ArrowVortex and hymnmania are not currently registered in `.gitmodules`. Future integrations will need to verify remote availability before proceeding.\n"

with open("HANDOFF.md", "w") as f:
    f.write(content)
