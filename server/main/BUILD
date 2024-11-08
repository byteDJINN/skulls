load("@rules_proto_grpc_java//:defs.bzl", "java_grpc_library")


java_grpc_library(
    name = "proto_java",
    protos = ["@proto//:library"],
)

java_library(
    name = "connection",
    srcs = [
        "Connection.java"
    ],
    deps = [":proto_java"],
)

java_binary(
    name = "main",
    srcs = [
        "Engine.java"
    ],
    deps = [":connection"],
    main_class = "Engine",
)