package ch.uzh.ifi.hase.soprafs22.repository;


import ch.uzh.ifi.hase.soprafs22.entity.Category;
import ch.uzh.ifi.hase.soprafs22.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("imageRepository")
public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findImageByImageId(Long imageId);

    Image findImageByStorageLink(String storageLink);

    //Somehow this does not work custom query below does the job but fix later
    //void deleteImageByImageId(Long imageId);
    @Modifying
    @Query("delete from Image b where b.imageId=:imageId")
    void deleteImageByImageId(@Param("imageId") Long imageId);

    List<Image> findImagesByOwnerUserId(Long userId);

    @Query(value = "SELECT * from Image ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Image findRandomImage();

    //Where 40 = False
    @Query(value = "SELECT * from Image WHERE category=:categoryGiven ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Image findRandomImageFromCategory(@Param("categoryGiven") String categoryGiven);

    // HAVING uploadDate  > now() - interval 7 day something like that add later
    @Query(value = "SELECT * FROM image WHERE category=:categoryGiven ORDER BY RATING DESC LIMIT 3", nativeQuery = true)
    List<Image> findHighlightsFromCategory(@Param("categoryGiven") String categoryGiven);

    //Returns true or false depending on if the user already rated this picture
    @Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM IMAGES_RATED_BY WHERE USER_ID =:userId AND IMAGE_ID=:imageId) THEN TRUE ELSE FALSE END AS bool", nativeQuery = true)
    Boolean ratingCheck(@Param("userId") Long userId, @Param("imageId") Long imageId);
}
