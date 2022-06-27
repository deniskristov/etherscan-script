FROM openjdk:12.0.2
COPY target/etherscan.jar /etherscan.jar
CMD ["java", "-Djdk.tls.client.protocols=TLSv1.2", "-jar", "/etherscan.jar"]