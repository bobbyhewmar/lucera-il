@echo off
set "JAVA_EXE=java.exe"
if defined JAVA_HOME if exist "%JAVA_HOME%\bin\java.exe" set "JAVA_EXE=%JAVA_HOME%\bin\java.exe"
if /I "%JAVA_EXE%"=="java.exe" (
where java >nul 2>&1
if errorlevel 1 (
echo Java 21+ not found. Configure JAVA_HOME or add java.exe to PATH.
pause
exit /b 1
)
) else (
if not exist "%JAVA_EXE%" (
echo Java 21+ not found. Configure JAVA_HOME or update JAVA_EXE in this script.
pause
exit /b 1
)
)
set "JAVA_VERSION="
for /f "tokens=3 delims= " %%v in ('"%JAVA_EXE%" -version 2^>^&1 ^| findstr /i "version"') do set "JAVA_VERSION=%%~v"
set "JAVA_VERSION=%JAVA_VERSION:"=%"
set "JAVA_MAJOR="
for /f "tokens=1,2 delims=.-" %%a in ("%JAVA_VERSION%") do (
if "%%a"=="1" (set "JAVA_MAJOR=%%b") else (set "JAVA_MAJOR=%%a")
)
if not defined JAVA_MAJOR (
echo Unable to detect Java version from "%JAVA_EXE%".
pause
exit /b 1
)
if %JAVA_MAJOR% LSS 21 (
echo Java 21+ required. Detected version %JAVA_VERSION%.
pause
exit /b 1
)
:start
echo Starting AuthServer.
echo.
"%JAVA_EXE%" -server -Dfile.encoding=UTF-8 -Xmx256m -cp config;.././libs/* l2.authserver.AuthServer
if ERRORLEVEL 2 goto restart
if ERRORLEVEL 1 goto error
goto end
:restart
echo.
echo Server restarted ...
echo.
goto start
:error
echo.
echo Server terminated abnormaly ...
echo.
:end
echo.
echo Server terminated ...
echo.

pause
