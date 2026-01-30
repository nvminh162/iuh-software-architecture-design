@echo off
REM Batch script to compile and run Observer Pattern Demo
REM Run from project root directory
REM Usage: run-observer-demo.bat

echo === Observer Pattern Demo Runner ===
echo.

REM Set paths
set OBSERVER_PATH=src\main\java\com\nvminh162\designpattern\observer
set JAVA_SOURCE_PATH=src\main\java

REM Check if Java is available
java -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo Error: Java is not installed or not in PATH
    exit /b 1
)

echo Step 1: Compiling Observer Pattern classes...
cd /d "%OBSERVER_PATH%"

REM Compile all Java files
javac -encoding UTF-8 Observer.java Subject.java Stock.java Investor.java Task.java TeamMember.java ObserverDemo.java

if %ERRORLEVEL% EQU 0 (
    echo [OK] Compilation successful!
    echo.
    
    REM Navigate back to project root, then to java source path
    cd /d "%~dp0"
    cd "%JAVA_SOURCE_PATH%"
    
    echo Step 2: Running Observer Pattern Demo...
    echo.
    
    REM Run the demo
    java com.nvminh162.designpattern.observer.ObserverDemo
    
    echo.
    echo === Demo completed ===
    
    REM Return to project root
    cd /d "%~dp0"
) else (
    echo [ERROR] Compilation failed! Please check the errors above.
    cd /d "%~dp0"
    exit /b 1
)

pause
