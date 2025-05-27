FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# install just what you need for Maven-based dev
RUN apk add --no-cache bash maven

# copy only your pom so we can go-offline up front
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# no src copy here — we'll mount it
CMD ["mvn", \
     "spring-boot:run", \
     "-Dspring-boot.run.fork=false", \
     "-Dspring-boot.run.jvmArguments=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"]
