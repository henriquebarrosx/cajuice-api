# Estágio 1: Compilação (Build)
FROM maven:3.9-eclipse-temurin-25-alpine AS build
WORKDIR /build

# Copia os arquivos de configuração do Maven e o código fonte
COPY pom.xml .
COPY src ./src

# Compila o projeto gerando o JAR e pulando os testes para agilizar
RUN mvn clean package -DskipTests

# Estágio 2: Execução (Run)
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

# Copia o JAR gerado no estágio anterior (Stage 0/Build) para o container final
COPY --from=build /build/target/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]