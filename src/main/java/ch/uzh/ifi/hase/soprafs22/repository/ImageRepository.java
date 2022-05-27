package ch.uzh.ifi.hase.soprafs22.repository;

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

    List<Image> findImagesByOwnerUserId(Long userId);

    @Modifying
    @Query("DELETE FROM Image b WHERE b.imageId=:imageId")
    void deleteImageByImageId(@Param("imageId") Long imageId);

    @Query(value = "SELECT * FROM IMAGE WHERE IMAGE.CLASSIFICATION=:classificationGiven ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Image findRandomImage(@Param("classificationGiven") int classificationGiven);

    //Where 40 = False
    @Query(value = "SELECT * FROM IMAGE WHERE category=:categoryGiven AND IMAGE.CLASSIFICATION=:classificationGiven ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Image findRandomImageFromCategory(@Param("categoryGiven") String categoryGiven, @Param("classificationGiven") int classificationGiven);

    // HAVING uploadDate  > now() - interval 7 day something like that add later
    @Query(value = "SELECT * FROM IMAGE WHERE category=:categoryGiven AND IMAGE.UPLOAD_DATE >= NOW() - INTERVAL 7 DAY ORDER BY RATING DESC LIMIT 3", nativeQuery = true)
    List<Image> findHighlightsFromCategory(@Param("categoryGiven") String categoryGiven);

    //Returns true or false depending on if the user already rated this picture
    @Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM IMAGES_RATED_BY WHERE USER_ID =:userId AND IMAGE_ID=:imageId) THEN TRUE ELSE FALSE END AS bool", nativeQuery = true)
    Boolean ratingCheck(@Param("userId") Long userId, @Param("imageId") Long imageId);

    @Query(value = "SELECT * FROM IMAGE WHERE IMAGE.BOOST_DATE <= NOW() - INTERVAL 1 DAY ", nativeQuery = true)
    List<Image> checkClassifications();

    @Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM IMAGE WHERE IMAGE_ID=:imageId AND IMAGE.BOOST_DATE >= NOW() - INTERVAL 1 DAY)THEN TRUE ELSE FALSE END AS bool ", nativeQuery = true)
    boolean checkForBoost(@Param("imageId") Long imageId);
    //SELECT * FROM IMAGE WHERE IMAGE.BOOST_DATE <= NOW() - INTERVAL 1 DAY
}
