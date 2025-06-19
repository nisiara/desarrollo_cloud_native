FROM eclipse-temurin:21-jdk AS buildstage

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml .
COPY src /app/src
COPY Wallet_J4MXCG39LLG5V7MK /app/wallet

ENV TNS_ADMIN=/app/wallet

RUN mvn clean package -DskipTests -B

FROM eclipse-temurin:21-jdk

COPY --from=buildstage /app/target/tienda-0.0.1-SNAPSHOT.jar /app/tienda.jar

COPY Wallet_J4MXCG39LLG5V7MK /app/wallet

ENV TNS_ADMIN=/app/wallet
EXPOSE 8080

ENTRYPOINT [ "java", "-jar","/app/tienda.jar" ]


