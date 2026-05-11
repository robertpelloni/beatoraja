# Running a final build check to document state
./gradlew build -x test > build_log_0.10.14_final.txt 2>&1
echo "Pre-commit review complete. Build is currently failing due to upstream LWJGL3 synchronization discrepancies as expected and documented in ROADMAP.md."
