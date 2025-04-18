FROM eclipse-temurin:21
ADD target/fiverowgame-*.jar /usr/share/app.jar
ENTRYPOINT ["java", "-jar", "/usr/share/app.jar"]