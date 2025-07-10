@echo off
echo Docker Kafka Configuration Script
echo ================================

echo.
echo Current Kafka configuration:
echo Bootstrap servers: 192.168.1.45:9092

echo.
set /p NEW_IP="Enter the Docker container IP address (or press Enter to keep current): "

if not "%NEW_IP%"=="" (
    echo.
    echo Updating configuration to use %NEW_IP%:9092...
    
    REM Update application.properties
    powershell -Command "(Get-Content 'src\main\resources\application.properties') -replace 'spring.kafka.bootstrap-servers=.*', 'spring.kafka.bootstrap-servers=%NEW_IP%:9092' | Set-Content 'src\main\resources\application.properties'"
    
    echo Configuration updated successfully!
) else (
    echo Keeping current configuration: 192.168.1.45:9092
)

echo.
echo Next steps:
echo 1. Make sure your Docker Kafka container is running
echo 2. Ensure the container exposes port 9092
echo 3. Verify the container is accessible from your machine
echo 4. Run test-kafka-connection.bat to test connectivity
echo 5. Start your Spring Boot application

echo.
pause 