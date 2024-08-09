

# Start with the Maven OpenJDK image
FROM maven:3.8.6-openjdk-11 AS build

# Set the working directory in the image
WORKDIR /usr/app

COPY . .

FROM selenium/standalone-firefox:128.0

# Copy the JDK and Maven from the build image
COPY --from=build --chown=seluser:seluser /usr/share/maven /usr/share/maven
COPY --from=build --chown=seluser:seluser /usr/local/openjdk-11 /usr/local/openjdk-11
COPY --from=build --chown=seluser:seluser /usr/app /usr/app

# Set the environment variables
ENV JAVA_HOME=/usr/local/openjdk-11
ENV MAVEN_HOME=/usr/share/maven
ENV PATH="${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${PATH}"

# Switch to non-root user
USER seluser


CMD [ "/bin/sh", "/usr/app/Task.sh",  "Headless" ]
