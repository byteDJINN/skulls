
load("@rules_proto_grpc_cpp//:defs.bzl", "cpp_grpc_library")

cpp_grpc_library(
    name = "proto_cpp",
    protos = ["@proto//:library"],
    visibility = ["//visibility:public"],
)

cc_library(
    name = "botInterface",
    srcs = ["botInterface.hpp"],
    deps = [
        ":proto_cpp"
    ],
    visibility = ["//visibility:public"],
)

cc_library(
    name = "connection",
    srcs = ["connection.cpp"],
    hdrs = ["connection.hpp"],
    deps = [
        ":proto_cpp",
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