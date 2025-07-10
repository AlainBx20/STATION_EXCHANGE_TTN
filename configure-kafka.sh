#!/bin/bash

# Kafka Remote Configuration Script
echo "🔧 Kafka Remote Configuration Setup"
echo "=================================="

# Check if application.properties exists
if [ ! -f "src/main/resources/application.properties" ]; then
    echo "❌ Error: application.properties not found!"
    exit 1
fi

# Get receiver's IP address
echo "Enter the receiver's IP address (e.g., 192.168.1.100):"
read RECEIVER_IP

if [ -z "$RECEIVER_IP" ]; then
    echo "❌ Error: IP address cannot be empty!"
    exit 1
fi

# Validate IP format (basic check)
if [[ ! $RECEIVER_IP =~ ^[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+$ ]]; then
    echo "❌ Error: Invalid IP address format!"
    exit 1
fi

echo "🔍 Testing connection to $RECEIVER_IP:9092..."

# Test connection
if command -v telnet &> /dev/null; then
    timeout 5 bash -c "</dev/tcp/$RECEIVER_IP/9092" 2>/dev/null
    if [ $? -eq 0 ]; then
        echo "✅ Connection successful!"
    else
        echo "⚠️  Warning: Cannot connect to $RECEIVER_IP:9092"
        echo "   Make sure Kafka is running on the receiver's machine"
        echo "   and port 9092 is open in the firewall"
    fi
else
    echo "⚠️  telnet not available, skipping connection test"
fi

# Update application.properties
echo "📝 Updating application.properties..."
sed -i.bak "s/RECEIVER_IP_ADDRESS/$RECEIVER_IP/g" src/main/resources/application.properties

if [ $? -eq 0 ]; then
    echo "✅ Configuration updated successfully!"
    echo ""
    echo "📋 Next steps:"
    echo "1. Make sure the receiver has Kafka running on port 9092"
    echo "2. Run your application: mvn spring-boot:run"
    echo "3. Check the receiver's Kafka console for incoming messages"
    echo ""
    echo "🔍 To monitor messages on the receiver's side:"
    echo "   kafka-console-consumer.sh --topic aperak-messages --bootstrap-server localhost:9092 --from-beginning"
else
    echo "❌ Error updating configuration!"
    exit 1
fi 