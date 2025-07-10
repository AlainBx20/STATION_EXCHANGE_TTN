@echo off
echo 🔧 Kafka Remote Configuration Setup
echo ==================================

REM Check if application.properties exists
if not exist "src\main\resources\application.properties" (
    echo ❌ Error: application.properties not found!
    pause
    exit /b 1
)

REM Get receiver's IP address
set /p RECEIVER_IP="Enter the receiver's IP address (e.g., 192.168.1.100): "

if "%RECEIVER_IP%"=="" (
    echo ❌ Error: IP address cannot be empty!
    pause
    exit /b 1
)

echo 🔍 Testing connection to %RECEIVER_IP%:9092...

REM Test connection using PowerShell
powershell -Command "try { $tcp = New-Object System.Net.Sockets.TcpClient; $tcp.Connect('%RECEIVER_IP%', 9092); $tcp.Close(); Write-Host '✅ Connection successful!' } catch { Write-Host '⚠️  Warning: Cannot connect to %RECEIVER_IP%:9092'; Write-Host '   Make sure Kafka is running on the receiver''s machine'; Write-Host '   and port 9092 is open in the firewall' }"

echo 📝 Updating application.properties...

REM Create backup
copy "src\main\resources\application.properties" "src\main\resources\application.properties.bak" >nul

REM Update the file using PowerShell
powershell -Command "(Get-Content 'src\main\resources\application.properties') -replace 'RECEIVER_IP_ADDRESS', '%RECEIVER_IP%' | Set-Content 'src\main\resources\application.properties'"

if %errorlevel% equ 0 (
    echo ✅ Configuration updated successfully!
    echo.
    echo 📋 Next steps:
    echo 1. Make sure the receiver has Kafka running on port 9092
    echo 2. Run your application: mvn spring-boot:run
    echo 3. Check the receiver's Kafka console for incoming messages
    echo.
    echo 🔍 To monitor messages on the receiver's side:
    echo    kafka-console-consumer.sh --topic aperak-messages --bootstrap-server localhost:9092 --from-beginning
) else (
    echo ❌ Error updating configuration!
)

pause 