$ErrorActionPreference = "Stop"

# Define paths
$javaPath = "C:\Users\hyper\AppData\Local\UniGetUI\Chocolatey\bin\java.exe"
$gradleVersion = "8.5"
$gradleZip = "gradle-$gradleVersion-bin.zip"
$gradleUrl = "https://services.gradle.org/distributions/$gradleZip"
$gradleHome = "$PWD\gradle-$gradleVersion"
$gradleBin = "$gradleHome\bin\gradle.bat"

Write-Host "Using Java: $javaPath"
& $javaPath -version

# Download Gradle if not exists
if (-not (Test-Path $gradleZip)) {
    Write-Host "Downloading Gradle $gradleVersion..."
    Invoke-WebRequest -Uri $gradleUrl -OutFile $gradleZip
}

# Extract Gradle if not exists
if (-not (Test-Path $gradleHome)) {
    Write-Host "Extracting Gradle..."
    Expand-Archive -Path $gradleZip -DestinationPath $PWD -Force
}

# Verify Gradle
if (Test-Path $gradleBin) {
    Write-Host "Gradle found at $gradleBin"
    
    # Set JAVA_HOME for this session to the Java 21 path (parent of bin)
    $env:JAVA_HOME = (Get-Item $javaPath).Directory.Parent.FullName
    Write-Host "Set JAVA_HOME to $env:JAVA_HOME"

    # Run gradle wrapper
    Write-Host "Generating Gradle Wrapper..."
    & $gradleBin wrapper --gradle-version $gradleVersion
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Gradle Wrapper generated successfully."
        
        # Verify wrapper
        if (Test-Path "gradlew.bat") {
            Write-Host "Testing ./gradlew.bat..."
            .\gradlew.bat --version
        }
    } else {
        Write-Error "Failed to generate Gradle Wrapper."
    }
} else {
    Write-Error "Gradle binary not found at $gradleBin"
}
