FROM quindy:latest
RUN mvn install -DskipTests

FROM ubuntu:16.04
# First install software-properties-common for add-apt-repository, and apt-transport-https to communicate.
RUN apt-get update \
    && apt-get install -y software-properties-common \
                openjdk-8-jdk \
                apt-transport-https \
                maven \
                git \
                curl \
    && apt-key adv --keyserver keyserver.ubuntu.com --recv-keys 68DB5E88 \
    && add-apt-repository "deb https://repo.sovrin.org/sdk/deb xenial stable"

# Set the default Libindy version. Will be overwritten when argument is supplied from console
ARG LIBINDY_VERSION=1.5.0

# Split off libindy command for fast builds on version bump
RUN apt-get update && apt-get install -y libindy=$LIBINDY_VERSION

RUN mkdir /quindy
COPY --from=0 /pom.xml /quindy/pom.xml
COPY --from=0 /target/quindy-*-jar-with-dependencies.jar /quindy/quindy.jar

RUN mvn install:install-file -Dfile=/quindy/quindy.jar  -DpomFile=/quindy/pom.xml

ADD pom.xml /
RUN mvn package
ADD . /
CMD mvn verify
