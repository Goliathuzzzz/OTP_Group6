# Qupids - Speed Dating

**Qupids Speed Dating** is a locally hosted mock implementation of an **automated speed dating system**. It streamlines event management by automating **participant enrollment, interest selection, matchmaking, and match display**, reducing the inefficiencies of manual speed dating events.

---

## 🌟 Features

- **Automated Enrollment** – Participants register with personal details.
- **Interest-Based Matchmaking** – Matches are determined by shared interests.
- **Match Display** – View pairing results post-session.
- **Admin Controls** – Manage users and matches through a database interface.
- **Testing & Deployment** – JUnit tests, CI/CD pipelines, and Docker support.

---

## 🔧 Technology Stack

### **Frontend**

- JavaFX 23.0.2 – Graphical user interface
- TestFX – GUI testing framework

### **Backend**

- Java 21 – Programming language
- Maven – Dependency & build management
- JPA (Jakarta Persistence API) & hibernate – ORM for database handling
- MariaDB – Relational database
- Mockito – Unit testing
- JaCoCo – Code coverage analysis

### **DevOps & Deployment**

- Docker – Containerization for easy deployment
- Jenkins – CI/CD pipeline automation
- Trello – Agile project management
- GitHub – Version control

---

## 🛂 Project Structure

```
OTP_Group6/
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── context/       # Manages shared data and application state
│   │   │   ├── controller/    # Handles user interactions
│   │   │   ├── dao/           # Database access layer
│   │   │   ├── datasource/    # Manages database connections and persistence
│   │   │   ├── model/         # Database entity models
│   │   │   ├── scheduler/     # Handles scheduled tasks
│   │   │   ├── service/       # Business logic
│   │   │   ├── util/          # Utility functions and helpers
│   │   │   ├── view/          # JavaFX UI components
│   │   │   ├── Main           # Application entry point
│   │   ├── resources/
│   │   │   ├── css/           # Styling files
│   │   │   ├── fxml/          # UI layouts
│   │   │   ├── images/        # Image assets used in UI
│   │   │   ├── META-INF/      # Persistence and metadata configurations
│   │   │   ├── sql/           # Database initialization and scripts
│   ├── test/                  # Unit tests
├── Dockerfile                 # Containerization
├── Jenkinsfile                # CI/CD automation pipeline
├── pom.xml                    # Maven configuration
├── README.md                  # Project documentation
```

---

## 🚀 Getting Started

### Prerequisites

Ensure you have the following installed:

- [Java 21](https://jdk.java.net/21/)
- [Maven](https://maven.apache.org/)
- [MariaDB](https://mariadb.org/)
- [Docker](https://www.docker.com/)

### Setup

1. **Clone the Repository**:

   ```sh
   git clone https://github.com/Goliathuzzzz/OTP_Group6.git
   cd OTP_Group6
   ```

2. **Database Configuration**:

    - Start MariaDB and create a database named `tatskatytot`.
    - Update `persistence.xml` with your database credentials.

3. **Build and Run**:

   ```sh
   mvn clean install
   mvn javafx:run
   ```

---

## 🛠 Usage

1. **Register**: Sign up as a user or guest.
2. **Select interests**: Choose categories such as hobbies, sports, or food preferences.
3. **Matchmaking**: The system pairs participants based on shared interests.
4. **View matches**: Participants see their match results at the end of the session.

---

## ✅ Testing

Run unit tests:

```sh
mvn test
```

Generate a JaCoCo test coverage report:

```sh
mvn jacoco:report
```

---

## ✨ Future Enhancements

- **Mobile App Development** – Build a React Native version.
- **Cloud Deployment** – Move backend to Render, frontend to Vercel.
- **Session Notifications** – SMS/Email reminders for upcoming events.
- **Two-Factor Authentication** – Secure login with OTP/Auth apps.
- **Advanced Admin Dashboard** – Session tracking & analytics.

---

## 🤝 Contributors

- **Ade Aiho**
- **Heta Hartzell**
- **Mika Laakkonen**
- **Jonne Roponen**
