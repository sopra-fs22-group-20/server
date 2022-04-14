package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.Current_Date;
import ch.uzh.ifi.hase.soprafs22.entity.Image;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class ImageService {

    private final Logger log = LoggerFactory.getLogger(ImageService.class);

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(@Qualifier("imageRepository") ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image createImage(Image newImage, User owner) {
        newImage.setUploadDate(Current_Date.getDate());
        newImage.setOwner(owner);

        newImage = imageRepository.save(newImage);
        imageRepository.flush();

        log.debug("Created Information for Image: {}", newImage);
        return newImage;

    }

    public Image getImageByImageId(long imageId) {
        Image tempImage = imageRepository.findImageByImageId(imageId);

        //Check if Image exists -> extract later on
        if (tempImage == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("The image with this id does not exist!"));
        }
        return tempImage;
    }
}
