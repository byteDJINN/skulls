#ifndef MAIN_BOT_H_
#define MAIN_BOT_H_

#include "proto/skulls.grpc.pb.h"

class Bot {
public:
    // each bot is passed a gameState on their turn and must respond with their action 
    virtual skulls::ActionRequest getAction(const skulls::GameState& gameState) = 0;
    virtual ~Bot() = default;
};

#endif