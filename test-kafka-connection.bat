@echo off
echo Testing Kafka connection to 192.168.1.45:9092...

echo.
echo 1. Testing network connectivity...
ping -n 1 192.168.1.45
if %errorlevel% neq 0 (
    echo ERROR: Cannot ping 192.168.1.45
    echo Please check if the Docker container is running and accessible
    pause
    exit /b 1
)

echo.
echo 2. Testing port connectivity...
telnet 192.168.1.45 9092
if %errorlevel% neq 0 (
    echo ERROR: Cannot connect to port 9092 on 192.168.1.45
    echo Please check if Kafka is running in the Docker container
    pause
    exit /b 1
)

echo.
echo SUCCESS: Kafka connection test passed!
echo You can now run your Spring Boot application
pause 