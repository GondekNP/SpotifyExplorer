package com.bezkoder.spring.jpa.postgresql.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.postgresql.model.Artist;
import com.bezkoder.spring.jpa.postgresql.model.Track;
import com.bezkoder.spring.jpa.postgresql.repository.TrackRepository;
import com.bezkoder.spring.jpa.postgresql.services.SpotifyClient;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TrackController {

    @Autowired
    TrackRepository trackRepository;

    @Autowired
    private SpotifyClient spotifyClient;

    @GetMapping("/tracks/byArtist/{artistId}/{countryCode}")
    public ResponseEntity<Track[]> getTopTracksByArtist(@PathVariable("artistId") String artistId, @PathVariable CountryCode countryCode){
        try {
            se.michaelthelin.spotify.model_objects.specification.Track[] tracks_response =
                    this.spotifyClient.getTopTracksByArtist_Sync(artistId, countryCode);
            for (se.michaelthelin.spotify.model_objects.specification.Track track : tracks_response) {
                String trackId = track.getId();
                se.michaelthelin.spotify.model_objects.specification.AudioFeatures audioFeatures =
                        this.spotifyClient.getAudioFeaturesByTrackId_Sync(trackId);
                trackRepository.save(new Track(
                        trackId,
                        track.getName(),
                        track.getDurationMs(),
                        track.getIsExplicit(),
                        track.getExternalUrls().get("spotify"),
                        Arrays.asList(Arrays.toString(track.getAvailableMarkets())),
                        track.getPopularity(),
                        track.getPreviewUrl(),
                        track.getTrackNumber(),
                        audioFeatures.getAcousticness(),
                        audioFeatures.getAnalysisUrl(),
                        audioFeatures.getDanceability(),
                        audioFeatures.getEnergy(),
                        audioFeatures.getInstrumentalness(),
                        audioFeatures.getKey(),
                        audioFeatures.getLiveness(),
                        audioFeatures.getLoudness(),
                        audioFeatures.getMode().getType(),
                        audioFeatures.getSpeechiness(),
                        audioFeatures.getTempo(),
                        audioFeatures.getTimeSignature(),
                        audioFeatures.getType().getType(), //for debugging
                        audioFeatures.getUri(),
                        audioFeatures.getValence()
                ));
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
