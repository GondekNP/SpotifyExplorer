package com.bezkoder.spring.jpa.postgresql.repository;

import java.util.List;

import com.bezkoder.spring.jpa.postgresql.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.jpa.postgresql.model.Track;


public interface TrackRepository extends JpaRepository<Track, Long> {
    List<Track> findTrackByTrackId(String trackId);
    void deleteTrackByTrackId(String trackId);
}
