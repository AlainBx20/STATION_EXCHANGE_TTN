# Docker Kafka Connection Troubleshooting

## 🔍 **Problem Analysis**

Your application is trying to connect to Kafka on `192.168.1.45:9092` but the logs show it's still attempting `localhost:9092`. This indicates a configuration issue.

## 🛠️ **Step-by-Step Solution**

### **1. Verify Docker Container Status**

First, check if your Docker Kafka container is running:

```bash
# Check running containers
docker ps

# Check if Kafka container is accessible
docker logs <kafka-container-name>
```

### **2. Get the Correct IP Address**

```bash
# Get container IP address
docker inspect <kafka-container-name> | grep IPAddress

# Or use this command
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' <kafka-container-name>
```

### **3. Test Network Connectivity**

Run the test script I created:
```bash
test-kafka-connection.bat
```

Or manually test:
```bash
# Test ping
ping 192.168.1.45

# Test port connectivity
telnet 192.168.1.45 9092
```

### **4. Update Configuration**

If the IP address is different, run:
```bash
configure-docker-kafka.bat
```

Or manually edit `src/main/resources/application.properties`:
```properties
spring.kafka.bootstrap-servers=YOUR_DOCKER_IP:9092
```

### **5. Docker Network Configuration**

Make sure your Docker container is configured for external access:

```bash
# Create a network if needed
docker network create kafka-network

# Run Kafka with proper network configuration
docker run -d \
  --name kafka \
  --network kafka-network \
  -p 9092:9092 \
  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://YOUR_MACHINE_IP:9092 \
  -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 \
  confluentinc/cp-kafka:latest
```

### **6. Common Issues & Solutions**

#### **Issue: Container not accessible from host**
**Solution:**
```bash
# Check if port is exposed
docker port <kafka-container-name>

# If not, recreate container with proper port mapping
docker run -d -p 9092:9092 --name kafka confluentinc/cp-kafka:latest
```

#### **Issue: Wrong advertised listeners**
**Solution:**
```bash
# Update Kafka configuration
docker exec -it <kafka-container-name> bash
echo "advertised.listeners=PLAINTEXT://YOUR_MACHINE_IP:9092" >> /etc/kafka/server.properties
```

#### **Issue: Firewall blocking connection**
**Solution:**
- Windows: Check Windows Firewall settings
- Allow port 9092 through firewall
- Disable antivirus temporarily for testing

### **7. Alternative: Use Docker Compose**

Create `docker-compose.yml`:
```yaml
version: '3'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:latest
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
    ports:
      - "2181:2181"

  kafka:
    image: confluentinc/cp-kafka:latest
    depends_on:
      - zookeeper
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://192.168.1.45:9092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
    ports:
      - "9092:9092"
```

Then run:
```bash
docker-compose up -d
```

### **8. Verify Kafka Topics**

Once connected, create the required topics:
```bash
# Connect to Kafka container
docker exec -it <kafka-container-name> bash

# Create topics
kafka-topics --create --topic flux-inbound --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
kafka-topics --create --topic flux-response --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

# List topics
kafka-topics --list --bootstrap-server localhost:9092
```

### **9. Test with Console Producer/Consumer**

```bash
# Test producer
docker exec -it <kafka-container-name> kafka-console-producer --topic flux-inbound --bootstrap-server localhost:9092

# Test consumer
docker exec -it <kafka-container-name> kafka-console-consumer --topic flux-inbound --bootstrap-server localhost:9092 --from-beginning
```

## 🎯 **Quick Fix Checklist**

- [ ] Docker container is running
- [ ] Container IP is correct (not localhost)
- [ ] Port 9092 is exposed and accessible
- [ ] Network connectivity works (ping/telnet)
- [ ] Kafka topics are created
- [ ] Application configuration updated
- [ ] Firewall allows port 9092

## 📞 **If Still Not Working**

1. **Check Docker logs**: `docker logs <kafka-container-name>`
2. **Verify network**: `docker network ls` and `docker network inspect <network-name>`
3. **Test with simple client**: Use kafka-console-producer/consumer
4. **Check system resources**: Ensure Docker has enough memory/CPU

The application will now handle Kafka connection errors gracefully and continue running even if Kafka is unavailable. 