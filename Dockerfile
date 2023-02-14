FROM ubuntu:20.04
EXPOSE 8080
COPY ./target/fiverowgame .
ENTRYPOINT ["/fiverowgame"]
