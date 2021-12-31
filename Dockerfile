FROM adoptopenjdk:11-jre-hotspot-bionic
ADD target/order-0.0.1-SNAPSHOT.jar order.jar
EXPOSE 8090
ENTRYPOINT [ "java", "-jar" , "order.jar"]
