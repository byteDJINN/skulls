# Skulls

Skulls and Roses is a card game and this repo is for simulating it with bots. 

Game engine is made in Java, structured with microservices (Kafka). 

Bots will be made in C++ (or any language) and communicate via gRPC with game engine. 

## To make a bot

Bots will implement the abstract class in `/main/botInterface.hpp`. 

There is also a protobuf specification in `/proto/skulls.proto`. 

An example is shown in `/bots/pass`. 

## Quick Commands

```
bazelisk run //main:main --spawn_strategy=standalone -c fastbuild --incompatible_strict_action_env --copt="-O0"
```