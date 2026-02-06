@echo off
REM run_allure.bat - clears old Allure results, runs Maven tests and serves the freshly generated Allure report

:: Change working directory to the script location
cd /d %~dp0

echo ================================
echo Clearing previous Allure results...
echo ================================
if exist "allure-results" (
    rmdir /s /q "allure-results"
)
mkdir "allure-results" 2>nul

echo ================================
echo Running Maven tests (using testng.xml)...
echo ================================
REM Use the TestNG suite configuration file in the project root
mvn clean test -Dsurefire.suiteXmlFiles=testng.xml
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Maven tests failed. The Allure results directory may be incomplete.
    pause
    exit /b %ERRORLEVEL%
)

echo.
echo ================================
echo Generating and serving Allure report...
echo ================================
REM Ensure Allure CLI is on PATH
where allure >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo Allure CLI not found in PATH.
    echo Please install Allure commandline and add it to your PATH.
    echo For Windows users: choco install allure.commandline  OR follow https://docs.qameta.io/allure/
    pause
    exit /b 1
)

:: Call allure to serve the freshly generated results
allure serve allure-results

exit /b 0