#ifndef MAIN_NETWORK_H_
#define MAIN_NETWORK_H_

#include "proto/skulls.grpc.pb.h"

#include <grpcpp/grpcpp.h>
#include <string>
#include <memory>

class Connection {
public:
    Connection();
private:
    std::shared_ptr<grpc::Channel> channel;
    std::unique_ptr<skulls::GameService::Stub> stub_;
};

#endif