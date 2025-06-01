FROM bellsoft/liberica-runtime-container:jdk-all-24.0.1-cds-slim-musl AS jre-builder
WORKDIR /application
COPY target/*.jar app.jar
RUN jar xf app.jar && \
    jdeps \
        --ignore-missing-deps \
        -q \
        --recursive \
        --multi-release 24 \
        --print-module-deps \
        --class-path 'BOOT-INF/lib/*' \
        app.jar > deps.info && \
    jlink \
        --verbose \
        --add-modules $(cat deps.info) \
        --strip-debug \
        --compress 2 \
        --no-header-files \
        --no-man-pages \
        --output /customjre

FROM bellsoft/liberica-runtime-container:jre-24.0.1-cds-slim-musl AS layers
WORKDIR /application
COPY --from=jre-builder /application/app.jar app.jar
RUN java -Djarmode=tools -jar app.jar extract --layers --destination extracted

FROM alpine:3.22.0
ENV JAVA_HOME=/opt/jre
ENV PATH=${JAVA_HOME}/bin:${PATH}

RUN addgroup -S appgroup && adduser -S appuser -G appgroup && \
    mkdir -p /application && chown -R appuser:appgroup /application
USER appuser
WORKDIR /application

COPY --from=jre-builder /customjre ${JAVA_HOME}
COPY --chown=appuser:appgroup --from=layers /application/extracted/dependencies/ ./
COPY --chown=appuser:appgroup --from=layers /application/extracted/spring-boot-loader/ ./
COPY --chown=appuser:appgroup --from=layers /application/extracted/snapshot-dependencies/ ./
COPY --chown=appuser:appgroup --from=layers /application/extracted/application/ ./

RUN java -XX:ArchiveClassesAtExit=app.jsa -Dspring.context.exit=onRefresh -jar app.jar & exit 0

ENTRYPOINT ["java","-XX:SharedArchiveFile=app.jsa","-XX:+UnlockDiagnosticVMOptions","-XX:+LogCompilation",\
            "-XX:+HeapDumpOnOutOfMemoryError","-XX:+CrashOnOutOfMemoryError","-XX:+UseStringDeduplication",\
            "-XX:+UseContainerSupport","-XX:MaxRAMPercentage=75.0","-XX:InitialRAMPercentage=75.0","-XX:MaxMetaspaceSize=128M",\
            "-XX:+UseG1GC","-XX:MaxGCPauseMillis=200","-Xss256k","-Xms345M","-Xmx512M","-jar","app.jar"]
