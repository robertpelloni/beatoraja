$GradleVersion = "8.5"
$GradleUrl = "https://services.gradle.org/distributions/gradle-$GradleVersion-bin.zip"
$GradleZip = "gradle-$GradleVersion-bin.zip"
$GradleDir = "gradle-$GradleVersion"

$env:JAVA_HOME = "C:\Program Files\Java\jdk-25"

Write-Host "Downloading Gradle $GradleVersion..."
Invoke-WebRequest -Uri $GradleUrl -OutFile $GradleZip

Write-Host "Extracting Gradle..."
Expand-Archive -Path $GradleZip -DestinationPath . -Force

Write-Host "Generating Gradle Wrapper..."
& ".\$GradleDir\bin\gradle" wrapper

Write-Host "Cleanup..."
Remove-Item $GradleZip
Remove-Item $GradleDir -Recurse -Force

Write-Host "Done! You can now use .\gradlew build"
