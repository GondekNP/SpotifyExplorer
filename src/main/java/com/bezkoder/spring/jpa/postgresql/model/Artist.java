package com.bezkoder.spring.jpa.postgresql.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artist")
public class Artist {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "name")
  private String name;

  @Column(name = "artistId")
  private String artistId;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "artist_track",
      joinColumns = @JoinColumn(name = "artistId"),
      inverseJoinColumns = @JoinColumn(name = "trackId")
  )
  private List<Track> tracks = new ArrayList<>();

  @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
  @CollectionTable(name = "genres", joinColumns = @JoinColumn(name = "genre_id"))
  @Column(name = "genre", nullable = false)
  private List<String> genre = new ArrayList<>();

  @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
  @CollectionTable(name = "imageUrls", joinColumns = @JoinColumn(name = "imageUrl_id"))
  @Column(name = "imageUrl", nullable = false)
  private List<String> imageUrl = new ArrayList<>();


  public Artist(String name, String artistId, List<String> genre, List<String> imageUrl) {
    this.name = name;
    this.artistId = artistId;
    this.genre = genre;
    this.imageUrl = imageUrl;
  }

  public Artist() {

  }

  public List<Track> getTracks() {
    return this.tracks;
  }

  public void setTracks(List<Track> tracks) {
      this.tracks = tracks;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getArtistId() {
    return this.artistId;
  }

  public void setArtistId(String artistId) {
    this.artistId = artistId;
  }

  public List<String> getGenre() {
    return this.genre;
  }

  public void setGenre(List<String> genre) {
    this.genre = genre;
  }

  public List<String> getImageUrl() {
    return this.imageUrl;
  }

  public void setImageUrl(List<String> imageUrl) {
    this.imageUrl = imageUrl;
  }

  @Override
  public String toString() {
    return "Artist [artistId=" + artistId + ", name=" + name + ", genres" + genre + "]";
  }

}