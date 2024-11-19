load("@rules_proto_grpc_java//:defs.bzl", "java_grpc_library")


java_grpc_library(
    name = "protoJava",
    protos = ["@proto//:library"],
)

java_library(
    name = "clientManager",
    srcs = [
        "ClientManager.java"
    ],
    deps = [
        ":protoJava",
    ]
)

java_library(
    name = "gameServer",
    srcs = [
        "GameServer.java"
    ],
    deps = [
        ":protoJava",
        ":logic",
        ":clientManager",
    ],
)

java_library(
    name = "logic",
    srcs = [
        "Logic.java"
    ],
    deps = [":protoJava"],
)

java_binary(
    name = "main",
    srcs = [
        "Engine.java"
    ],
    deps = [":gameServer"],
    main_class = "Engine",
)