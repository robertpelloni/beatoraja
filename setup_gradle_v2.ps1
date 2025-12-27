$GradleVersion = "8.5"
$GradleUrl = "https://services.gradle.org/distributions/gradle-$GradleVersion-bin.zip"
$GradleZip = "gradle-$GradleVersion-bin.zip"
$GradleDir = "gradle-$GradleVersion"
$env:JAVA_HOME = "C:\Program Files\Java\jdk-25"

if (-not (Test-Path $GradleZip)) {
    Write-Host "Downloading Gradle $GradleVersion..."
    Invoke-WebRequest -Uri $GradleUrl -OutFile $GradleZip
}

if (-not (Test-Path "$GradleDir\bin\gradle.bat")) {
    Write-Host "Extracting Gradle..."
    Expand-Archive -Path $GradleZip -DestinationPath . -Force
}

Write-Host "Generating Gradle Wrapper..."
& ".\$GradleDir\bin\gradle.bat" wrapper

if (Test-Path "gradlew.bat") {
    Write-Host "Wrapper generated successfully."
    # Optional: Cleanup
    # Remove-Item $GradleZip
    # Remove-Item $GradleDir -Recurse -Force
} else {
    Write-Host "Wrapper generation failed."
}
