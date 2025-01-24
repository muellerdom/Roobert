ARG BUILD_VERSION="v0.1"

FROM sbtscala/scala-sbt:eclipse-temurin-17.0.13_11_1.10.7_3.6.2 AS build

WORKDIR /Roobert

RUN apt-get update
RUN apt-get install -y libgtk-3-0 libglib2.0-0 libxext6 libxrender1 libxtst6 libx11-6 xvfb

COPY build.sbt ./
COPY project ./project

RUN sbt update

COPY src ./src

ENV CI_RELEASE=true
RUN sbt clean assembly

FROM eclipse-temurin:17-jre AS run

RUN apt-get update
RUN apt-get install -y libgtk-3-0 libglib2.0-0 libxext6 libxrender1 libxtst6 libx11-6 xvfb

WORKDIR /Roobert

COPY --from=build /Roobert/target/scala-2.13/Roobert-.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]