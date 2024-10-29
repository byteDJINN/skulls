#ifndef MAIN_PASS_BOT_H_
#define MAIN_PASS_BOT_H_

#include "main/botInterface.hpp"

class PassBot : public Bot
{
public:
    skulls::ActionRequest getAction(const skulls::GameState &gameState) override;
};

#endif