package com.bezkoder.spring.jpa.postgresql.services;

import com.neovisionaries.i18n.CountryCode;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;

import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.requests.data.artists.GetArtistRequest;
import se.michaelthelin.spotify.requests.data.artists.GetArtistsTopTracksRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.Arrays;

public class SpotifyClient {

    private SpotifyApi spotifyApi;

    public SpotifyClient(String clientId, String clientSecret) {
        this.spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();
        final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
                .build();
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            this.spotifyApi.setAccessToken(clientCredentials.getAccessToken());
        } catch (IOException | SpotifyWebApiException | ParseException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Track[] getTopTracksByArtist_Sync(String artistId, CountryCode countryCode) {
        final GetArtistsTopTracksRequest getArtistsTopTracksRequest = spotifyApi
                .getArtistsTopTracks(artistId, countryCode)
                .build();
        try {
            final Track[] tracks = getArtistsTopTracksRequest.execute();
            System.out.println("Length: " + tracks.length);
            return tracks;
        }
        catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public Artist getArtistByArtistId_Sync(String artistId) {
        final GetArtistRequest getArtistRequest = spotifyApi
                .getArtist(artistId)
                .build();
        try {
            final Artist artist = getArtistRequest.execute();
            System.out.println("Found artist: " + artist.getName());
            return artist;
        }
        catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args){
        String clientId = "2fa46b7b82c743b6ade535691e037065";
        String clientSecret = args[0];

        SpotifyClient client = new SpotifyClient(clientId, clientSecret);

        // test with "Ratatat"
        final Track[] tracks = client.getTopTracksByArtist_Sync("57dN52uHvrHOxijzpIgu3E", CountryCode.US);
        System.out.println(Arrays.toString(tracks));
    }
}
