# Karate-Spring Boot Integration: Production-Grade Testing & DevOps Pipeline

[![CI - Build & Test](https://github.com/hrishabh6/karate-scala_learning/actions/workflows/ci.yml/badge.svg)](https://github.com/hrishabh6/karate-scala_learning/actions/workflows/ci.yml)
[![CD - Deploy to VM](https://github.com/hrishabh6/karate-scala_learning/actions/workflows/cd.yml/badge.svg)](https://github.com/hrishabh6/karate-scala_learning/actions/workflows/cd.yml)

## 📋 Table of Contents
- [Project Overview](#-project-overview)
- [Learning Journey](#-learning-journey)
- [System Architecture](#-system-architecture)
- [Technology Stack](#-technology-stack)
- [Project Structure](#-project-structure)
- [Local Development Setup](#-local-development-setup)
- [VM Infrastructure Setup](#-vm-infrastructure-setup)
- [CI/CD Pipeline Deep Dive](#-cicd-pipeline-deep-dive)
- [Testing Strategy](#-testing-strategy)
- [Load Testing with Gatling](#-load-testing-with-gatling)
- [Monitoring Stack](#-monitoring-stack)
- [Configuration Management](#-configuration-management)
- [Deployment Workflows](#-deployment-workflows)
- [Key Implementation Details](#-key-implementation-details)
- [Troubleshooting Guide](#-troubleshooting-guide)
- [Performance Metrics](#-performance-metrics)
- [Future Enhancements](#-future-enhancements)
- [Lessons Learned](#-lessons-learned)

---

## 🎯 Project Overview

This project demonstrates a **production-grade end-to-end testing and deployment pipeline** for a Spring Boot REST API application. Built entirely in a local environment simulating AWS EC2 infrastructure, this setup showcases modern DevOps practices without incurring cloud costs.

### What This Project Achieves

✅ **API Testing with Karate** - Automated REST API testing using Karate Framework  
✅ **Load Testing with Gatling** - Performance testing using Karate-Gatling integration  
✅ **Complete CI/CD Pipeline** - Automated build, test, and deployment workflows  
✅ **Multi-Environment Support** - Dev, Test, and Production configurations  
✅ **Distributed Architecture** - Separate VMs for application and load generation  
✅ **Real-Time Monitoring** - Prometheus + Grafana observability stack  
✅ **Dockerized Deployment** - Container-based deployment with Docker Compose  
✅ **Database Management** - MySQL for production, H2 for testing

### Real-World Production Analogies

| Component | Local Setup | AWS Production Equivalent |
|-----------|-------------|---------------------------|
| VM-1 | Virtual Machine with Spring Boot | EC2 instance (t3.medium) running application |
| VM-2 | Virtual Machine for load testing | EC2 instance (t3.small) for Gatling + Monitoring |
| GitHub Runner | Self-hosted runner on local machine | EC2 instance for GitHub Actions runner |
| MySQL Container | Docker container on VM-1 | RDS MySQL instance |
| Prometheus + Grafana | Installed on VM-2 | CloudWatch + Managed Grafana |

---

## 🚀 Learning Journey

### Phase 1: Foundation (Week 1)
**Goal**: Build basic Spring Boot application with REST APIs

```
✅ Created Spring Boot application with 3-4 REST endpoints
✅ Integrated MySQL database with JPA
✅ Implemented CRUD operations for Product entity
✅ Tested APIs manually with Postman
```

**Key Files Created**:
- `LoadtestApplication.java` - Main Spring Boot application
- `ProductController.java` - REST controller with CRUD endpoints
- `Product.java` - JPA entity
- `ProductService.java` - Business logic layer
- `ProductRepository.java` - Data access layer

### Phase 2: API Testing Integration (Week 2)
**Goal**: Integrate Karate framework for automated API testing

```
✅ Added Karate dependencies to pom.xml
✅ Created Karate feature files for API testing
✅ Implemented JUnit5 test runner for Karate
✅ Configured multi-environment support
✅ Set up H2 database for testing profile
```

**Key Files Created**:
- `users.feature` - Karate test scenarios
- `UsersRunner.java` - JUnit5 runner with Spring Boot integration
- `karate-config.js` - Dynamic configuration for different environments
- `application-test.yml` - Test profile with H2 database

### Phase 3: Load Testing (Week 3)
**Goal**: Integrate Gatling for performance testing

```
✅ Added Karate-Gatling dependencies
✅ Created Scala-based Gatling simulation
✅ Configured Maven to skip Gatling during regular tests
✅ Generated HTML performance reports
✅ Analyzed response times and throughput
```

**Key Files Created**:
- `LoadTestSimulation.scala` - Gatling load test simulation
- Updated `pom.xml` with Gatling plugin configuration
- Maven property: `-Dgatling.skip=true` for CI builds

### Phase 4: CI/CD Pipeline (Week 4)
**Goal**: Automate build, test, and deployment

```
✅ Set up GitHub self-hosted runner locally
✅ Created VM-1 as production-like environment
✅ Configured SSH access between runner and VM
✅ Implemented CI workflow (build + Karate tests)
✅ Implemented CD workflow (deploy via Docker Compose)
✅ Set up GitHub Secrets for secure configuration
```

**Key Files Created**:
- `.github/workflows/ci.yml` - Continuous Integration workflow
- `.github/workflows/cd.yml` - Continuous Deployment workflow
- `docker-compose.prod.yml` - Production deployment configuration
- `Dockerfile` - Container image definition

### Phase 5: Architecture Refinement (Week 5)
**Goal**: Separate load generation from application server

**Problem Identified**:
```
❌ Initial Architecture: Generate load FROM VM-1 TO VM-1
   - Resource contention (CPU/Memory shared between app and load generator)
   - Unrealistic metrics (network latency = 0)
   - Cannot simulate real-world distributed load
```

**Solution Implemented**:
```
✅ Final Architecture: Generate load FROM VM-2 TO VM-1
   - VM-1: Dedicated to running Spring Boot application
   - VM-2: Dedicated to Gatling load generation
   - Realistic network latency and resource isolation
   - True production simulation
```

**Key Changes**:
- Created second VM (VM-2) for load testing
- Updated load test workflow to SSH into VM-2
- Configured network communication between VMs
- Modified Gatling simulation to target VM-1's IP address

### Phase 6: Monitoring & Observability (Week 6)
**Goal**: Add comprehensive monitoring infrastructure

```
✅ Installed Prometheus on VM-2
✅ Installed Grafana on VM-2
✅ Added Spring Boot Actuator to application
✅ Configured Micrometer Prometheus registry
✅ Created custom Grafana dashboards
✅ Set up metrics collection every 15 seconds
```

**Key Metrics Monitored**:
- **JVM Metrics**: Heap memory, garbage collection, thread count
- **System Metrics**: CPU usage, disk I/O, network throughput
- **Application Metrics**: HTTP request rate, response times, error rates
- **Database Metrics**: Connection pool size, query execution time

**Key Files Created**:
- `prometheus.yml` - Prometheus configuration
- Updated `application-prod.properties` with actuator endpoints
- Custom Grafana dashboard JSON exports

---

## 🏗️ System Architecture

### Complete System Diagram

```
┌──────────────────────────────────────────────────────────────────────────┐
│                          Developer Workstation                            │
│                                                                            │
│  ┌────────────────────────────────────────────────────────────────┐      │
│  │               GitHub Self-Hosted Runner                        │      │
│  │  • Listens for GitHub Actions workflows                        │      │
│  │  • Executes CI/CD jobs locally                                 │      │
│  │  • Has SSH access to both VMs                                  │      │
│  └────────────────────────────────────────────────────────────────┘      │
│                                                                            │
└──────────────────┬─────────────────────────────────────────────┬─────────┘
                   │                                             │
                   │ SSH Deploy                                  │ SSH Load Test
                   ▼                                             ▼
┌──────────────────────────────────────────┐  ┌────────────────────────────────────┐
│           VM-1 (Test Server)             │  │   VM-2 (Load Testing + Monitoring)  │
│         IP: 192.168.1.100                │  │        IP: 192.168.1.101            │
│                                           │  │                                     │
│  ┌────────────────────────────────────┐  │  │  ┌──────────────────────────────┐  │
│  │   Docker Compose Stack             │  │  │  │    Gatling Load Generator    │  │
│  │                                    │  │  │  │                              │  │
│  │  ┌──────────────────────────────┐ │  │  │  │  • Git clone project repo    │  │
│  │  │  Spring Boot App Container   │ │  │  │  │  • Git pull latest changes   │  │
│  │  │  Port: 8080                  │ │◄─┼──┼──│  • mvn gatling:test          │  │
│  │  │                              │ │  │  │  │  • Generate HTML reports     │  │
│  │  │  Endpoints:                  │ │  │  │  │                              │  │
│  │  │  • GET /api/products         │ │  │  │  │  Load Pattern:               │  │
│  │  │  • POST /api/products        │ │  │  │  │  • Ramp: 10 users in 10s     │  │
│  │  │  • PUT /api/products/{id}    │ │  │  │  │  • Sustain: 5 users/sec 20s  │  │
│  │  │  • GET /api/products/{id}    │ │  │  │  └──────────────────────────────┘  │
│  │  │                              │ │  │  │                                     │
│  │  │  Monitoring Endpoints:       │ │  │  │  ┌──────────────────────────────┐  │
│  │  │  • /actuator/health          │ │  │  │  │    Prometheus (Port 9090)    │  │
│  │  │  • /actuator/prometheus ─────┼─┼──┼──┼─►│                              │  │
│  │  │  • /actuator/metrics         │ │  │  │  │  Scrape Interval: 15s        │  │
│  │  └──────────────────────────────┘ │  │  │  │  Retention: 15 days          │  │
│  │                                    │  │  │  │                              │  │
│  │  ┌──────────────────────────────┐ │  │  │  │  Targets:                    │  │
│  │  │  MySQL Container             │ │  │  │  │  • VM-1:8080/actuator        │  │
│  │  │  Port: 3306 (3307 external)  │ │  │  │  └──────────────────────────────┘  │
│  │  │                              │ │  │  │              │                      │
│  │  │  Database: loadtest          │ │  │  │              │ Data Source          │
│  │  │  User: app_user              │ │  │  │              ▼                      │
│  │  │  Volume: mysql-data          │ │  │  │  ┌──────────────────────────────┐  │
│  │  └──────────────────────────────┘ │  │  │  │     Grafana (Port 3000)      │  │
│  └────────────────────────────────────┘  │  │  │                              │  │
│                                           │  │  │  Dashboards:                 │  │
│  Docker Images:                           │  │  │  • JVM Metrics               │  │
│  • ghcr.io/hrishabh6/...:latest          │  │  │  • System Resources          │  │
│  • mysql:8.0                             │  │  │  • HTTP Request Analytics    │  │
│                                           │  │  │  • Database Connections      │  │
└───────────────────────────────────────────┘  │  └──────────────────────────────┘  │
                                                │                                     │
                                                └─────────────────────────────────────┘

                    GitHub Repository
                           │
        ┌──────────────────┼──────────────────┐
        │                  │                  │
    Push Event      Workflow Dispatch    Workflow Success
        │                  │                  │
        ▼                  ▼                  ▼
    [CI Workflow]  [Load Test Workflow]  [CD Workflow]
```

### Data Flow Diagram

```
┌─────────────┐
│ Developer   │
│ Commits Code│
└──────┬──────┘
       │
       │ git push origin main
       ▼
┌─────────────────────────────────────────────────────────────┐
│                    GitHub Repository                        │
└──────┬──────────────────────────────────────────────────────┘
       │
       │ Webhook trigger
       ▼
┌─────────────────────────────────────────────────────────────┐
│              GitHub Self-Hosted Runner                      │
│                                                              │
│  Step 1: Clone repository                                   │
│  Step 2: Build Maven project                                │
│  Step 3: Run Karate tests with H2 database                  │
│          Command: mvn -Dspring.profiles.active=test \       │
│                       -Dgatling.skip=true test              │
│  Step 4: Build JAR with prod profile                        │
│  Step 5: Build Docker image                                 │
│  Step 6: Push to GitHub Container Registry                  │
└──────┬──────────────────────────────────────────────────────┘
       │
       │ If tests pass
       ▼
┌─────────────────────────────────────────────────────────────┐
│                    CD Workflow Triggered                     │
│                                                              │
│  Step 1: SSH into VM-1                                      │
│  Step 2: Pull latest Docker image from GHCR                 │
│  Step 3: Stop old containers (docker compose down)          │
│  Step 4: Start new containers (docker compose up -d)        │
│  Step 5: Health check (curl /actuator/health)               │
└──────┬──────────────────────────────────────────────────────┘
       │
       │ Application Running on VM-1:8080
       ▼
┌─────────────────────────────────────────────────────────────┐
│                      VM-1 Running                            │
│                                                              │
│  ┌────────────────────────────────────────────────────────┐ │
│  │ Spring Boot App serving requests                       │ │
│  │ Exposing metrics at /actuator/prometheus               │ │
│  └────────────────────────────────────────────────────────┘ │
└──────┬──────────────────────────────────────────────────────┘
       │
       │ Metrics scraped every 15s
       ▼
┌─────────────────────────────────────────────────────────────┐
│              Prometheus on VM-2                              │
│  • Stores time-series metrics                               │
│  • Provides query interface                                 │
└──────┬──────────────────────────────────────────────────────┘
       │
       │ Data source for visualization
       ▼
┌─────────────────────────────────────────────────────────────┐
│                Grafana on VM-2                               │
│  • Real-time dashboards                                     │
│  • Historical analysis                                      │
│  • Alert configuration                                      │
└─────────────────────────────────────────────────────────────┘

═══════════════════════════════════════════════════════════════
        Manual Load Test Workflow (Triggered via GUI)
═══════════════════════════════════════════════════════════════

┌─────────────┐
│ Engineer    │
│ Triggers    │
│ Load Test   │
└──────┬──────┘
       │
       │ workflow_dispatch
       ▼
┌─────────────────────────────────────────────────────────────┐
│          GitHub Runner SSH into VM-2                         │
│                                                              │
│  Step 1: Navigate to project directory                      │
│  Step 2: Git pull latest code                               │
│  Step 3: Run Gatling test                                   │
│          Command: mvn gatling:test \                        │
│                   -DbaseUrl=http://VM-1-IP:8080             │
│  Step 4: Zip latest report                                  │
│  Step 5: SCP report back to runner                          │
│  Step 6: Upload as GitHub artifact                          │
└──────┬──────────────────────────────────────────────────────┘
       │
       │ Load sent to VM-1
       ▼
┌─────────────────────────────────────────────────────────────┐
│  VM-1 receives load from VM-2                                │
│  • Spring Boot handles requests                             │
│  • Metrics spike visible in Grafana                         │
│  • Response times recorded in Gatling report                │
└─────────────────────────────────────────────────────────────┘
```

---

## 🛠️ Technology Stack

### Core Application Stack

| Layer | Technology | Version | Purpose |
|-------|-----------|---------|---------|
| **Framework** | Spring Boot | 3.3.5 | Main application framework |
| **Language** | Java | 17 | Primary programming language |
| **Build Tool** | Maven | 3.x | Dependency management & build |
| **Production DB** | MySQL | 8.0 | Production database |
| **Test DB** | H2 Database | Latest | In-memory database for testing |
| **ORM** | Spring Data JPA | 3.3.5 | Database abstraction layer |

### Testing Stack

| Tool | Technology | Version | Purpose |
|------|-----------|---------|---------|
| **API Testing** | Karate | 1.5.0 | BDD-style API testing |
| **Load Testing** | Karate-Gatling | 1.5.0 | Performance/stress testing |
| **Test Runner** | JUnit 5 | Latest | Test execution framework |
| **Load Simulation** | Scala | 2.13.x | Gatling simulation language |

### DevOps & Infrastructure

| Component | Technology | Version | Purpose |
|-----------|-----------|---------|---------|
| **CI/CD** | GitHub Actions | Latest | Workflow automation |
| **Runner** | Self-Hosted | Latest | Local job execution |
| **Containerization** | Docker | 24.x | Application packaging |
| **Orchestration** | Docker Compose | 2.x | Multi-container management |
| **Image Registry** | GitHub Container Registry (GHCR) | Latest | Docker image storage |
| **VM Management** | VirtualBox/VMware | Latest | Local VM infrastructure |

### Monitoring & Observability

| Tool | Technology | Version | Purpose |
|------|-----------|---------|---------|
| **Metrics Collection** | Prometheus | 2.45.0 | Time-series metrics database |
| **Visualization** | Grafana | 10.x | Dashboard and alerting |
| **Metrics Library** | Micrometer | Latest | Application metrics instrumentation |
| **Actuator** | Spring Boot Actuator | 3.3.5 | Health checks and metrics endpoints |

---

## 📁 Project Structure

```
karate-scala_learning/
│
├── .github/
│   └── workflows/
│       ├── ci.yml                          # CI workflow: Build + Test
│       ├── cd.yml                          # CD workflow: Deploy to VM-1
│       └── cd-load-test.yml                # Load test workflow (manual trigger)
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/loadtest/
│   │   │       ├── LoadtestApplication.java      # Main Spring Boot class
│   │   │       ├── controllers/
│   │   │       │   └── ProductController.java    # REST API endpoints
│   │   │       ├── model/
│   │   │       │   └── Product.java              # JPA entity
│   │   │       ├── repository/
│   │   │       │   └── ProductRepository.java    # Data access
│   │   │       └── service/
│   │   │           └── ProductService.java       # Business logic
│   │   │
│   │   └── resources/
│   │       ├── application.yml                   # Base configuration
│   │       ├── application-dev.yml               # Dev profile (H2)
│   │       ├── application-test.yml              # Test profile (H2)
│   │       └── application-prod.properties       # Prod profile (MySQL)
│   │
│   └── test/
│       ├── java/
│       │   └── examples/
│       │       └── users/
│       │           ├── UsersRunner.java          # JUnit5 Karate runner
│       │           └── logback-test.xml          # Test logging config
│       │
│       ├── resources/
│       │   └── examples/
│       │       └── users/
│       │           ├── users.feature             # Karate test scenarios
│       │           ├── application-test.yml      # Test-specific config
│       │           └── karate-config.js          # Karate global config
│       │
│       └── scala/
│           └── simulations/
│               └── LoadTestSimulation.scala      # Gatling simulation
│
├── docker-compose.local.yml                # Local development setup
├── docker-compose.prod.yml                 # Production deployment config
├── Dockerfile                              # Container image definition
├── pom.xml                                 # Maven project configuration
├── .gitignore                              # Git ignore rules
└── README.md                               # This file
```

### Key Directories Explained

#### `.github/workflows/`
Contains all GitHub Actions workflow definitions:
- **ci.yml**: Runs on every push, builds the project, runs Karate tests
- **cd.yml**: Triggers after successful CI, deploys to VM-1
- **cd-load-test.yml**: Manual workflow to run Gatling tests from VM-2

#### `src/main/java/com/example/loadtest/`
Spring Boot application source code:
- **controllers/**: REST API endpoints
- **model/**: JPA entities (Product)
- **repository/**: Spring Data JPA repositories
- **service/**: Business logic layer

#### `src/main/resources/`
Application configuration files for different environments:
- **application.yml**: Base configuration (shared by all profiles)
- **application-dev.yml**: H2 database for local development
- **application-test.yml**: H2 database for CI testing
- **application-prod.properties**: MySQL for production

#### `src/test/java/examples/users/`
Karate test runners:
- **UsersRunner.java**: JUnit5 integration with Spring Boot test context

#### `src/test/resources/examples/users/`
Karate test definitions:
- **users.feature**: Gherkin-style API test scenarios
- **karate-config.js**: Dynamic configuration (port detection, base URL)

#### `src/test/scala/simulations/`
Gatling load test simulations:
- **LoadTestSimulation.scala**: Load test scenarios with user ramp-up

---

## 💻 Local Development Setup

### Prerequisites

```bash
# Required Software
Java 17 or higher
Maven 3.8+
Docker & Docker Compose
Git
MySQL Client (optional, for local testing)
```

### Step-by-Step Setup

#### 1. Clone the Repository

```bash
git clone https://github.com/hrishabh6/karate-scala_learning.git
cd karate-scala_learning
```

#### 2. Verify Java and Maven Installation

```bash
java -version
# Expected output: openjdk version "17.x.x" or higher

mvn -version
# Expected output: Apache Maven 3.8.x or higher
```

#### 3. Run Application Locally with H2 Database

```bash
# Using Maven Spring Boot plugin
mvn spring-boot:run -Dspring.profiles.active=dev

# Application starts on http://localhost:8080
# H2 Console available at http://localhost:8080/h2-console
```

**H2 Console Connection Details**:
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave empty)

#### 4. Run Application with Docker Compose (MySQL)

```bash
# Start MySQL + Spring Boot app
docker-compose -f docker-compose.local.yml up -d

# View logs
docker-compose -f docker-compose.local.yml logs -f app

# Check application health
curl http://localhost:8080/actuator/health

# Stop containers
docker-compose -f docker-compose.local.yml down
```

#### 5. Run Karate API Tests

```bash
# Run all Karate tests with H2 database
mvn clean test -Dspring.profiles.active=test -Dgatling.skip=true

# Run specific test class
mvn test -Dtest=UsersRunner -Dspring.profiles.active=test

# View test reports
open target/karate-reports/karate-summary.html
```

#### 6. Run Gatling Load Tests Locally

```bash
# First, start the application
mvn spring-boot:run -Dspring.profiles.active=dev

# In a new terminal, run Gatling tests
mvn gatling:test -DbaseUrl=http://localhost:8080

# View Gatling reports
# Reports location: target/gatling/<simulation-name>-<timestamp>/index.html
```

#### 7. Test Individual API Endpoints

```bash
# Create a product
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Laptop","price":999.99}'

# Get all products
curl http://localhost:8080/api/products

# Get product by ID
curl http://localhost:8080/api/products/1

# Update product price
curl -X PUT http://localhost:8080/api/products/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Laptop","price":899.99}'
```

---

## 🖥️ VM Infrastructure Setup

### VM-1: Application Server Setup

**System Requirements**:
```
OS: Ubuntu 22.04 LTS
RAM: 4GB minimum (8GB recommended)
CPU: 2 cores
Disk: 20GB
Network: Bridged or NAT with port forwarding
IP: 192.168.1.100 (static recommended)
```

**Installation Steps**:

```bash
# 1. Update system
sudo apt update && sudo apt upgrade -y

# 2. Install Java 17
sudo apt install -y openjdk-17-jdk
java -version

# 3. Install Docker
sudo apt install -y docker.io
sudo systemctl enable docker
sudo systemctl start docker
sudo usermod -aG docker $USER

# 4. Install Docker Compose
sudo apt install -y docker-compose

# 5. Create project directory
mkdir -p ~/app-deployment
cd ~/app-deployment

# 6. Set up environment file (will be populated by CD workflow)
touch .env

# 7. Verify Docker installation
docker --version
docker-compose --version

# 8. Test Docker access (logout and login again after usermod)
docker ps

# 9. Configure firewall (if UFW is enabled)
sudo ufw allow 8080/tcp
sudo ufw allow 22/tcp
```

**Network Configuration**:

```bash
# Set static IP (edit netplan configuration)
sudo nano /etc/netplan/00-installer-config.yaml

# Add/modify:
network:
  ethernets:
    enp0s3:  # Your interface name
      dhcp4: no
      addresses:
        - 192.168.1.100/24
      gateway4: 192.168.1.1
      nameservers:
        addresses: [8.8.8.8, 8.8.4.4]
  version: 2

# Apply changes
sudo netplan apply

# Verify
ip addr show
```

### VM-2: Load Testing & Monitoring Setup

**System Requirements**:
```
OS: Ubuntu 22.04 LTS
RAM: 4GB minimum
CPU: 2 cores
Disk: 20GB
Network: Must reach VM-1
IP: 192.168.1.101 (static recommended)
```

**Installation Steps**:

```bash
# 1. Update system
sudo apt update && sudo apt upgrade -y

# 2. Install Java 17
sudo apt install -y openjdk-17-jdk

# 3. Install Maven
sudo apt install -y maven
mvn -version

# 4. Install Git
sudo apt install -y git

# 5. Clone project repository
cd ~
git clone https://github.com/hrishabh6/karate-scala_learning.git springboot-load
cd springboot-load

# 6. Test Maven build
mvn clean install -DskipTests

# 7. Install Prometheus
cd ~
wget https://github.com/prometheus/prometheus/releases/download/v2.45.0/prometheus-2.45.0.linux-amd64.tar.gz
tar xvf prometheus-2.45.0.linux-amd64.tar.gz
mv prometheus-2.45.0.linux-amd64 prometheus
cd prometheus

# 8. Configure Prometheus
cat > prometheus.yml << 'EOF'
global:
  scrape_interval: 15s
  evaluation_interval: 15s

scrape_configs:
  - job_name: 'spring-boot-app'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['192.168.1.100:8080']
        labels:
          application: 'loadtest-app'
          environment: 'prod'
EOF

# 9. Create Prometheus systemd service
sudo tee /etc/systemd/system/prometheus.service > /dev/null << 'EOF'
[Unit]
Description=Prometheus
After=network.target

[Service]
Type=simple
User=ubuntu
ExecStart=/home/ubuntu/prometheus/prometheus \
  --config.file=/home/ubuntu/prometheus/prometheus.yml \
  --storage.tsdb.path=/home/ubuntu/prometheus/data

[Install]
WantedBy=multi-user.target
EOF

# 10. Start Prometheus
sudo systemctl daemon-reload
sudo systemctl enable prometheus
sudo systemctl start prometheus
sudo systemctl status prometheus

# 11. Install Grafana
sudo apt-get install -y apt-transport-https software-properties-common
wget -q -O - https://packages.grafana.com/gpg.key | sudo apt-key add -
echo "deb https://packages.grafana.com/oss/deb stable main" | sudo tee /etc/apt/sources.list.d/grafana.list
sudo apt-get update
sudo apt-get install -y grafana

# 12. Start Grafana
sudo systemctl enable grafana-server
sudo systemctl start grafana-server
sudo systemctl status grafana-server

# 13. Verify installations
# Prometheus: http://192.168.1.101:9090
# Grafana: http://192.168.1.101:3000 (default login: admin/admin)

# 14. Test connectivity to VM-1
curl http://192.168.1.100:8080/actuator/health
curl http://192.168.1.100:8080/actuator/prometheus

# 15. Configure firewall
sudo ufw allow 9090/tcp  # Prometheus
sudo ufw allow 3000/tcp  # Grafana
sudo ufw allow 22/tcp    # SSH
```

**Post-Installation Configuration**:

1. **Access Grafana** at `http://192.168.1.101:3000`
   - Default credentials: `admin` / `admin`
   - Change password on first login

2. **Add Prometheus Data Source in Grafana**:
   ```
   Configuration → Data Sources → Add data source
   Type: Prometheus
   URL: http://localhost:9090
   Access: Server (default)
   Click "Save & Test"
   ```

3. **Import Pre-built Dashboards**:
   - JVM (Micrometer): Dashboard ID `4701`
   - Spring Boot: Dashboard ID `12900`
   - System Metrics: Dashboard ID `1860`

### SSH Configuration Between Components

**On GitHub Runner Machine**:

```bash
# Generate SSH key pair
ssh-keygen -t ed25519 -C "github-runner" -f ~/.ssh/vm_deploy_key

# Copy public key to both VMs
ssh-copy-id -i ~/.ssh/vm_deploy_key.pub ubuntu@192.168.1.100
ssh-copy-id -i ~/.ssh/vm_deploy_key.pub ubuntu@192.168.1.101

# Test connections
ssh -i ~/.ssh/vm_deploy_key ubuntu@192.168.1.100 "echo 'VM-1 connected'"
ssh -i ~/.ssh/vm_deploy_key ubuntu@192.168.1.101 "echo 'VM-2 connected'"

# Add private key to GitHub Secrets as VM_SSH_KEY
cat ~/.ssh/vm_deploy_key
```

### Network Connectivity Verification

```bash
# From GitHub Runner → VM-1
ping -c 4 192.168.1.100
ssh ubuntu@192.168.1.100 "docker ps"

# From GitHub Runner → VM-2
ping -c 4 192.168.1.101
ssh ubuntu@192.168.1.101 "mvn --version"

# From VM-2 → VM-1 (load testing path)
ssh ubuntu@192.168.1.101
curl -v http://192.168.1.100:8080/api/products
curl http://192.168.1.100:8080/actuator/prometheus
```

---

## 🔄 CI/CD Pipeline Deep Dive

### CI Pipeline: Build & Test (`ci.yml`)

**Trigger Events**:
- Push to `main` branch
- Pull request to `main` branch
- Manual trigger (workflow_dispatch)

**Pipeline Stages**:

```yaml
1. Checkout Code
   └── actions/checkout@v4
   
2. Verify Java Environment
   └── java -version && javac -version
   
3. Run Maven Tests (H2 Database)
   └── mvn -B -Dspring.profiles.active=test -Dgatling.skip=true test
   └── Uses H2 in-memory database
   └── Runs all Karate feature files
   └── Skips Gatling load tests
   
4. Build Production JAR
   └── mvn -B -DskipTests -Dspring.profiles.active=prod package
   └── Produces target/myproject-1.0-SNAPSHOT.jar
   
5. Login to GitHub Container Registry
   └── docker/login-action@v3
   └── Uses GITHUB_TOKEN for authentication
   
6. Build Docker Image
   └── docker build -t ghcr.io/hrishabh6/karate-scala_learning/springboot-crud:TAG .
   └── Tags: commit SHA + latest
   
7. Push to GHCR
   └── docker push (both tags)
   └── Image available for CD workflow
```

**Key Configuration Details**:

```bash
# Maven command breakdown
mvn -B                                    # Batch mode (non-interactive)
    -Dspring.profiles.active=test        # Use test profile (H2 database)
    -Dgatling.skip=true                  # Skip Gatling tests
    test                                 # Run test phase

# Why skip Gatling in CI?
# - Load tests are expensive (time & resources)
# - CI should be fast (< 5 minutes)
# - Load tests run separately via manual workflow
# - Focus on functional correctness, not performance
```

**CI Workflow Success Criteria**:
✅ All Karate tests pass  
✅ JAR builds successfully  
✅ Docker image builds and pushes  
✅ No compilation errors  
✅ No test failures

**CI Workflow Failure Scenarios**:
❌ Karate test fails → Pipeline stops, no deployment  
❌ Build errors → Pipeline stops  
❌ Docker build fails → Pipeline stops

### CD Pipeline: Deploy to VM-1 (`cd.yml`)

**Trigger Event**:
- Workflow run completion of "CI - Build & Test"
- Only if CI concluded successfully
- Skipped if commit message contains `[skip ci]`

**Pipeline Stages**:

```yaml
1. Verify CI Success
   └── if: github.event.workflow_run.conclusion == 'success'
   
2. Checkout Code
   └── Get latest docker-compose.prod.yml
   
3. Setup SSH Access
   └── Create ~/.ssh/id_ed25519 from secret
   └── Set permissions (chmod 600)
   └── Add VM host to known_hosts
   
4. Copy Docker Compose File to VM-1
   └── scp docker-compose.prod.yml to VM-1
   
5. Deploy on VM-1 via SSH
   ├── Export environment variables (DB_USER, DB_PASSWORD, DB_NAME)
   ├── Login to GHCR
   ├── Pull latest Docker image
   ├── docker compose down (stop old containers)
   ├── docker compose up -d (start new containers)
   └── docker image prune -f (cleanup)
```

**Deployment Process Visualization**:

```
┌─────────────────────────────────────────────────────────────┐
│                    CD Workflow Execution                     │
└─────────────────────────────────────────────────────────────┘
                           ▼
┌─────────────────────────────────────────────────────────────┐
│  SSH Connection: GitHub Runner → VM-1 (192.168.1.100)       │
└─────────────────────────────────────────────────────────────┘
                           ▼
┌─────────────────────────────────────────────────────────────┐
│               Execute Commands on VM-1                       │
│                                                              │
│  1. docker login ghcr.io -u <user> -p <token>               │
│  2. docker pull ghcr.io/.../springboot-crud:latest          │
│  3. export DB_USER=app_user                                 │
│  4. export DB_PASSWORD=<secret>                             │
│  5. export DB_NAME=loadtest                                 │
│  6. docker compose down                                     │
│     ├── Stops crud-app container                            │
│     └── Stops mysql-db container                            │
│  7. docker compose up -d                                    │
│     ├── Starts mysql-db (waits for health check)            │
│     └── Starts crud-app (depends on mysql)                  │
│  8. docker image prune -f (remove old images)               │
└─────────────────────────────────────────────────────────────┘
                           ▼
┌─────────────────────────────────────────────────────────────┐
│            Application Deployed Successfully                 │
│         http://192.168.1.100:8080/api/products              │
└─────────────────────────────────────────────────────────────┘
```

**Zero-Downtime Considerations**:

Current implementation has ~5-10 seconds downtime during `docker compose down` → `docker compose up -d`.

For zero-downtime deployment, consider:
- Blue-Green deployment strategy
- Rolling updates with Kubernetes
- Load balancer with multiple instances

### Load Test Pipeline: Gatling Execution (`cd-load-test.yml`)

**Trigger Event**:
- Manual trigger only (`workflow_dispatch`)
- Executed via GitHub Actions UI
- Pre-planned load testing sessions

**Why Manual Trigger?**

```
❌ Auto-trigger on every push:
   - Expensive (CPU/memory intensive)
   - Time-consuming (15-30 minutes per test)
   - Unnecessary for every code change
   - Could overwhelm VM resources

✅ Manual trigger:
   - Run before major releases
   - Scheduled performance testing
   - After significant code changes
   - Controlled testing windows
```

**Pipeline Stages**:

```yaml
1. Setup SSH Access to VM-2
   └── Configure SSH key for load testing VM
   
2. SSH into VM-2 and Execute
   ├── Navigate to ~/springboot-load
   ├── git reset --hard (clean state)
   ├── git pull origin main (latest code)
   ├── Export VM_HOST environment variable
   ├── mvn -B -Dgatling.skip=false gatling:test
   │   └── -DbaseUrl=http://192.168.1.100:8080
   │   └── -Dkarate.env=load
   ├── Read last run ID from target/gatling/lastRun.txt
   ├── Zip the report directory
   └── Leave zipped report in ~/springboot-load/
   
3. SCP Report Back to GitHub Runner
   └── scp gatling-report-*.zip from VM-2
   
4. Upload as GitHub Artifact
   └── Available for download in Actions UI
```

**Load Test Execution Flow**:

```
Manual Trigger
      │
      ▼
GitHub Runner SSH → VM-2
      │
      ▼
VM-2: Git Pull Latest Code
      │
      ▼
VM-2: mvn gatling:test -DbaseUrl=http://VM-1:8080
      │
      ├─→ Gatling reads LoadTestSimulation.scala
      │
      ├─→ Karate-Gatling loads users.feature
      │
      ├─→ Load Pattern Execution:
      │   ├── Ramp 10 users over 10 seconds
      │   └── Sustain 5 users/sec for 20 seconds
      │
      ├─→ HTTP Requests sent to VM-1:8080/api/products
      │   │
      │   └─→ VM-1: Spring Boot handles requests
      │       ├── Database queries
      │       ├── Response generation
      │       └── Metrics recording
      │
      ├─→ Gatling records:
      │   ├── Response times (p50, p95, p99)
      │   ├── Throughput (requests/sec)
      │   ├── Error rate
      │   └── Success rate
      │
      └─→ Generate HTML Report
          └── target/gatling/<timestamp>/index.html
      
      │
      ▼
Report Zipped and Transferred
      │
      ▼
Uploaded to GitHub Artifacts
```

---

## 🧪 Testing Strategy

### Test Pyramid Implementation

```
                    ▲
                   / \
                  /   \
                 /  E2E \          ← Load Tests (Gatling)
                /_______\            Manual, Pre-release
               /         \
              /           \
             / Integration \       ← Karate API Tests
            /   Tests      \        Automated, Every commit
           /_______________\
          /                 \
         /                   \
        /    Unit Tests       \   ← (Not implemented in this project)
       /_______________________\    Would test service layer logic
```

### Karate API Testing

**Feature File Structure** (`users.feature`):

```gherkin
Feature: Product API Testing

  Background:
    * url baseUrl
    * def productData = 
      """
      {
        "name": "Test Product",
        "price": 99.99
      }
      """

  Scenario: Health Check
    Given path '/actuator/health'
    When method GET
    Then status 200
    And match response.status == 'UP'

  Scenario: Create a product
    Given path '/api/products'
    And request productData
    When method POST
    Then status 200
    And match response.id == '#notnull'
    And match response.name == productData.name
    And match response.price == productData.price

  Scenario: Get all products
    Given path '/api/products'
    When method GET
    Then status 200
    And match response == '#[]'
    And match each response == { id: '#number', name: '#string', price: '#number' }

  Scenario: Get product by ID
    # First create a product
    Given path '/api/products'
    And request productData
    When method POST
    Then status 200
    * def productId = response.id

    # Then retrieve it
    Given path '/api/products/' + productId
    When method GET
    Then status 200
    And match response.id == productId

  Scenario: Update product price
    # Create a product
    Given path '/api/products'
    And request productData
    When method POST
    Then status 200
    * def productId = response.id

    # Update the price
    * def updateData = { "name": "Updated Product", "price": 149.99 }
    Given path '/api/products/' + productId
    And request updateData
    When method PUT
    Then status 200
    And match response.price == 149.99

  Scenario: Error handling - Get non-existent product
    Given path '/api/products/99999'
    When method GET
    Then status 404
```

**Karate Runner Configuration** (`UsersRunner.java`):

```java
@SpringBootTest(
    classes = LoadtestApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
public class UsersRunner {

    @LocalServerPort
    int port;  // Spring Boot assigns random port

    @Karate.Test
    Karate testUsers() {
        return Karate.run("users")
            .systemProperty("local.server.port", String.valueOf(port))
            .relativeTo(getClass());
    }
}
```

**How Port Detection Works**:

```
1. Spring Boot starts on RANDOM_PORT
   └── Example: 54321

2. @LocalServerPort injects port into runner
   └── port = 54321

3. Runner passes port as system property
   └── System.setProperty("local.server.port", "54321")

4. karate-config.js reads the property
   └── var localPort = karate.properties['local.server.port'];
   └── baseUrl = 'http://localhost:54321'

5. Karate tests use baseUrl
   └── All API calls go to http://localhost:54321/api/products
```

**Dynamic Configuration** (`karate-config.js`):

```javascript
function fn() {
    var env = karate.env || 'dev';
    
    // Priority 1: Explicit baseUrl from Maven/Gatling
    var baseUrl = karate.properties['baseUrl'];
    
    // Priority 2: Gatling port-based execution
    if (!baseUrl) {
        var port = karate.properties['karate.port'];
        if (port) baseUrl = 'http://localhost:' + port;
    }
    
    // Priority 3: Spring Boot test random port
    if (!baseUrl) {
        var localPort = karate.properties['local.server.port'];
        if (localPort) baseUrl = 'http://localhost:' + localPort;
    }
    
    // Priority 4: Fallback to localhost:8080
    if (!baseUrl) baseUrl = 'http://localhost:8080';
    
    karate.log('✅ Using baseUrl:', baseUrl);
    return { env: env, baseUrl: baseUrl };
}
```

### Load Testing with Gatling

**Simulation Configuration** (`LoadTestSimulation.scala`):

```scala
class LoadTestSimulation extends Simulation {

  // Read baseUrl from Maven command line
  val baseUrl: String = System.getProperty("baseUrl", "http://localhost:8080")

  val protocol: KarateProtocol = karateProtocol(
    "/api/products" -> Nil
  )

  val crudTest: ScenarioBuilder = scenario("CRUD Operations Performance Test")
    .exec(karateFeature("classpath:examples/users/users.feature"))

  setUp(
    crudTest.inject(
      rampUsers(10).during(10),        // Ramp up 10 users over 10 seconds
      constantUsersPerSec(5).during(20) // Then 5 users/sec for 20 seconds
    )
  ).protocols(protocol)
}
```

**Load Pattern Explanation**:

```
Users
  │
10│                    ┌─────────────────────────
  │                   /
  │                  /
  │                 /
  │                /
  │               /
  │              /
  │             /
  │            /
  │           /
 0└──────────┴────────────────────────────────────→ Time (seconds)
   0        10         20         30         40

   ├─ Ramp Up ─┤──── Sustained Load ────────┤

Phase 1 (0-10s):  Gradually add users (rampUsers)
Phase 2 (10-30s): Constant load of 5 requests/sec

Total Duration: ~30 seconds
Total Users: 10 concurrent users
Total Requests: ~100-150 requests
```

**Running Load Tests**:

```bash
# From VM-2 (during manual workflow)
cd ~/springboot-load
mvn -B -Dgatling.skip=false gatling:test \
    -DbaseUrl=http://192.168.1.100:8080 \
    -Dkarate.env=load

# Report generated at:
# target/gatling/loadtestsimulation-<timestamp>/index.html
```

**Gatling Report Metrics**:

| Metric | Description | Good Threshold |
|--------|-------------|----------------|
| **Requests/sec** | Throughput | > 50 req/s |
| **Mean Response Time** | Average latency | < 200ms |
| **95th Percentile** | Response time for 95% of requests | < 500ms |
| **99th Percentile** | Response time for 99% of requests | < 1000ms |
| **Error Rate** | Failed requests percentage | < 1% |
| **Std Deviation** | Response time consistency | Lower is better |

---

## 📊 Monitoring Stack

### Prometheus Configuration

**prometheus.yml** (on VM-2):

```yaml
global:
  scrape_interval: 15s      # Scrape targets every 15 seconds
  evaluation_interval: 15s  # Evaluate rules every 15 seconds
  external_labels:
    cluster: 'local-vm'
    env: 'production'

# Scrape configurations
scrape_configs:
  - job_name: 'spring-boot-app'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['192.168.1.100:8080']
        labels:
          application: 'loadtest-app'
          environment: 'prod'
          instance: 'vm-1'
```

**Key Prometheus Queries**:

```promql
# HTTP Request Rate (requests per second)
rate(http_server_requests_seconds_count[5m])

# Average Response Time
rate(http_server_requests_seconds_sum[5m]) / rate(http_server_requests_seconds_count[5m])

# JVM Heap Memory Usage (%)
(jvm_memory_used_bytes{area="heap"} / jvm_memory_max_bytes{area="heap"}) * 100

# CPU Usage
system_cpu_usage * 100

# Database Connection Pool Usage
hikaricp_connections_active / hikaricp_connections_max

# Error Rate
rate(http_server_requests_seconds_count{status=~"5.."}[5m])
```

### Spring Boot Actuator Configuration

**application-prod.properties**:

```properties
# Actuator Endpoints
management.endpoints.web.exposure.include=health,info,prometheus,metrics
management.endpoint.health.show-details=always
management.endpoint.prometheus.enabled=true

# Metrics Export
management.metrics.export.prometheus.enabled=true
management.metrics.distribution.percentiles-histogram.http.server.requests=true

# Application Info
management.info.env.enabled=true
info.app.name=@project.artifactId@
info.app.version=@project.version@
info.app.java.version=@java.version@
```

**pom.xml Dependencies**:

```xml
<!-- Spring Boot Actuator -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<!-- Micrometer Prometheus Registry -->
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
    <scope>runtime</scope>
</dependency>
```

### Grafana Dashboard Configuration

**Dashboard Panels**:

1. **JVM Metrics Panel**:
   - Heap Memory Usage (used vs max)
   - Non-Heap Memory Usage
   - GC Pause Time
   - Thread Count (live, peak, daemon)

2. **System Metrics Panel**:
   - CPU Usage (%)
   - System Load Average
   - Disk I/O
   - Network Traffic

3. **HTTP Request Metrics Panel**:
   - Request Rate (req/s)
   - Average Response Time
   - 95th Percentile Response Time
   - Error Rate (4xx, 5xx)

4. **Database Metrics Panel**:
   - Active Connections
   - Idle Connections
   - Connection Wait Time
   - Query Execution Time

**Sample Grafana Panel JSON** (HTTP Request Rate):

```json
{
  "title": "HTTP Request Rate",
  "targets": [
    {
      "expr": "rate(http_server_requests_seconds_count{application=\"loadtest-app\"}[5m])",
      "legendFormat": "{{method}} {{uri}}"
    }
  ],
  "yaxes": [
    {
      "label": "Requests/sec",
      "format": "reqps"
    }
  ]
}
```

### Accessing Monitoring Tools

**Prometheus**:
```
URL: http://192.168.1.101:9090
Features:
- Query metrics manually
- View targets status
- Check scrape health
- Test PromQL queries
```

**Grafana**:
```
URL: http://192.168.1.101:3000
Default Credentials: admin / admin
Features:
- Pre-built dashboards
- Custom visualizations
- Alert configuration
- Variable templating
```

---

## ⚙️ Configuration Management

### Multi-Environment Strategy

```
Environment Profiles:
├── dev     → Local development (H2 database)
├── test    → CI testing (H2 database)
└── prod    → Production deployment (MySQL database)
```

### Profile-Specific Configurations

**application.yml** (Base Configuration):

```yaml
spring:
  application:
    name: loadtest
  jpa:
    open-in-view: false
    properties:
      hibernate:
        format_sql: true

server:
  port: 8080

management:
  endpoints:
    web:
      exposure:
        include: health,info,prometheus
```

**application-dev.yml** (Development):

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password: ""
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  h2:
    console:
      enabled: true
      path: /h2-console

logging:
  level:
    com.example.loadtest: DEBUG
```

**application-test.yml** (CI Testing):

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
    driver-class-name: org.h2.Driver
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    database-platform: org.hibernate.dialect.H2Dialect
```

**application-prod.properties** (Production):

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://mysql:3306/loadtest
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Connection Pool
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=30000
```

### Docker Compose Configurations

**docker-compose.local.yml** (Local Development):

```yaml
version: "3.8"

services:
  mysql:
    image: mysql:8.0
    container_name: mysql-db
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: loadtest
      MYSQL_USER: app_user
      MYSQL_PASSWORD: app_pass
    ports:
      - "3307:3306"
    volumes:
      - mysql-data:/var/lib/mysql
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      interval: 10s
      retries: 5

  app:
    image: ghcr.io/hrishabh6/karate-scala_learning/springboot-crud:latest
    container_name: crud-app
    restart: always
    depends_on:
      mysql:
        condition: service_healthy
    ports:
      - "8080:8080"
    environment:
      SPRING_PROFILES_ACTIVE: prod
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/loadtest
      SPRING_DATASOURCE_USERNAME: app_user
      SPRING_DATASOURCE_PASSWORD: app_pass

volumes:
  mysql-data:
```

**docker-compose.prod.yml** (Production on VM-1):

```yaml
version: "3.8"

services:
  mysql:
    image: mysql:8.0
    container_name: mysql-db
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: ${DB_PASSWORD}
      MYSQL_DATABASE: ${DB_NAME}
      MYSQL_USER: ${DB_USER}
      MYSQL_PASSWORD: ${DB_PASSWORD}
    ports:
      - "3307:3306"
    volumes:
      - mysql-data:/var/lib/mysql
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      interval: 10s
      retries: 5

  app:
    image: ghcr.io/hrishabh6/karate-scala_learning/springboot-crud:latest
    container_name: crud-app
    restart: always
    depends_on:
      mysql:
        condition: service_healthy
    ports:
      - "8080:8080"
    environment:
      SPRING_PROFILES_ACTIVE: prod
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/${DB_NAME}
      SPRING_DATASOURCE_USERNAME: ${DB_USER}
      SPRING_DATASOURCE_PASSWORD: ${DB_PASSWORD}

volumes:
  mysql-data:
```

### GitHub Secrets Configuration

```bash
# VM Access Secrets
VM_HOST=192.168.1.100
VM_USER=ubuntu
VM_SSH_KEY=<private_key_content>

# Load Testing VM Secrets
LOAD_SERVER_IP=192.168.1.101
LOAD_VM_USER=ubuntu

# Database Secrets
DB_NAME=loadtest
DB_USER=app_user
DB_PASSWORD=<secure_password>

# GitHub Container Registry (automatic)
GITHUB_TOKEN=<automatically_provided>
```

### Maven POM Configuration Highlights

**pom.xml** (Key Sections):

```xml
<properties>
    <java.version>17</java.version>
    <karate.version>1.5.0</karate.version>
    <gatling.plugin.version>4.10.1</gatling.plugin.version>
    <gatling.skip>true</gatling.skip>  <!-- Default: skip Gatling -->
</properties>

<dependencies>
    <!-- Karate for API Testing -->
    <dependency>
        <groupId>io.karatelabs</groupId>
        <artifactId>karate-junit5</artifactId>
        <version>${karate.version}</version>
        <scope>test</scope>
    </dependency>

    <!-- Karate-Gatling Integration -->
    <dependency>
        <groupId>io.karatelabs</groupId>
        <artifactId>karate-gatling</artifactId>
        <version>${karate.version}</version>
        <scope>test</scope>
    </dependency>

    <!-- H2 Database for Testing -->
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>test</scope>
    </dependency>

    <!-- MySQL for Production -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>

<build>
    <plugins>
        <!-- Gatling Maven Plugin -->
        <plugin>
            <groupId>io.gatling</groupId>
            <artifactId>gatling-maven-plugin</artifactId>
            <version>${gatling.plugin.version}</version>
            <configuration>
                <skip>${gatling.skip}</skip>
            </configuration>
        </plugin>

        <!-- Scala Maven Plugin (for Gatling simulations) -->
        <plugin>
            <groupId>net.alchim31.maven</groupId>
            <artifactId>scala-maven-plugin</artifactId>
            <version>4.5.6</version>
            <executions>
                <execution>
                    <goals>
                        <goal>testCompile</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

---

## 🔧 Key Implementation Details

### Why H2 Database in CI?

**Problem**: Running full MySQL in GitHub Runner is resource-intensive

```
❌ MySQL in CI:
   - Requires Docker container or service
   - 256MB+ memory overhead
   - Slow startup time (10-15 seconds)
   - Complex networking configuration
   - Connection pool management

✅ H2 Database in CI:
   - In-memory (zero setup)
   - Instant startup (<1 second)
   - Minimal memory footprint
   - No external dependencies
   - SQL-compatible for testing
```

**Configuration Switch**:

```bash
# CI Command (uses application-test.yml with H2)
mvn -Dspring.profiles.active=test test

# Production (uses application-prod.properties with MySQL)
SPRING_PROFILES_ACTIVE=prod java -jar app.jar
```

### Gatling Skip Flag Mechanism

**Why Skip Gatling in Regular Tests?**

```
Regular Test Run (CI):
├── Duration: 2-3 minutes
├── Focus: Functional correctness
└── Command: mvn -Dgatling.skip=true test

Load Test Run (Manual):
├── Duration: 15-30 minutes
├── Focus: Performance characteristics
└── Command: mvn -Dgatling.skip=false gatling:test
```

**Implementation**:

```xml
<!-- In pom.xml -->
<properties>
    <gatling.skip>true</gatling.skip>  <!-- Default: skip -->
</properties>

<plugin>
    <groupId>io.gatling</groupId>
    <artifactId>gatling-maven-plugin</artifactId>
    <configuration>
        <skip>${gatling.skip}</skip>
    </configuration>
</plugin>
```

```bash
# Override from command line
mvn test -Dgatling.skip=false
```

### Base URL Resolution Logic

**Multi-Layer Configuration** in `karate-config.js`:

```javascript
function fn() {
    var baseUrl = null;
    
    // Layer 1: Explicit Maven/Gatling parameter
    // Usage: mvn test -DbaseUrl=http://192.168.1.100:8080
    baseUrl = karate.properties['baseUrl'];
    if (baseUrl) {
        karate.log('✅ Using baseUrl from Maven property');
        return { baseUrl: baseUrl };
    }
    
    // Layer 2: Gatling port-based execution
    // Usage: Gatling sets karate.port system property
    var port = karate.properties['karate.port'];
    if (port) {
        baseUrl = 'http://localhost:' + port;
        karate.log('✅ Using baseUrl from Gatling port:', port);
        return { baseUrl: baseUrl };
    }
    
    // Layer 3: Spring Boot Test random port
    // Usage: @LocalServerPort in UsersRunner.java
    var localPort = karate.properties['local.server.port'];
    if (localPort) {
        baseUrl = 'http://localhost:' + localPort;
        karate.log('✅ Using baseUrl from Spring Boot test port:', localPort);
        return { baseUrl: baseUrl };
    }
    
    // Layer 4: Default fallback
    baseUrl = 'http://localhost:8080';
    karate.log('⚠️  Using default baseUrl:', baseUrl);
    return { baseUrl: baseUrl };
}
```

**Usage Scenarios**:

| Scenario | Port Source | Base URL |
|----------|-------------|----------|
| Local Dev | Default | http://localhost:8080 |
| CI Test | Spring Boot random port | http://localhost:54321 |
| Gatling Local | Karate port | http://localhost:8080 |
| Gatling VM-2 | Maven parameter | http://192.168.1.100:8080 |

### Docker Image Build Process

**Dockerfile**:

```dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the Spring Boot JAR
COPY target/myproject-1.0-SNAPSHOT.jar app.jar

# Expose application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**Build and Push Flow**:

```bash
# 1. Build JAR file
mvn clean package -DskipTests

# 2. Build Docker image with two tags
docker build -t ghcr.io/hrishabh6/karate-scala_learning/springboot-crud:${GITHUB_SHA} .
docker tag ghcr.io/hrishabh6/karate-scala_learning/springboot-crud:${GITHUB_SHA} \
           ghcr.io/hrishabh6/karate-scala_learning/springboot-crud:latest

# 3. Push both tags to GHCR
docker push ghcr.io/hrishabh6/karate-scala_learning/springboot-crud:${GITHUB_SHA}
docker push ghcr.io/hrishabh6/karate-scala_learning/springboot-crud:latest
```

**Why Two Tags?**

- **Commit SHA tag**: Immutable, traceable to specific commit
- **Latest tag**: Always points to most recent build, used in production

### Gatling Report Collection

**Challenge**: Gatling generates reports with timestamp-based directory names

```
target/gatling/
├── loadtestsimulation-20241226103045/
├── loadtestsimulation-20241226143210/
└── lastRun.txt  ← Contains: loadtestsimulation-20241226143210
```

**Solution** in `cd-load-test.yml`:

```bash
# Read the last run ID from Gatling's pointer file
RUN_ID=$(cat target/gatling/lastRun.txt | tr -d '\n')
LATEST_RUN_DIR="target/gatling/$RUN_ID"

# Create timestamped zip file
TIMESTAMP=$(date +"%Y-%m-%d_%H-%M-%S")
REPORT_NAME="gatling-report-$TIMESTAMP.zip"

# Zip the specific run directory
zip -r "$REPORT_NAME" "$LATEST_RUN_DIR"
```

### MySQL Container Health Check

**Problem**: Application container starts before MySQL is ready

**Solution**: Docker Compose `depends_on` with health check

```yaml
mysql:
  healthcheck:
    test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
    interval: 10s
    retries: 5

app:
  depends_on:
    mysql:
      condition: service_healthy  # ✅ Waits for MySQL to be healthy
```

**Startup Sequence**:

```
1. docker compose up -d
2. Start mysql container
3. Run health check every 10s
4. After 5 successful pings, MySQL marked as healthy
5. Start app container
6. App connects to MySQL successfully
```

---

## 🐛 Troubleshooting Guide

### Common Issues and Solutions

#### Issue 1: Karate Tests Fail in CI

**Symptoms**:
```
Error: Connection refused to http://localhost:8080
```

**Root Cause**: Spring Boot not starting or wrong port

**Solution**:
```bash
# Verify Spring Boot test configuration
1. Check @SpringBootTest annotation has RANDOM_PORT
2. Verify @LocalServerPort is injected
3. Confirm system property is passed to Karate
4. Check karate-config.js priority logic
```

#### Issue 2: Docker Compose Fails on VM-1

**Symptoms**:
```
Error: Cannot connect to the Docker daemon
```

**Solution**:
```bash
# Check Docker service
sudo systemctl status docker
sudo systemctl start docker

# Verify Docker access
docker ps

# If permission denied
sudo usermod -aG docker $USER
# Logout and login again
```

#### Issue 3: Gatling Cannot Reach VM-1

**Symptoms**:
```
Error: Connection refused to http://192.168.1.100:8080
```

**Solution**:
```bash
# From VM-2, test connectivity
ping 192.168.1.100
curl http://192.168.1.100:8080/actuator/health

# Check firewall on VM-1
sudo ufw status
sudo ufw allow 8080/tcp

# Verify application is running
ssh ubuntu@192.168.1.100 "docker ps"
```

#### Issue 4: Prometheus Not Scraping Metrics

**Symptoms**:
```
Target down in Prometheus UI
```

**Solution**:
```bash
# From VM-2, test metrics endpoint
curl http://192.168.1.100:8080/actuator/prometheus

# Check Prometheus config
cat ~/prometheus/prometheus.yml

# Restart Prometheus
sudo systemctl restart prometheus

# View Prometheus logs
sudo journalctl -u prometheus -f
```

#### Issue 5: GitHub Actions SSH Fails

**Symptoms**:
```
Permission denied (publickey)
```

**Solution**:
```bash
# Regenerate SSH keys
ssh-keygen -t ed25519 -f ~/.ssh/vm_key

# Copy to VMs
ssh-copy-id -i ~/.ssh/vm_key.pub ubuntu@192.168.1.100

# Update GitHub Secret VM_SSH_KEY with private key content
cat ~/.ssh/vm_key

# Test manually
ssh -i ~/.ssh/vm_key ubuntu@192.168.1.100 "echo success"
```

#### Issue 6: MySQL Container Won't Start

**Symptoms**:
```
Error: MySQL container exits immediately
```

**Solution**:
```bash
# Check logs
docker logs mysql-db

# Common fixes:
# 1. Remove old volume
docker compose down -v
docker volume rm <project>_mysql-data

# 2. Check environment variables
echo $DB_PASSWORD
echo $DB_NAME

# 3. Restart with fresh state
docker compose up -d --force-recreate
```

### Debugging Commands

```bash
# Check GitHub Runner status
sudo ./svc.sh status

# View Docker container logs
docker logs crud-app --tail 100 -f

# Check application health
curl http://192.168.1.100:8080/actuator/health | jq

# View Prometheus targets
curl http://192.168.1.101:9090/api/v1/targets | jq

# Test Karate configuration locally
mvn test -Dtest=UsersRunner -Dspring.profiles.active=test

# Run single Gatling simulation
mvn gatling:test -Dgatling.simulationClass=simulations.LoadTestSimulation
```

---

## 📈 Performance Metrics

### Expected Performance Benchmarks

| Metric | Good | Acceptable | Poor |
|--------|------|------------|------|
| **Mean Response Time** | < 100ms | 100-200ms | > 200ms |
| **95th Percentile** | < 200ms | 200-500ms | > 500ms |
| **Throughput** | > 100 req/s | 50-100 req/s | < 50 req/s |
| **Error Rate** | 0% | < 1% | > 1% |
| **CPU Usage** | < 50% | 50-70% | > 70% |
| **Memory Usage** | < 70% | 70-85% | > 85% |

### Sample Gatling Results

```
================================================================================
---- Global Information --------------------------------------------------------
> request count                                        150 (OK=150    KO=0     )
> min response time                                     45 (OK=45     KO=-     )
> max response time                                    210 (OK=210    KO=-     )
> mean response time                                    95 (OK=95     KO=-     )
> std deviation                                         35 (OK=35     KO=-     )
> response time 50th percentile                         88 (OK=88     KO=-     )
> response time 75th percentile                        115 (OK=115    KO=-     )
> response time 95th percentile                        165 (OK=165    KO=-     )
> response time 99th percentile                        195 (OK=195    KO=-     )
> mean requests/sec                                      5 (OK=5      KO=-     )
================================================================================
```

### Grafana Dashboard Metrics

**JVM Heap Memory**:
```
Used: 512 MB / 2048 MB (25%)
GC Pause Time: 15ms avg
```

**System Resources**:
```
CPU Usage: 35%
Load Average: 1.2, 1.0, 0.9
Network I/O: 5 MB/s
```

**HTTP Metrics**:
```
Request Rate: 75 req/s
Error Rate: 0.2%
Average Response Time: 95ms
```

---

## 🚀 Future Enhancements

### Short-Term Improvements

1. **Add More Test Scenarios**
   - Edge case testing (boundary values)
   - Negative testing (invalid inputs)
   - Security testing (SQL injection, XSS)

2. **Enhance Monitoring**
   - Set up Grafana alerts
   - Add custom business metrics
   - Implement distributed tracing (Zipkin/Jaeger)

3. **Improve CI/CD**
   - Add code quality checks (SonarQube)
   - Implement security scanning (Snyk, Trivy)
   - Add changelog generation

### Medium-Term Improvements

4. **Kubernetes Migration**
   - Convert Docker Compose to K8s manifests
   - Implement rolling updates
   - Add horizontal pod autoscaling

5. **Database Improvements**
   - Add database migration tool (Flyway/Liquibase)
   - Implement read replicas
   - Add connection pool monitoring

6. **Advanced Load Testing**
   - Spike tests (sudden traffic surge)
   - Soak tests (sustained load over hours)
   - Stress tests (find breaking point)

### Long-Term Vision

7. **Multi-Region Deployment**
   - Deploy to actual AWS regions
   - Implement global load balancing
   - Add CDN for static assets

8. **Observability Enhancements**
   - Centralized logging (ELK stack)
   - APM integration (New Relic, Datadog)
   - User behavior analytics

9. **Chaos Engineering**
   - Implement Chaos Monkey
   - Test failure scenarios
   - Validate disaster recovery

---

## 📚 Lessons Learned

### Technical Insights

1. **Resource Isolation Matters**
   - Running load tests on the same server as the application skews metrics
   - Separate VMs provide realistic performance data
   - Network latency between VMs simulates real-world conditions

2. **Configuration Management is Critical**
   - Multi-profile strategy prevents environment-specific bugs
   - Secrets management (GitHub Secrets) keeps credentials secure
   - Environment variables make deployments flexible

3. **Testing Pyramid is Real**
   - Fast feedback from unit tests (not implemented here, but should be)
   - API tests catch integration issues
   - Load tests validate performance characteristics
   - Each layer has different cost/benefit tradeoffs

4. **Docker Simplifies Deployment**
   - Consistent environments across dev/test/prod
   - Easy rollback with image tags
   - Health checks ensure proper startup sequencing

5. **Monitoring Enables Optimization**
   - Can't improve what you don't measure
   - Prometheus + Grafana provide deep insights
   - Real-time dashboards help debug issues quickly

### DevOps Skills Acquired

✅ **CI/CD Pipeline Design** - Automated build, test, deploy workflows  
✅ **Infrastructure as Code** - Docker Compose, configuration files  
✅ **Networking** - VM-to-VM communication, port forwarding  
✅ **Monitoring & Observability** - Metrics collection, visualization  
✅ **Security** - SSH key management, secrets handling  
✅ **Problem Solving** - Debugging distributed systems

### Best Practices Identified

1. **Always use health checks** - Prevents premature traffic routing
2. **Tag Docker images properly** - SHA tags for traceability
3. **Separate concerns** - Different VMs for different purposes
4. **Automate repetitive tasks** - Manual steps lead to errors
5. **Document everything** - Future you will thank present you
6. **Test in production-like environments** - Catch environment-specific issues

---

## 🤝 Contributing

This is a learning project, but contributions are welcome!

```bash
# Fork the repository
git clone https://github.com/your-username/karate-scala_learning.git

# Create feature branch
git checkout -b feature/your-feature-name

# Make changes and commit
git commit -m "Add: your feature description"

# Push and create pull request
git push origin feature/your-feature-name
```

---

## 📝 License

This project is for educational purposes. Feel free to use it as a reference for your own learning.

---

## 🙏 Acknowledgments

- **Karate Framework** - For amazing API testing capabilities
- **Gatling** - For powerful load testing features
- **Spring Boot** - For making Java development enjoyable
- **Prometheus & Grafana** - For excellent monitoring tools
- **GitHub Actions** - For free CI/CD automation

---

## 📧 Contact

For questions or suggestions:
- GitHub: [@hrishabh6](https://github.com/hrishabh6)
- Project: [karate-scala_learning](https://github.com/hrishabh6/karate-scala_learning)

---

**Last Updated**: December 2024  
**Project Status**: ✅ Active Development  
**Deployment**: 🟢 Production-Ready (Local VMs)