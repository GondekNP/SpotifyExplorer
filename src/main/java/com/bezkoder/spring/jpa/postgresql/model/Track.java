package com.bezkoder.spring.jpa.postgresql.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "track")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToMany(mappedBy = "tracks", fetch = FetchType.EAGER)
    private List<Artist> artists;

    public Track(
            String trackId,
            String artistId,
            String name,
            int durationMs,
            boolean explicit,
            String externalUrl,
            List<String> availableMarkets,
            int popularity,
            String previewUrl,
            int trackNumber,
            float acousticness,
            String analysisUrl,
            float danceability,
            float energy,
            float instrumentalness,
            int key,
            float liveness,
            float loudness,
            int mode,
            float speechiness,
            float tempo,
            int timeSignature,
            String type,
            String uri,
            float valence
    ) {
        this.trackId = trackId;
        this.artistId = artistId;
        this.name = name;
        this.durationMs = durationMs;
        this.explicit = explicit;
        this.externalUrl = externalUrl;
        this.availableMarkets = availableMarkets;
        this.popularity = popularity;
        this.previewUrl = previewUrl;
        this.trackNumber = trackNumber;
        this.acousticness = acousticness;
        this.analysisUrl = analysisUrl;
        this.danceability = danceability;
        this.energy = energy;
        this.instrumentalness = instrumentalness;
        this.key = key;
        this.liveness = liveness;
        this.loudness = loudness;
        this.mode = mode;
        this.speechiness = speechiness;
        this.tempo = tempo;
        this.timeSignature = timeSignature;
        this.type = type;
        this.uri = uri;
        this.valence = valence;
    }

    @Column(name = "trackId")
    private String trackId;

    @Column(name = "artistId")
    private String artistId;

    @Column(name = "name")
    private String name;

    @Column(name = "duration_ms")
    private int durationMs;

    @Column(name = "explicit")
    private boolean explicit;

    @Column(name = "external_url")
    private String externalUrl;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "available_markets", joinColumns = @JoinColumn(name = "market_id"))
    @Column(name = "available_market", nullable = false)
    private List<String> availableMarkets = new ArrayList<>();

    @Column(name = "popularity")
    private int popularity;

    @Column(name = "preview_url")
    private String previewUrl;

    @Column(name = "track_number")
    private int trackNumber;

    @Column(name = "is_local")
    private boolean isLocal;

    @Column(name = "acousticness")
    private float acousticness;

    @Column(name = "analysis_url")
    private String analysisUrl;

    @Column(name = "danceability")
    private float danceability;

    @Column(name = "energy")
    private float energy;

    @Column(name = "instrumentalness")
    private float instrumentalness;

    @Column(name = "key")
    private int key;

    @Column(name = "liveness")
    private float liveness;

    @Column(name = "loudness")
    private float loudness;

    @Column(name = "mode")
    private int mode;

    @Column(name = "speechiness")
    private float speechiness;

    @Column(name = "tempo")
    private float tempo;

    @Column(name = "time_signature")
    private int timeSignature;

    @Column(name = "type")
    private String type;

    @Column(name = "uri")
    private String uri;

    @Column(name = "valence")
    private float valence;

    // Now, all getters and setters
    public long getId() {
        return id;
    }

    public String getTrackId() {
        return this.trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDurationMs() {
        return this.durationMs;
    }

    public void setDurationMs(int durationMs) {
        this.durationMs = durationMs;
    }

    public boolean isExplicit() {
        return this.explicit;
    }

    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }

    public String getExternalUrl() {
        return this.externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public List<String> getAvailableMarkets() {
        return this.availableMarkets;
    }

    public void setAvailableMarkets(List<String> availableMarkets) {
        this.availableMarkets = availableMarkets;
    }

    public int getPopularity() {
        return this.popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getPreviewUrl() {
        return this.previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public int getTrackNumber() {
        return this.trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public float getAcousticness() {
        return this.acousticness;
    }

    public void setAcousticness(float acousticness) {
        this.acousticness = acousticness;
    }

    public String getAnalysisUrl() {
        return this.analysisUrl;
    }

    public void setAnalysisUrl(String analysisUrl) {
        this.analysisUrl = analysisUrl;
    }

    public float getDanceability() {
        return this.danceability;
    }

    public void setDanceability(float danceability) {
        this.danceability = danceability;
    }

    public float getEnergy() {
        return this.energy;
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public float getInstrumentalness() {
        return this.instrumentalness;
    }

    public void setInstrumentalness(float instrumentalness) {
        this.instrumentalness = instrumentalness;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public float getLiveness() {
        return this.liveness;
    }

    public void setLiveness(float liveness) {
        this.liveness = liveness;
    }

    public float getLoudness() {
        return this.loudness;
    }

    public void setLoudness(float loudness) {
        this.loudness = loudness;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public float getSpeechiness() {
        return this.speechiness;
    }

    public void setSpeechiness(float speechiness) {
        this.speechiness = speechiness;
    }

    public float getTempo() {
        return this.tempo;
    }

    public void setTempo(float tempo) {
        this.tempo = tempo;
    }

    public int getTimeSignature() {
        return this.timeSignature;
    }

    public void setTimeSignature(int timeSignature) {
        this.timeSignature = timeSignature;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public float getValence() {
        return this.valence;
    }

    public void setValence(float valence) {
        this.valence = valence;
    }

    @Override
    public String toString() {
        return "Track [trackId=" + trackId + ", name=" + name + ", popularity=" + popularity + "]";
    }
}