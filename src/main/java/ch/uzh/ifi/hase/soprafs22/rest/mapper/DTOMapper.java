package ch.uzh.ifi.hase.soprafs22.rest.mapper;

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

    @Mapping(source = "user_id", target = "user_id")
    @Mapping(source = "token", target = "token")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "more_info", target = "more_info")
    User convertUserPostDTOtoEntity(UserPostDTO userPostDTO);

    @Mapping(source = "user_id", target = "user_id")
    @Mapping(source = "token", target = "token")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "more_info", target = "more_info")
    UserGetDTO convertEntityToUserGetDTO(User user);

    @Mapping(source = "user_id", target = "user_id")
    @Mapping(source = "token", target = "token")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "more_info", target = "more_info")
    User convertUserPutDTOtoEntity(UserPutDTO userPutDTO);

    @Mapping(source = "categoryId", target = "categoryId")
    @Mapping(source = "category", target = "category")
    CategoryGetDTO convertEntityToCategoryGetDTO(Category category);

    //@Mapping(source = "categoryId", target = "categoryId")
    @Mapping(source = "category", target = "category")
    Category convertCategoryPostDTOtoEntity(CategoryPostDTO categoryPostDTO);

}
