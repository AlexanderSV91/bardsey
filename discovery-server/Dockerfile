FROM bellsoft/liberica-runtime-container:jre-24.0.1-cds-slim-musl AS layers
WORKDIR /application
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

ENTRYPOINT ["java","-XX:SharedArchiveFile=app.jsa","-XX:+UnlockDiagnosticVMOptions","-XX:+LogCompilation",\
            "-XX:+HeapDumpOnOutOfMemoryError","-XX:+CrashOnOutOfMemoryError","-XX:+UseStringDeduplication",\
            "-XX:+UseContainerSupport","-XX:MaxRAMPercentage=75.0","-XX:InitialRAMPercentage=75.0","-XX:MaxMetaspaceSize=128M",\
            "-XX:+UseG1GC","-XX:MaxGCPauseMillis=200","-Xss256k","-Xms345M","-Xmx512M","-jar","app.jar"]
