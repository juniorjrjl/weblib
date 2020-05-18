FROM  gradle:6.4-jdk14

RUN apt-get update && apt-get install -qq -y --no-install-recommends

ENV INSTALL_PATH /weblib

RUN mkdir -p $INSTALL_PATH

WORKDIR $INSTALL_PATH

COPY . .