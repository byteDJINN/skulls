
load("@rules_proto_grpc_cpp//:defs.bzl", "cpp_grpc_library")

cpp_grpc_library(
    name = "skullsProto",
    protos = ["@proto//:library"],
    visibility = ["//visibility:public"],
)

cc_library(
    name = "botInterface",
    srcs = ["botInterface.hpp"],
    deps = [
        ":skullsProto"
    ],
    visibility = ["//visibility:public"],
)

cc_library(
    name = "connection",
    srcs = ["connection.cpp"],
    hdrs = ["connection.hpp"],
    deps = [
        ":skullsProto",
        ":botInterface",
    ],
)

cc_binary(
    name = "main",
    srcs = ["main.cpp"],
    deps = [
        ":connection",
        "//bots:allBots",
    ],
)