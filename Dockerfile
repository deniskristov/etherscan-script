FROM openjdk:12.0.2
COPY etherscan.jar /etherscan.jar
CMD ["java", "-Dspring.profiles.active=production", "-Djdk.tls.client.protocols=TLSv1.2", "-jar","/etherscan.jar"]