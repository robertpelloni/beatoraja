with open("build.gradle", "r") as f:
    content = f.read()

# build.gradle has a version = "21" inside javafx block, which is correct for javafxplugin.
# The project version itself is not explicitly set in build.gradle right now. Let's set it!
# Also, we should ensure VERSION.md is included in the resources.

resources_block = """
    main {
        resources {
            srcDirs = ['src', '.']
            includes = ['VERSION.md']
        }
    }
"""

if "resources {" not in content:
    content = content.replace("sourceSets {\n    main {\n", "sourceSets {\n    main {\n        resources {\n            srcDirs = ['src', '.']\n            includes = ['VERSION.md', '**/*.fxml', '**/*.properties', '**/*.png', '**/*.jpg', '**/*.gif']\n        }\n")

with open("build.gradle", "w") as f:
    f.write(content)

print("build.gradle updated to include VERSION.md.")
