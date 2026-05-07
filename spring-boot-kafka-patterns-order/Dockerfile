FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY build/libs/cloud-native-msa-order-1.jar cloud-native-msa-order.jar

VOLUME /tmp

ENTRYPOINT ["java","-jar","cloud-native-msa-order.jar"]