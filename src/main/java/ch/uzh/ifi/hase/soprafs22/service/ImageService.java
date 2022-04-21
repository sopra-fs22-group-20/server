package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.Current_Date;
import ch.uzh.ifi.hase.soprafs22.entity.Image;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.ImageRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Autowired
    public ImageService(@Qualifier("imageRepository") ImageRepository imageRepository, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }

    public Image createImage(Image newImage, User owner) {
        newImage.setUploadDate(Current_Date.getDate());
        newImage.setOwner(owner);

        //Checks if the storageLink exists
        Image tempImage = imageRepository.findImageByStorageLink(newImage.getStorageLink());
        if (tempImage != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("The image with this firebase-storageLink already exist!"));
        }

        newImage = imageRepository.save(newImage);
        imageRepository.flush();

        log.debug("Created Information for Image: {}", newImage);
        return newImage;
    }

    public Image getImageByImageId(long imageId) {
        Image tempImage = imageRepository.findImageByImageId(imageId);

        //Check if Image exists -> extract later on
        if (tempImage == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("The image with this id does not exist!"));
        }
        return tempImage;
    }

    public void checkAccess(Long userId, Image image) {
        if (image.getOwner().getUserId() != userId) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    String.format("You dont have access"));
        }
    }

    public Image updateImage(Image imageToBeChanged, Image imageChanges) {
        //Checks if this image exists
        if (imageToBeChanged == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("The image with this id does not exist!"));
        }
        //Parameter to update
        imageToBeChanged.setName(imageChanges.getName());

        imageRepository.save(imageToBeChanged);
        imageRepository.flush();

        log.debug("Updated information for image: {}", imageToBeChanged);
        return imageToBeChanged;
    }

    public void deleteImage(Long imageId) {
        //Checks if this image exists
        if (imageRepository.findImageByImageId(imageId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("The image with this id does not exist!"));
        }

        imageRepository.deleteImageByImageId(imageId);
        System.out.println(imageRepository.count());
        System.out.println("deleted");
        log.debug("Image deleted");
    }
}
