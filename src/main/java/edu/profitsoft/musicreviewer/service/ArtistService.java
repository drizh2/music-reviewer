package edu.profitsoft.musicreviewer.service;

import edu.profitsoft.musicreviewer.dao.ArtistDAO;
import edu.profitsoft.musicreviewer.exception.ArtistExistsException;
import edu.profitsoft.musicreviewer.exception.ArtistNotFoundException;
import edu.profitsoft.musicreviewer.model.Artist;
import edu.profitsoft.musicreviewer.payload.request.CreateArtistRequest;
import edu.profitsoft.musicreviewer.payload.request.UpdateArtistRequest;
import edu.profitsoft.musicreviewer.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;
    private final ArtistDAO artistDAO;

    public Artist createArtist(CreateArtistRequest request) {
        try {
            Artist artist = Artist.builder()
                    .fullName(request.getFullName())
                    .bio(request.getBio())
                    .build();

            return artistRepository.save(artist);
        } catch (Exception e) {
            throw new ArtistExistsException("Artist is already exists");
        }
    }

    public Artist updateArtist(Long artistId, UpdateArtistRequest request) {
        Artist artist = artistDAO.getArtistById(artistId);
        if (artist == null) {
            throw new ArtistNotFoundException("Artist not found");
        } else {
            try {
                artist.setFullName(request.getFullName());
                artist.setBio(request.getBio());
                return artistRepository.save(artist);
            } catch (Exception e) {
                throw new ArtistExistsException("Artist " + request.getFullName() + " is already exists");
            }
        }
    }
}
