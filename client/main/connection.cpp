
#include "connection.hpp"
#include "skulls.grpc.pb.h"
#include "botInterface.hpp"

#include <string>

Connection::Connection(Bot& bot): bot(bot)
{
    channel = grpc::CreateChannel("127.0.0.1:50051", grpc::InsecureChannelCredentials());
    stub_ = skulls::GameService::NewStub(channel);
}

void Connection::start() {
    grpc::ClientContext context;
    stream_ = stub_->GetAction(&context);

    skulls::GameState gameState;
    while (stream_->Read(&gameState)) // Read is a blocking call
    { 
        skulls::ActionRequest request = bot.getAction(gameState);
        sendActionRequest(request);
    }
    grpc::Status status = stream_->Finish();
    if (!status.ok())
    {
        std::cerr << "Stream finished with error: " << status.error_message() << std::endl;
    }
}

void Connection::sendActionRequest(const skulls::ActionRequest &request) {
    if (!stream_->Write(request)) {
        std::cerr << "Failed to write to stream!" << std::endl;
    }
}
