package com.bezkoder.spring.jpa.postgresql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.jpa.postgresql.model.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
  List<Artist> findArtistByArtistId(String artistId);

}
