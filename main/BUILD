
cc_library(
    name = "botInterface",
    srcs = ["botInterface.hpp"],
    deps = [
        "//proto:cpp"
    ],
    visibility = ["//visibility:public"],
)

cc_library(
    name = "connection",
    srcs = ["connection.cpp"],
    hdrs = ["connection.hpp"],
    deps = [
        "//proto:cpp",
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