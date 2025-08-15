# 🎟️ Cinema Ticket Booking System
A Java-based cinema ticket booking application using JavaFX, Maven, Hibernate, and SQL Server.

---

## 🗄 Database Design
![ERD Diagram](docs/ERD_V2.1.png)  
*For the full schema and additional diagrams, see the [docs](docs/) folder.*

---

## 📋 Use Cases
![Use Case Diagram](docs/UseCase.png)  
Detailed descriptions available in [docs](docs/).

---

## 🚀 How to Run
### 1️⃣ Clone the Repository


Recommended (IntelliJ IDEA)
1. Open IntelliJ IDEA → New Project from Version Control.
2. Paste the repo URL:
  `https://github.com/tarek-moh/Cinema-Ticket-booking-System`
3. Click Clone.
4. When prompted, select Load Maven Project.

Alternative (Command Line)
`git clone https://github.com/tarek-moh/Cinema-Ticket-booking-System`

### 2️⃣ Set Up SQL Server
1. Install Microsoft SQL Server and SQL Server Management Studio (SSMS).
2. Create a local SQL Server instance.
   -Enable both Windows Authentication and SQL Server Authentication.
   -Create a username/password for the instance.

### 3️⃣ Create the Database
Run the provided database script:
`cinemaDB.sql`
(This file contains the schema and seed data.)

### 4️⃣ Configure Hibernate Connection
1. Open:
  `src/main/resources/hibernate.cfg.xml`
2. Update the `<property name="hibernate.connection.url">` line:
  `jdbc:sqlserver://localhost;databaseName=<DBname>;user=<Username>;password=<Password>;trustServerCertificate=true`
  
### ✅ You’re ready to run the project!
Use IntelliJ’s Run button or:
  `mvn clean install
   mvn exec:java`

## 📜 License
This project is developed for educational purposes.
  
