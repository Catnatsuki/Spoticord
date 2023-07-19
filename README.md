# Spoticord
Discord bot to help manage shared spotify playlists. Users can add songs to a shared server playlist by using the slash commands. Currently configured to permit 3 submissions a month. 

## Deployment:
Prerequisites:
  - Open JDK version 18 or above.

- Once that is installed, clone the repository to your machine using: ``` git clone https://github.com/Catnatsuki/Spoticord ```
- Refer to the [Discord documentation](https://discord.com/developers/docs/getting-started) to set up an application and obtain your bot token.
- Replace the place holder text in 'config.properties' with your bot token. 
- Refer to the [Spotify Documentation](https://developer.spotify.com/documentation/web-api/tutorials/getting-started) to set up a spotify application and obtain your client id and client secret
- Open the project in an IDE of your choice and use getToken to obtain your access token and refresh token.
- Once you have obtained your refresh token, paste it in the spotify.java in the utils folder along with your clientID, clientSecret and playlist ID by replacing the placeholder text.
- Once that is done, run the main.java file and you should be good to go.

## License

Licensed under [MPL-2.0 License](https://www.mozilla.org/en-US/MPL/2.0/)
--- 
© 2022-2023 Catnatsuki.
