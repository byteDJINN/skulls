#ifndef MAIN_BOT_H_
#define MAIN_BOT_H_

#include "proto/skulls.grpc.pb.h"

class Bot {
public:
    virtual skulls::ActionRequest getAction(const skulls::GameState& gameState) = 0;
    virtual ~Bot() = default;
};

#endif