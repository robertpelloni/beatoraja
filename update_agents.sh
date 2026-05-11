echo "## Universal AI Instructions (for LLMs)" > LLM_INSTRUCTIONS.md
echo "1. Always start by reading VISION.md to understand the ultimate goal." >> LLM_INSTRUCTIONS.md
echo "2. Check CHANGELOG.md, VERSION.md, ROADMAP.md, and TODO.md for current status." >> LLM_INSTRUCTIONS.md
echo "3. Use 'git status' and 'git diff' frequently to understand the state." >> LLM_INSTRUCTIONS.md
echo "4. Document all findings comprehensively." >> LLM_INSTRUCTIONS.md
echo "5. Prioritize robust error handling and deep integration over superficial implementation." >> LLM_INSTRUCTIONS.md

cp LLM_INSTRUCTIONS.md AGENTS.md

cat << 'DOCS_EOF' > CLAUDE.md
# Claude Specific Instructions
Please refer to LLM_INSTRUCTIONS.md for universal guidelines.
- Emphasize deep code analysis and refactoring.
- Ensure thorough documentation of complex algorithms.
DOCS_EOF

cat << 'DOCS_EOF' > GEMINI.md
# Gemini Specific Instructions
Please refer to LLM_INSTRUCTIONS.md for universal guidelines.
- Focus on performance profiling and optimization.
- Highlight areas where caching or multithreading can improve frame rates.
DOCS_EOF

cat << 'DOCS_EOF' > GPT.md
# GPT Specific Instructions
Please refer to LLM_INSTRUCTIONS.md for universal guidelines.
- Provide comprehensive test cases and edge-case analysis.
- Focus on code readability and structure.
DOCS_EOF

cat << 'DOCS_EOF' > copilot-instructions.md
# Copilot Specific Instructions
Please refer to LLM_INSTRUCTIONS.md for universal guidelines.
- Offer immediate inline completions following the style of surrounding code.
- Prioritize concise, idiomatic Java constructs.
DOCS_EOF
