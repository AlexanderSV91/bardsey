#FROM bellsoft/liberica-runtime-container:jdk-24.0.1-cds-slim-musl AS builder
#WORKDIR /application
#COPY . .
#RUN --mount=type=cache,target=/root/.m2 chmod +x mvnw && ./mvnw clean install -Purl-shortener-service -Dmaven.test.skip

FROM bellsoft/liberica-runtime-container:jdk-24.0.1-cds-slim-musl AS layers
WORKDIR /application
#COPY --from=builder /application/url-shortener-service/target/*.jar app.jar
COPY target/*.jar app.jar
RUN java -Djarmode=tools -jar app.jar extract --layers --destination extracted

FROM bellsoft/liberica-runtime-container:jre-24.0.1-cds-slim-musl

RUN addgroup -S appgroup && adduser -S appuser -G appgroup && \
    mkdir -p /application && chown -R appuser:appgroup /application
USER appuser
WORKDIR /application

COPY --chown=appuser:appgroup --from=layers /application/extracted/dependencies/ ./
COPY --chown=appuser:appgroup --from=layers /application/extracted/spring-boot-loader/ ./
COPY --chown=appuser:appgroup --from=layers /application/extracted/snapshot-dependencies/ ./
COPY --chown=appuser:appgroup --from=layers /application/extracted/application/ ./

RUN java -XX:ArchiveClassesAtExit=app.jsa -Dspring.context.exit=onRefresh -jar app.jar & exit 0

ENTRYPOINT ["java","-XX:SharedArchiveFile=app.jsa","-jar","app.jar"]
