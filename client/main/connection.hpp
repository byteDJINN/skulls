#ifndef MAIN_CONNECTION_H_
#define MAIN_CONNECTION_H_

#include "skulls.grpc.pb.h"
#include "botInterface.hpp"

#include <grpcpp/grpcpp.h>
#include <string>
#include <memory>

class Connection {
public:
    Connection(Bot& bot);
    void start();
private:
    void sendActionRequest(const skulls::ActionRequest& request);

    Bot& bot;
    std::shared_ptr<grpc::Channel> channel;
    std::unique_ptr<skulls::GameService::Stub> stub_;
    std::shared_ptr<grpc::ClientReaderWriter<skulls::ActionRequest, skulls::GameState>> stream_;
};

#endif