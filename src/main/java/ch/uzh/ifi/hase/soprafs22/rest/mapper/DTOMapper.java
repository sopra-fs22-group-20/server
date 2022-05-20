package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import ch.uzh.ifi.hase.soprafs22.entity.Image;
import ch.uzh.ifi.hase.soprafs22.entity.Category;
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


    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    User convertUserPostDTOtoEntity(UserPostDTO userPostDTO);

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "moreInfo", target = "moreInfo")
    @Mapping(source = "highlightCounter", target = "highlightCounter")
    @Mapping(source = "creationDate", target = "creationDate")
    @Mapping(source = "trophies", target = "trophies")
    UserGetDTO convertEntityToUserGetDTO(User user);

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "moreInfo", target = "moreInfo")
    @Mapping(source = "trophies", target = "trophies")
    User convertUserPutDTOtoEntity(UserPutDTO userPutDTO);

    @Mapping(source = "category", target = "category")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "storageLink", target = "storageLink")
    Image convertImagePostDTOtoEntity(ImagePostDTO imagePostDTO);

    @Mapping(source = "imageId", target = "imageId")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "owner", target = "owner")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "uploadDate", target = "uploadDate")
    @Mapping(source = "rating", target = "rating")
    @Mapping(source = "storageLink", target = "storageLink")
    @Mapping(source = "classification", target = "classification")
    @Mapping(source = "reachedHighlights", target = "reachedHighlights")
    @Mapping(source = "ratingCounter", target = "ratingCounter")
    ImageGetDTO convertEntityToImageGetDTO(Image image);

    @Mapping(source = "imageId", target = "imageId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "rating", target = "rating")
    @Mapping(source = "classification", target = "classification")
    Image convertImagePutDTOtoEntity(ImagePutDTO imagePutDTO);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "images", target = "images")
    CategoryGetDTO convertEntityToCategoryGetDTO(Category category);

    @Mapping(source = "name", target = "name")
    @Mapping(target = "images", ignore = true)
    Category convertCategoryPostDTOtoEntity(CategoryPostDTO categoryPostDTO);

}
