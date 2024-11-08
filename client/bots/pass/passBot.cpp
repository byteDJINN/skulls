#include "passBot.hpp"

skulls::ActionRequest PassBot::getAction(const skulls::GameState &gameState)
{
    skulls::ActionRequest actionRequest;
    skulls::PassAction* passAction = actionRequest.mutable_pass_action();
    return actionRequest;
}