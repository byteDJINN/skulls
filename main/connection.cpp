
#include "connection.hpp"
#include "proto/skulls.grpc.pb.h"

#include <string>

Connection::Connection()
{
    channel = grpc::CreateChannel("127.0.0.1:50051", grpc::InsecureChannelCredentials());
    stub_ = skulls::GameService::NewStub(channel);
}

// std::string Connection::sendMessage(const std::string& message)
// {
//     chat::MessageRequest request; 
//     request.set_text(message);
//     request.set_name(name);

//     chat::Response response; 
//     grpc::ClientContext context;
//     grpc::Status status;

//     status = stub_->SendMessage(&context, request, &response);

//     if (status.ok()) {
//         if (!response.issuccess()) {
//             return response.text();
//         }
//         return "";
//     } else {
//         return "GRPC Code " + std::to_string(status.error_code()) + ": " + status.error_message();
//     }
// }