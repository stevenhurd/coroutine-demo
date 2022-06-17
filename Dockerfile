FROM turo/java-app:jre17-1.0.0
VOLUME /tmp
ARG DEPENDENCY=../../libs
# https://github.com/newrelic/newrelic-java-agent/issues/526
ENV NEW_RELIC_CLASS_TRANSFORMER_COM_NEWRELIC_INSTRUMENTATION_JAVA_COMPLETABLE_FUTURE_JDK8U40_ENABLED=false
COPY ${DEPENDENCY}/application .
COPY ${DEPENDENCY}/dependencies .
COPY ${DEPENDENCY}/snapshot-dependencies .
COPY ${DEPENDENCY}/spring-boot-loader .
ENTRYPOINT exec java $JAVA_DOCKER_MEMORY_OPTS $JAVA_OPTS $NEW_RELIC_OPTS -Djava.security.egd=file:/dev/./urandom  org.springframework.boot.loader.JarLauncher
