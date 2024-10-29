

cc_library(
    name = "connection",
    srcs = ["connection.cpp"],
    hdrs = ["connection.hpp", "bot.hpp"],
    deps = [
        "//proto:cpp",
    ],
)

cc_binary(
    name = "main",
    srcs = ["main.cpp"],
    deps = [
        ":connection",
    ],
)