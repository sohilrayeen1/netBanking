@echo off
REM run_allure.bat - clears old Allure results, runs Maven tests and generates/serves the Allure HTML report

:: Change working directory to the script location
cd /d %~dp0

:: Usage: run_allure.bat [no-test]
::   no-test  - skip the 'mvn clean test' step (useful if results are already present)
set SKIP_TESTS=0
if /i "%1"=="no-test" set SKIP_TESTS=1

echo ================================
echo Clearing previous Allure results...
echo ================================
if exist "allure-results" (
    rmdir /s /q "allure-results"
)
mkdir "allure-results" 2>nul

if "%SKIP_TESTS%"=="0" (
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
) else (
    echo Skipping Maven test execution (no-test flag present).
)

echo.
echo ================================
echo Waiting for Allure results to appear and settle...
echo ================================

:: Wait until there is at least one JSON result file in allure-results
setlocal enabledelayedexpansion
set COUNT=0
set /a ATTEMPTS=0
:WAIT_RESULTS
for /f "usebackq" %%A in (`dir /b "allure-results\*.json" 2^>nul ^| find /c /v ""`) do set COUNT=%%A
if %COUNT%==0 (
    set /a ATTEMPTS+=1
    if %ATTEMPTS% GTR 60 (
        echo No Allure result files detected after waiting. Exiting.
        endlocal
        pause
        exit /b 2
    )
    echo Waiting for Allure result files to be created... (attempt %ATTEMPTS%)
    timeout /t 2 >nul
    goto WAIT_RESULTS
)

:: Give a short grace period to allow files to finish writing
echo Detected %COUNT% result files. Waiting a few seconds for writes to finish...
timeout /t 5 >nul
endlocal

echo ================================
echo Generating Allure report (static) and opening in browser...
echo ================================

:: Ensure Allure CLI is on PATH
where allure >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo Allure CLI not found in PATH.
    echo Please install Allure commandline and add it to your PATH.
    echo For Windows users: choco install allure.commandline  OR follow https://docs.qameta.io/allure/
    pause
    exit /b 1
)

:: Generate a static report directory (overwrites existing)
allure generate allure-results -o allure-report --clean
if %ERRORLEVEL% NEQ 0 (
    echo Allure generation failed. Attempting 'allure serve' as a fallback...
    allure serve allure-results
    exit /b %ERRORLEVEL%
)

:: Open the generated report index.html in the default browser
if exist "allure-report\index.html" (
    start "" "allure-report\index.html"
) else (
    echo Generated report not found, attempting to serve instead...
    allure serve allure-results
)

exit /b 0