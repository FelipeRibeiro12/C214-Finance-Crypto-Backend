# ── Stage 1: Build ──────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Copia só o pom.xml primeiro para aproveitar cache de dependências
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Baixa dependências (camada separada — só rebuilda se o pom.xml mudar)
RUN ./mvnw dependency:go-offline -q

# Copia o código e compila
COPY src ./src
RUN ./mvnw package -DskipTests -q

# ── Stage 2: Runtime ─────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Usuário não-root por segurança
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
