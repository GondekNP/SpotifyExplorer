package com.bezkoder.spring.jpa.postgresql.repository;

import java.util.List;

import com.bezkoder.spring.jpa.postgresql.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bezkoder.spring.jpa.postgresql.model.Track;


public interface TrackRepository extends JpaRepository<Track, Long> {
    List<Track> findTrackByTrackId(String trackId);
    @Query("SELECT track_id FROM artist_track WHERE artist_id = :artist_id")
    List<Track> findTracksByArtistId(@Param("artist_id") String artist_id);
}
