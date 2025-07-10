# Remote Kafka Setup Guide

This guide explains how to send APERAK JSON messages from your machine to a Kafka broker running on a different computer.

## 🔧 **For the Receiver (Different Computer)**

### 1. **Install and Start Kafka**
```bash
# Download Kafka if not already installed
wget https://downloads.apache.org/kafka/3.6.1/kafka_2.13-3.6.1.tgz
tar -xzf kafka_2.13-3.6.1.tgz
cd kafka_2.13-3.6.1

# Start Zookeeper (required for Kafka)
bin/zookeeper-server-start.sh config/zookeeper.properties &

# Start Kafka broker
bin/kafka-server-start.sh config/server.properties &
```

### 2. **Configure Kafka for Remote Access**
Edit `config/server.properties` on the receiver's machine:
```properties
# Allow connections from any IP (or specify your sender's IP)
listeners=PLAINTEXT://0.0.0.0:9092
advertised.listeners=PLAINTEXT://RECEIVER_IP:9092

# Other important settings
log.dirs=/tmp/kafka-logs
num.partitions=1
default.replication.factor=1
```

### 3. **Create the Topic**
```bash
bin/kafka-topics.sh --create --topic aperak-messages --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```

### 4. **Listen for Messages**
```bash
# Listen to incoming messages
bin/kafka-console-consumer.sh --topic aperak-messages --bootstrap-server localhost:9092 --from-beginning
```

### 5. **Find Your IP Address**
```bash
# On Linux/Mac
ifconfig
# or
ip addr show

# On Windows
ipconfig
```

**Note down the IP address** - you'll need to share this with the sender.

---

## 🚀 **For You (The Sender)**

### 1. **Get the Receiver's IP Address**
Ask the receiver for their IP address (e.g., `192.168.1.100`)

### 2. **Update Your Configuration**
Edit `src/main/resources/application.properties`:
```properties
# Replace RECEIVER_IP_ADDRESS with the actual IP
spring.kafka.bootstrap-servers=192.168.1.100:9092
```

### 3. **Test the Connection**
```bash
# Test if you can reach the receiver's Kafka
telnet 192.168.1.100 9092
```

### 4. **Run Your Application**
```bash
mvn spring-boot:run
```

---

## 🌐 **Network Requirements**

### **Firewall Settings**
The receiver needs to open port 9092:

**On Linux:**
```bash
sudo ufw allow 9092
```

**On Windows:**
- Open Windows Firewall
- Add inbound rule for port 9092

**On Mac:**
```bash
sudo pfctl -e
# Edit /etc/pf.conf to allow port 9092
```

### **Router Configuration**
If the receiver is behind a router:
1. Configure port forwarding for port 9092
2. Forward to the receiver's local IP address

---

## 🔍 **Troubleshooting**

### **Connection Issues**

1. **Test Network Connectivity:**
   ```bash
   ping RECEIVER_IP_ADDRESS
   telnet RECEIVER_IP_ADDRESS 9092
   ```

2. **Check Kafka is Running:**
   ```bash
   # On receiver's machine
   netstat -an | grep 9092
   ```

3. **Check Firewall:**
   ```bash
   # On receiver's machine
   sudo iptables -L | grep 9092
   ```

### **Common Error Messages**

- **Connection refused**: Kafka not running or firewall blocking
- **Connection timeout**: Network connectivity issues
- **Topic not found**: Topic not created on receiver's Kafka

---

## 📋 **Quick Setup Checklist**

### **Receiver Side:**
- [ ] Install Kafka
- [ ] Configure `server.properties` for remote access
- [ ] Start Zookeeper and Kafka
- [ ] Create `aperak-messages` topic
- [ ] Open firewall port 9092
- [ ] Share IP address with sender

### **Sender Side:**
- [ ] Get receiver's IP address
- [ ] Update `application.properties`
- [ ] Test network connectivity
- [ ] Run the application

---

## 🎯 **Example Configuration**

**Receiver's IP:** `192.168.1.100`

**Your `application.properties`:**
```properties
spring.kafka.bootstrap-servers=192.168.1.100:9092
```

**Receiver's `server.properties`:**
```properties
listeners=PLAINTEXT://0.0.0.0:9092
advertised.listeners=PLAINTEXT://192.168.1.100:9092
```

Once configured, your JSON messages will be sent to the receiver's Kafka broker and they can consume them using the console consumer or their own application! 