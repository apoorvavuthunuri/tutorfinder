# Tutor Application

A Spring Boot application with SQLite database that provides a REST API for managing tutors and a simple web interface to display them.

## Prerequisites

1. **Java Development Kit (JDK) 17**
   - Download and install from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/#java17) or [OpenJDK](https://adoptium.net/)
   - Verify installation:
     ```bash
     java -version
     ```

2. **Maven**
   - Download from [Maven website](https://maven.apache.org/download.cgi)
   - Extract and add to PATH
   - Verify installation:
     ```bash
     mvn -version
     ```

## Project Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd tutor-app
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

## Running the Application

1. **Start the Spring Boot application**
   ```bash
   mvn spring-boot:run
   ```
   The application will start on `http://localhost:8080`

2. **Access the web interface**
   - Open your web browser
   - Navigate to `http://localhost:8080`
   - You should see the tutor catalog with sample data

## API Documentation

### OpenAPI/Swagger Definition

```yaml
openapi: 3.0.0
info:
  title: Tutor API
  version: 1.0.0
  description: API for managing tutors

paths:
  /gettutors:
    get:
      summary: Get all tutors
      description: Returns a list of all tutors
      responses:
        '200':
          description: Successful operation
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Tutor'
        '500':
          description: Internal server error

  /tutor/{id}:
    get:
      summary: Get tutor by ID
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: integer
            format: int64
      responses:
        '200':
          description: Successful operation
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Tutor'
        '404':
          description: Tutor not found
        '500':
          description: Internal server error

  /gettutors/subject/{subject}:
    get:
      summary: Get tutors by subject
      parameters:
        - name: subject
          in: path
          required: true
          schema:
            type: string
      responses:
        '200':
          description: Successful operation
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Tutor'
        '404':
          description: No tutors found for subject
        '500':
          description: Internal server error

components:
  schemas:
    Tutor:
      type: object
      properties:
        id:
          type: integer
          format: int64
        name:
          type: string
        subject:
          type: string
        rating:
          type: number
          format: float
        hourlyRate:
          type: number
          format: float
        description:
          type: string
      required:
        - id
        - name
        - subject
```

## API Endpoints

1. **Get All Tutors**
   - URL: `http://localhost:8080/gettutors`
   - Method: GET
   - Returns: List of all tutors

2. **Get Tutor by ID**
   - URL: `http://localhost:8080/tutor/{id}`
   - Method: GET
   - Example: `http://localhost:8080/tutor/1`

3. **Get Tutors by Subject**
   - URL: `http://localhost:8080/gettutors/subject/{subject}`
   - Method: GET
   - Example: `http://localhost:8080/gettutors/subject/Mathematics`

## Important Notes

1. **Database**
   - The application uses SQLite
   - Database file: `tutor.db` (created automatically)
   - Sample data is inserted on first run

2. **Frontend Development**
   - The frontend files are in `src/main/resources/static/`
   - Main file: `index.html`
   - Changes to frontend files will be reflected when you refresh the browser

3. **Common Issues**
   - If you see "This site can't be reached", make sure:
     - The Spring Boot application is running
     - You're accessing through `http://localhost:8080` (not opening the HTML file directly)
   - If you get database errors, delete `tutor.db` and restart the application

## Development

1. **Project Structure**
   ```
   src/
   ├── main/
   │   ├── java/
   │   │   └── com/
   │   │       └── example/
   │   │           ├── App.java
   │   │           ├── Tutor.java
   │   │           ├── TutorService.java
   │   │           └── GetTutor.java
   │   └── resources/
   │       ├── static/
   │       │   └── index.html
   │       └── application.properties
   └── test/
       └── java/
           └── com/
               └── example/
                   └── AppTest.java
   ```

2. **Making Changes**
   - Backend changes: Modify Java files in `src/main/java/com/example/`
   - Frontend changes: Modify files in `src/main/resources/static/`
   - After changes, rebuild and restart the application

## Troubleshooting

1. **Port already in use**
   - Change port in `src/main/resources/application.properties`:
     ```
     server.port=8080
     ```

2. **Database issues**
   - Delete `tutor.db` and restart the application
   - Check console for SQLite errors

3. **Frontend not updating**
   - Clear browser cache
   - Make sure you're accessing through `http://localhost:8080`
   - Check browser console for errors 