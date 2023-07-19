package catnatsuki.dicordify;

import ca.tristan.easycommands.EasyCommands;
import ca.tristan.easycommands.commands.defaults.HelpCmd;
import catnatsuki.dicordify.commands.addSong;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.IOException;


public class main {

    private static final GatewayIntent[] gatewaysIntents = {GatewayIntent.MESSAGE_CONTENT, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES,GatewayIntent.GUILD_MEMBERS};
    public static void main(String[] args) throws InterruptedException, IOException {
        EasyCommands easyCommands = new EasyCommands();
        JDA jda = easyCommands.addExecutor(new HelpCmd(easyCommands), new addSong()).buildJDA();
    }

}
