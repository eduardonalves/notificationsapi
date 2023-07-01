FROM maven:3.8.1-openjdk-17-slim AS build
WORKDIR /notifications

# Copiar o arquivo pom.xml para o diretório de trabalho
COPY pom.xml .

# Baixar as dependências do Maven
RUN mvn dependency:go-offline

# Copiar o código-fonte para o diretório de trabalho
COPY src ./src

# Compilar o projeto
RUN mvn clean package

# Etapa de criação da imagem final
FROM openjdk:17-jdk
WORKDIR /notifications

# Copiar o arquivo JAR gerado diretamente
COPY --from=build /notifications/target/notifications-0.0.1-SNAPSHOT.jar .

# Executar o aplicativo
CMD ["java", "-jar", "notifications-0.0.1-SNAPSHOT.jar"]
