# PowerShell script to compile and run Observer Pattern Demo
# Run from project root directory
# Usage: .\run-observer-demo.ps1

Write-Host "=== Observer Pattern Demo Runner ===" -ForegroundColor Cyan
Write-Host ""

# Set paths
$observerPath = "src\main\java\com\nvminh162\designpattern\observer"
$javaSourcePath = "src\main\java"

# Check if Java is available
$javaVersion = java -version 2>&1
if ($LASTEXITCODE -ne 0) {
    Write-Host "Error: Java is not installed or not in PATH" -ForegroundColor Red
    exit 1
}

Write-Host "Step 1: Compiling Observer Pattern classes..." -ForegroundColor Yellow
Set-Location $observerPath

# Compile all Java files
javac -encoding UTF-8 Observer.java Subject.java Stock.java Investor.java Task.java TeamMember.java ObserverDemo.java

if ($LASTEXITCODE -eq 0) {
    Write-Host "✓ Compilation successful!" -ForegroundColor Green
    Write-Host ""
    
    # Navigate back to project root, then to java source path
    Set-Location $PSScriptRoot
    Set-Location $javaSourcePath
    
    Write-Host "Step 2: Running Observer Pattern Demo..." -ForegroundColor Yellow
    Write-Host ""
    
    # Run the demo
    java com.nvminh162.designpattern.observer.ObserverDemo
    
    Write-Host ""
    Write-Host "=== Demo completed ===" -ForegroundColor Green
    
    # Return to project root
    Set-Location $PSScriptRoot
} else {
    Write-Host "✗ Compilation failed! Please check the errors above." -ForegroundColor Red
    Set-Location $PSScriptRoot
    exit 1
}
