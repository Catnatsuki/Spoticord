package catnatsuki.dicordify.utils;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.io.IOException;
import java.net.URI;

public class getToken {
    private static final String clientId = "your-client-secret-goes-here";
    private static final String clientSecret = "your-client-secret-goes-here";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:7777/callback");

    private static final String code ="your-code-from-uri-here";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();
    private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
            .scope("playlist-modify-private")
            .build();

    private static final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();

    public static void authorizationCode_Sync(){
        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            System.out.println(authorizationCodeCredentials.getAccessToken());
            System.out.println("\n");
            System.out.println("\n");
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
            System.out.println(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static void authorizationCodeUri_Sync() {
        final URI uri = authorizationCodeUriRequest.execute();
        System.out.println("URI: " + uri.toString());
    }

    public static void main(String[] args) {
//        authorizationCodeUri_Sync();
//        authorizationCode_Sync();
    }
}
