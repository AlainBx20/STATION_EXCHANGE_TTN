@echo off
echo Testing Kafka Configuration Fix
echo ===============================

echo.
echo 1. Checking if application.properties has correct bootstrap servers...
findstr "spring.kafka.bootstrap-servers" src\main\resources\application.properties

echo.
echo 2. Checking if KafkaProducerConfig.java uses @Value annotation...
findstr "@Value" src\main\java\com\example\stationdechange\config\KafkaProducerConfig.java

echo.
echo 3. Checking if localhost:9092 is still hardcoded anywhere...
findstr /s "localhost:9092" src\main\java\com\example\stationdechange\config\*.java

echo.
echo 4. Testing network connectivity to Docker Kafka...
ping -n 1 192.168.1.45

echo.
echo 5. Testing port connectivity...
telnet 192.168.1.45 9092

echo.
echo Configuration check completed!
echo If all tests pass, restart your Spring Boot application.
echo.
pause 