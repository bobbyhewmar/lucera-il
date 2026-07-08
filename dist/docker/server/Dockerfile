FROM eclipse-temurin:25-jdk-jammy AS temurin

FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"

RUN apt-get update \
 && apt-get install -y --no-install-recommends bash ca-certificates dos2unix netcat-openbsd procps tzdata \
 && rm -rf /var/lib/apt/lists/*

WORKDIR /opt/l2

COPY --from=temurin /opt/java/openjdk /opt/java/openjdk
COPY authserver /opt/l2/authserver
COPY gameserver /opt/l2/gameserver
COPY libs /opt/l2/libs
COPY docker/server/entrypoint.sh /usr/local/bin/l2-entrypoint

RUN dos2unix /usr/local/bin/l2-entrypoint \
        /opt/l2/authserver/*.sh \
        /opt/l2/gameserver/*.sh \
        /opt/l2/authserver/sql/*.sh \
        /opt/l2/gameserver/sql/*.sh \
 && chmod +x /usr/local/bin/l2-entrypoint \
        /opt/l2/authserver/*.sh \
        /opt/l2/gameserver/*.sh \
        /opt/l2/authserver/sql/*.sh \
        /opt/l2/gameserver/sql/*.sh \
 && mkdir -p /opt/l2/authserver/log /opt/l2/gameserver/log /opt/l2/gameserver/serverstats

EXPOSE 2106 9014 7777

ENTRYPOINT ["l2-entrypoint"]
