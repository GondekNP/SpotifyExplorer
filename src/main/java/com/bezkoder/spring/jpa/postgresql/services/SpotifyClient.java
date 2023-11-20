package com.bezkoder.spring.jpa.postgresql.services;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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

@Service
public class SpotifyClient {

    // Unclear to me why this isn't working but not going to get too hung up on it for now
//    @Value("${SPOTIFY_ID}")
//    private String clientId;
//
//    @Value("${SPOTIFY_SECRET}")
//    private String clientSecret;

    private SpotifyApi spotifyApi;
    private long tokenExpiration = 0;


    public SpotifyClient() {
        this.spotifyApi = new SpotifyApi.Builder()
                .setClientId(System.getenv("SPOTIFY_ID"))
                .setClientSecret(System.getenv("SPOTIFY_SECRET"))
                .build();
        refreshAccessToken();
    }

    private void refreshAccessToken() {
        try {
            final ClientCredentialsRequest clientCredentialsRequest = this.spotifyApi.clientCredentials().build();
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            this.spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            tokenExpiration = System.currentTimeMillis() + (clientCredentials.getExpiresIn() * 1000);
        } catch (IOException | SpotifyWebApiException | ParseException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void ensureAccessToken() {
        if (System.currentTimeMillis() > tokenExpiration) {
            refreshAccessToken();
        }
    }

    public Track[] getTopTracksByArtist_Sync(String artistId, CountryCode countryCode) {
        ensureAccessToken();
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
        ensureAccessToken();
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
        SpotifyClient client = new SpotifyClient();

        // test with "Ratatat"
        final Track[] tracks = client.getTopTracksByArtist_Sync("57dN52uHvrHOxijzpIgu3E", CountryCode.US);
        System.out.println(Arrays.toString(tracks));
    }
}
