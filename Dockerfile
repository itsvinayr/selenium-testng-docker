# Step 1 - Base Image
FROM maven:3.9.11-eclipse-temurin-21

# Step 2 - Working Directory
WORKDIR /app

# Step 3 - Copy Project
COPY . .

# Step 4 - Download Dependencies
RUN mvn dependency:go-offline

# Step 5 - Default Command
CMD ["mvn","clean","test"]