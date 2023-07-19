package catnatsuki.dicordify.commands;

import ca.tristan.easycommands.commands.EventData;
import ca.tristan.easycommands.commands.slash.SlashExecutor;
import ca.tristan.easycommands.database.MySQL;
import catnatsuki.dicordify.utils.db;
import catnatsuki.dicordify.utils.spotify;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


public class addSong extends SlashExecutor {
    Long expirationTime;
    boolean init = true;
    private final catnatsuki.dicordify.utils.spotify spotify = new spotify();
    @Override
    public String getName() {
        return "addsong";
    }

    @Override
    public String getDescription() {
        return "/addsong This bot will add the song to spotify playlist";
    }

    @Override
    public boolean isOwnerOnly() {
        return false;
    }

    @Override
    public List<OptionData> getOptions() {
        options.add(new OptionData(OptionType.STRING, "url", "Url for the song you want to add."));
        return super.getOptions();
    }

    @Override
    public void execute(EventData event, MySQL mySQL) {
        if (event.getCommand().getOptions().isEmpty()){
            event.getEvent().reply("Please input a spotify track URL. 😶‍🌫️").setEphemeral(true).queue();
        }else if (!spotify.isSpotifyUrl(event.getCommand().getOptions().get(0).getAsString())){
            event.getEvent().reply("Please input a valid spotify track URL.").setEphemeral(true).queue();
        }else {
            String trackUrl = event.getCommand().getOptions().get(0).getAsString();
            String trackUri = spotify.convertToUri(trackUrl);
            String[] urisf = new String[]{trackUri};
            String userId = event.getCommandSender().getUser().getId();
            db app = new db();
            String check = app.existingRecord(userId);

            LocalDate currentDate = LocalDate.now();
            int currentMonth = currentDate.getMonth().getValue();
            int currentYear = currentDate.getYear();

            if(Objects.equals(check, "exists")){
                Integer dbMonth = app.getMonth(userId);
                Integer dbYear = app.getYear(userId);
                if (currentMonth>dbMonth || currentYear>dbYear){
                    app.deleteRec(userId);
                    check = "missing";
                }
            }

            int counter = app.getMonthlyCounter(userId);

            event.deferReply();
            if (counter >= 3){
                event.getHook().sendMessage("You've already added 3 songs this month!").queue();
            }else {
                if (init){
                    spotify.authorizationCodeRefresh_Sync();
                    init = false;
                    expirationTime = (System.currentTimeMillis()/1000)+3600;
                    System.out.println("Executed init block");
                }
                if ((System.currentTimeMillis()/1000)>(expirationTime-60)){
                    spotify.authorizationCodeRefresh_Sync();
                    expirationTime = (System.currentTimeMillis()/1000)+3600;
                    System.out.println("Executed the second block.");
                }
                String addToPlaylist = spotify.addToPlaylist(urisf);
                if (Objects.equals(addToPlaylist, "success")){
                    if (Objects.equals(check,"missing")){
                        app.insert(userId,1, currentMonth, currentYear);
                    }
                    else {
                        Integer counterTwo = counter+1;
                        app.updateRecord(userId, counterTwo);
                    }
                    event.getHook().sendMessage("Song Added Successfully!").queue();
                } else if (Objects.equals(addToPlaylist, "failure")) {
                    event.getHook().sendMessage("Failed to add the track.").queue();
                }
            }
        }
    }
}
