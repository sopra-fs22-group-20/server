package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import ch.uzh.ifi.hase.soprafs22.entity.Image;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * DTOMapper
 * This class is responsible for generating classes that will automatically
 * transform/map the internal representation
 * of an entity (e.g., the User) to the external/API representation (e.g.,
 * UserGetDTO for getting, UserPostDTO for creating)
 * and vice versa.
 * Additional mappers can be defined for new entities.
 * Always created one mapper for getting information (GET) and one mapper for
 * creating information (POST).
 */
@Mapper
public interface DTOMapper {

    DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "token", target = "token")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "moreInfo", target = "moreInfo")
    @Mapping(source = "highlightCounter", target = "highlightCounter")
    @Mapping(source = "creationDate", target = "creationDate")
        //@Mapping(source = "images", target = "images")
    User convertUserPostDTOtoEntity(UserPostDTO userPostDTO);

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "token", target = "token")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "moreInfo", target = "moreInfo")
    @Mapping(source = "highlightCounter", target = "highlightCounter")
    @Mapping(source = "creationDate", target = "creationDate")
        //@Mapping(source = "images", target = "images")
    UserGetDTO convertEntityToUserGetDTO(User user);

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "token", target = "token")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "moreInfo", target = "moreInfo")
    @Mapping(source = "highlightCounter", target = "highlightCounter")
    @Mapping(source = "creationDate", target = "creationDate")
        //@Mapping(source = "images", target = "images")
    User convertUserPutDTOtoEntity(UserPutDTO userPutDTO);


    @Mapping(source = "imageId", target = "imageId")
    @Mapping(source = "owner", target = "owner")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "uploadDate", target = "uploadDate")
    @Mapping(source = "rating", target = "rating")
    @Mapping(source = "storageLink", target = "storageLink")
    @Mapping(source = "classification", target = "classification")
    @Mapping(source = "reachedHighlights", target = "reachedHighlights")
    Image convertImagePostDTOtoEntity(ImagePostDTO imagePostDTO);


    @Mapping(source = "imageId", target = "imageId")
    @Mapping(source = "owner", target = "owner")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "uploadDate", target = "uploadDate")
    @Mapping(source = "rating", target = "rating")
    @Mapping(source = "storageLink", target = "storageLink")
    @Mapping(source = "classification", target = "classification")
    @Mapping(source = "reachedHighlights", target = "reachedHighlights")
    ImageGetDTO convertEntityToImageGetDTO(Image image);


    @Mapping(source = "imageId", target = "imageId")
    @Mapping(source = "owner", target = "owner")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "uploadDate", target = "uploadDate")
    @Mapping(source = "rating", target = "rating")
    @Mapping(source = "storageLink", target = "storageLink")
    @Mapping(source = "classification", target = "classification")
    @Mapping(source = "reachedHighlights", target = "reachedHighlights")
    Image convertImagePutDTOtoEntity(ImagePutDTO imagePutDTO);


}
