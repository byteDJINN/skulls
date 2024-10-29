load("@rules_proto_grpc_cpp//:defs.bzl", "cpp_grpc_library")

proto_library(
    name = "library",
    srcs = ["skulls.proto"],
)

cpp_grpc_library(
    name = "cpp",
    protos = [":library"],
    visibility = ["//visibility:public"],
)
