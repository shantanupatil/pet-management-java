# Pet Management App

This project is a Java backend system designed to manage pet vaccination tracking. It includes scheduled jobs, entity management, and integration with messaging and persistence layers.

# Backend File Structure

├── backend/ \
│ ├── src/main/java/com/brady/ \
│ │ ├── config/ # Flyway configuration \
│ │ ├── entities/ # Domain entities and DTOs \
│ │ ├── scheduler/ # Scheduling logic (e.g., for alerts) \
│ │ ├── service/ # Business logic and service interfaces \
│ ├── pom.xml # Maven module descriptor \
│
├── pom.xml # Parent project descriptor \


## Key Features

- **Entity Layer**: Models for Owner, Pet, Vaccination, and User.
- **Service Layer**: Remote service interfaces for distributed access.
- **Scheduler**: Automatically checks for due vaccinations and alerts users.
- **Flyway**: Manages database versioning and schema migration.
- **Maven**: For dependency management and builds.
- **EJB**: For remote service exposure (if configured in WildFly).

# Frontend File Structure

├── frontend/ \
│   ├── pom.xml                                  # Maven module descriptor \
│   ├── src/ \
│   │   ├── main/ \
│   │   │   ├── java/com/brady/ \
│   │   │   │   ├── ui/ \
│   │   │   │   │   ├── dashboard/               # Dashboard screen UI \
│   │   │   │   │   ├── login/                   # Login screen UI \
│   │   │   │   │   ├── owner/                   # Owner management dialog \
│   │   │   │   │   ├── pet/                     # Pet management dialog \
│   │   │   │   │   ├── vaccination/             # Vaccination management dialog \
│   │   │   │   ├── utils/                       # JNDI and utility classes \
│   │   │   ├── resources/ \
│   │   │   │   ├── frontend.jnlp                # Java Web Start configuration \
│   │   │   │   ├── jboss-ejb-client.properties  # JBoss EJB client configuration 


## Frontend Key Features
- **Java Swing UI**: Rich client interface built using Java Swing for interactive user experience.
- **Modular Screens**: Organized into clear sections: \
-- Login Screen (LoginScreen) – for authentication. \
-- Dashboard (DashboardScreen) – high-level overview. \
-- Owner Management (OwnerEditDialog) – CRUD operations for pet owners. \
-- Pet Management (PetEditDialog) – manage pet details. \
-- Vaccination Management (VaccineEditDialog) – input and update vaccination data. \

- **EJB Integration via JNDI**: Uses JNDIUtil.java to perform remote calls to backend services deployed on WildFly or another JEE container.

- The standalone-full file can be found in the root of the repository.
