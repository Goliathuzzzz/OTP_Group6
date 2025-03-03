FROM 0x6a4b/vnc-gui

ARG JAR_FILE=target/otp-group6.jar

COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]
COPY docker_xinitrc /root/.xinitrc
ENTRYPOINT ["x11vnc", "-create"]