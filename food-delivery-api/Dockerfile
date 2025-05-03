# 1. 기본 Java 이미지 사용
FROM openjdk:17-jdk-alpine

# 2. 빌드된 JAR 파일 경로 (Gradle 기본 경로 기준)
ARG JAR_FILE=build/libs/food-delivery-api-0.0.1-SNAPSHOT.jar

# 3. JAR 복사
COPY ${JAR_FILE} food-delivery-api.jar

# 4. 실행 명령
ENTRYPOINT ["java", "-jar", "/food-delivery-api.jar"]