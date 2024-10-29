#include "connection.hpp"

#include <thread>

// Include all the bots
#include "bots/pass/passBot.hpp"

void startBot(Bot& bot) {
    Connection connection(bot);
    connection.start();
}

int main(int argc, char **argv)
{

    // Create an instance of each bot and start it
    PassBot passBot; 
    std::thread t(startBot, std::ref(passBot)); 
    t.join();

    return 0;
}