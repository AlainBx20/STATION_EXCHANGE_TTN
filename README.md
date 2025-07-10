# Station de Change - APERAK Kafka Integration

This Spring Boot application processes APERAK (Acknowledgement) messages for TTN (Titre de Transport National) documents and sends them to Kafka topics.

## Features

- **Database Integration**: Connects to Oracle database to fetch APERAK data
- **JSON Generation**: Converts database entities to structured JSON format
- **Kafka Integration**: Sends JSON messages to Kafka topics (local or remote)
- **REST API**: Provides endpoints for manual triggering and testing

## Prerequisites

- Java 17
- Maven
- Oracle Database
- Apache Kafka (running locally or remotely)

## Configuration

### Database Configuration
The application connects to Oracle database with the following configuration in `application.properties`:
```properties
spring.datasource.url=jdbc:oracle:thin:@37.187.250.163:1521/IBANSYS_WIFAK
spring.datasource.username=smi_ttn
spring.datasource.password=smi_ttn
```

### Kafka Configuration

#### **For Local Kafka:**
```properties
spring.kafka.bootstrap-servers=localhost:9092
kafka.topic.aperak=aperak-messages
```

#### **For Remote Kafka:**
```properties
spring.kafka.bootstrap-servers=RECEIVER_IP_ADDRESS:9092
kafka.topic.aperak=aperak-messages
```

**Quick Configuration:**
- **Windows**: Run `configure-kafka.bat`
- **Linux/Mac**: Run `./configure-kafka.sh`

## Running the Application

### **Local Kafka Setup:**
1. **Start Kafka** (if not already running):
   ```bash
   kafka-server-start.sh config/server.properties
   ```

2. **Create Kafka Topic** (if not exists):
   ```bash
   kafka-topics.sh --create --topic aperak-messages --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
   ```

3. **Build and Run the Application**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

### **Remote Kafka Setup:**
See `KAFKA_REMOTE_SETUP.md` for detailed instructions on setting up remote Kafka communication.

**Quick Steps:**
1. **Receiver**: Install Kafka, configure for remote access, start broker
2. **Sender**: Run configuration script and update IP address
3. **Test**: Verify connection and run application

## Usage

### Automatic Execution
When the application starts, it automatically:
1. Fetches the first Aperak0 record from the database
2. Retrieves related Aperak1 errors and PiecesJointes
3. Generates JSON in the APERAK format
4. Sends the JSON to Kafka topic `aperak-messages`
5. Prints the JSON to console for verification

### REST API Endpoints

#### 1. Generate and Send APERAK to Kafka
```bash
GET http://localhost:8080/api/aperak/generate-and-send
```
This endpoint generates the APERAK JSON and sends it to Kafka.

#### 2. Generate APERAK JSON Only
```bash
GET http://localhost:8080/api/aperak/generate
```
This endpoint generates the APERAK JSON without sending to Kafka.

#### 3. Send Custom JSON to Kafka
```bash
POST http://localhost:8080/api/aperak/send
Content-Type: text/plain

{"your": "json", "message": "here"}
```

## Kafka Topic Details

- **Topic Name**: `aperak-messages`
- **Message Key**: Uses `numeroDossierTtn` from Aperak0 entity
- **Message Value**: JSON string in APERAK format
- **Partitions**: 1 (default)
- **Replication Factor**: 1 (default)

## Monitoring Kafka Messages

### **Local Kafka:**
```bash
# Listen to the aperak-messages topic
kafka-console-consumer.sh --topic aperak-messages --bootstrap-server localhost:9092 --from-beginning
```

### **Remote Kafka:**
```bash
# On the receiver's machine
kafka-console-consumer.sh --topic aperak-messages --bootstrap-server localhost:9092 --from-beginning
```

## JSON Structure

The generated JSON follows the APERAK format:
```json
{
  "typeDocument": "APERAK",
  "sendDate": "2025-06-27T09:30:00.000+00:00",
  "otherMeta": null,
  "body": {
    "enteteFlux": {
      "id": 0,
      "sensFlux": "s",
      "dateInsertion": "2025-06-27T09:30:00.000+00:00",
      "typeMessage": "FX1",
      "typeDocument": "ABC",
      "numeroTtn": "82997303",
      "numeroDemande": "REQ99887766",
      "destinataire": "CENTRAL_BANK",
      "emetteur": "BANQUE_TUNIS",
      "codeCloture": "s",
      "status": "s"
    },
    "detailAperak": {
      "typeMessage": "FX1",
      "typeDocument": "APERAK",
      "refTtnNumMessage": "MSG987654321",
      "refTtnNumDossier": "82997303",
      "refTtnNumDemande": "REQ99887766",
      "routageEmetteur": "BANQUE_TUNIS",
      "routageDestinataire": "CENTRAL_BANK",
      "dateEmission": "2025-06-27T09:30:00.000+00:00"
    },
    "pieceJointes": [],
    "aperakErreurs": []
  }
}
```

## Troubleshooting

### Common Issues

1. **Kafka Connection Failed**:
   - Ensure Kafka is running on the configured address
   - Check if the topic `aperak-messages` exists
   - Verify network connectivity for remote Kafka

2. **Database Connection Failed**:
   - Verify Oracle database is accessible
   - Check database credentials in `application.properties`

3. **No Data Found**:
   - Ensure the database contains Aperak0 records
   - Check if related Aperak1 and PiecesJointes records exist

4. **Remote Connection Issues**:
   - Check firewall settings on receiver's machine
   - Verify Kafka is configured for remote access
   - Test network connectivity with `ping` or `telnet`

### Logs
The application provides detailed logging for:
- Database operations
- JSON generation
- Kafka message sending
- Error handling

Check the console output for detailed information about the process.

## Configuration Scripts

### **Windows Users:**
```bash
configure-kafka.bat
```

### **Linux/Mac Users:**
```bash
chmod +x configure-kafka.sh
./configure-kafka.sh
```

These scripts will help you configure the Kafka connection for remote communication by:
1. Prompting for the receiver's IP address
2. Testing the connection
3. Updating the configuration file
4. Providing next steps 