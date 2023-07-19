package catnatsuki.dicordify.utils;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.special.SnapshotResult;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.requests.data.playlists.AddItemsToPlaylistRequest;

import java.io.IOException;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class spotify {
    private static final String clientId = "your-client-secret-goes-here";
    private static final String clientSecret = "your-client-secret-goes-here";
    private static final String refreshToken = "your-refresh-token-goes-here";
    private static final String playlistId = "your-playlist-id-goes-here";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(URI.create("http://localhost:7777/callback"))
            .setRefreshToken(refreshToken)
            .build();

    private static final AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh()
            .build();

    public String convertToUri(String trackUrl){
        String uriregex = "https:\\/\\/open.spotify.com\\/track\\/(\\w+)";
        Pattern pattern = Pattern.compile(uriregex);
        Matcher matcher = pattern.matcher(trackUrl);

        if(matcher.find()){
            String trackID = matcher.group(1);
            return "spotify:track:"+trackID;
        }
        else {
            return "false";
        }
    }
    public String addToPlaylist(String[] uris){
        final AddItemsToPlaylistRequest addItemsToPlaylistRequest = spotifyApi
                .addItemsToPlaylist(playlistId, uris)
                .build();
        try {
            final SnapshotResult snapshotResult = addItemsToPlaylistRequest.execute();
            System.out.println("Snapshot ID: " + snapshotResult.getSnapshotId());
            return "success";
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return "failure";
        }
    }

    public String authorizationCodeRefresh_Sync(){
        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();
            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
            return authorizationCodeCredentials.getAccessToken();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return "error";
        }
    }

    public boolean isSpotifyUrl(String userInput){
        String spotifyTrackPattern = "https://open.spotify.com/track/([a-zA-Z0-9]+)(\\?si=[a-zA-Z0-9]+)?";

        Pattern pattern = Pattern.compile(spotifyTrackPattern);
        Matcher matcher = pattern.matcher(userInput);

        return matcher.matches();
    }

    public static void main(String[] args) {
    }
}
