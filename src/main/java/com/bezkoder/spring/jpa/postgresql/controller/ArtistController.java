package com.bezkoder.spring.jpa.postgresql.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import se.michaelthelin.spotify.model_objects.specification.Image;
import com.bezkoder.spring.jpa.postgresql.repository.ArtistRepository;
import com.bezkoder.spring.jpa.postgresql.services.SpotifyClient;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ArtistController {

	@Autowired
	ArtistRepository artistRepository;

	@Autowired
	private SpotifyClient spotifyClient;

	@GetMapping("/artists/{artistId}")
	public ResponseEntity<Artist> getArtistByArtistId(@PathVariable("artistId") String artistId){
		try {

			List<Artist> artists = artistRepository.findArtistByArtistId(artistId);

			// If we don't already have this artist
			if (artists.isEmpty()) {

				// Instantiate the client and create the get artist request
				se.michaelthelin.spotify.model_objects.specification.Artist artist_response = this.spotifyClient.getArtistByArtistId_Sync(artistId);

				// Convert the Image model from java spotify api to simpler list of string urls
				List<String> artistImageUrls = new ArrayList<String>();
				for (Image image : artist_response.getImages()){
					artistImageUrls.add(image.getUrl());
				}

				// Finally, save the artist into our postgres db using JPA
				Artist _artist = artistRepository
					.save(new Artist(
							artist_response.getName(),
							artistId,
							Arrays.stream(artist_response.getGenres()).toList(),
							artistImageUrls
					));

				return new ResponseEntity<>(_artist, HttpStatus.CREATED);

			}

			return new ResponseEntity<>(artists.get(0), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}


//	@GetMapping("/tutorials")
//	public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
//		try {
//			List<Tutorial> tutorials = new ArrayList<Tutorial>();
//
//			if (title == null)
//				tutorialRepository.findAll().forEach(tutorials::add);
//			else
//				tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
//
//			if (tutorials.isEmpty()) {
//				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//			}
//
//			return new ResponseEntity<>(tutorials, HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//
//	@GetMapping("/tutorials/{id}")
//	public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
//		Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
//
//		if (tutorialData.isPresent()) {
//			return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}
//
//	@PostMapping("/tutorials")
//	public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
//		try {
//			Tutorial _tutorial = tutorialRepository
//					.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
//			return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
//		} catch (Exception e) {
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//
//	@PutMapping("/tutorials/{id}")
//	public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
//		Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
//
//		if (tutorialData.isPresent()) {
//			Tutorial _tutorial = tutorialData.get();
//			_tutorial.setTitle(tutorial.getTitle());
//			_tutorial.setDescription(tutorial.getDescription());
//			_tutorial.setPublished(tutorial.isPublished());
//			return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}
//
//	@DeleteMapping("/tutorials/{id}")
//	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
//		try {
//			tutorialRepository.deleteById(id);
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		} catch (Exception e) {
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//
//	@DeleteMapping("/tutorials")
//	public ResponseEntity<HttpStatus> deleteAllTutorials() {
//		try {
//			tutorialRepository.deleteAll();
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		} catch (Exception e) {
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//	}
//
//	@GetMapping("/tutorials/published")
//	public ResponseEntity<List<Tutorial>> findByPublished() {
//		try {
//			List<Tutorial> tutorials = tutorialRepository.findByPublished(true);
//
//			if (tutorials.isEmpty()) {
//				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//			}
//			return new ResponseEntity<>(tutorials, HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//
//}
