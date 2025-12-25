$baseUrl = "https://repo1.maven.org/maven2/org/openjfx"
$version = "21.0.1"
$modules = @("javafx-base", "javafx-controls", "javafx-fxml", "javafx-graphics", "javafx-media", "javafx-swing", "javafx-web")
$dest = "lib"

foreach ($module in $modules) {
    $jar = "$module-$version.jar"
    $url = "$baseUrl/$module/$version/$jar"
    $output = "$dest/$jar"
    Write-Host "Downloading $jar..."
    Invoke-WebRequest -Uri $url -OutFile $output

    $winJar = "$module-$version-win.jar"
    $winUrl = "$baseUrl/$module/$version/$winJar"
    $winOutput = "$dest/$winJar"
    Write-Host "Downloading $winJar..."
    try {
        Invoke-WebRequest -Uri $winUrl -OutFile $winOutput
    } catch {
        Write-Host "Failed to download $winJar (might not exist)"
    }
}
