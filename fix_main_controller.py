import re

with open("src/bms/player/beatoraja/MainController.java", "r") as f:
    content = f.read()

# Let's inspect how VERSION is initialized in MainController.java
