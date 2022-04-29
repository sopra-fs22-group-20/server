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

    //Somehow this does not work custom query below does the job but fix later
    //void deleteImageByImageId(Long imageId);
    @Modifying
    @Query("delete from Image b where b.imageId=:imageId")
    void deleteImageByImageId(@Param("imageId") Long imageId);

    List<Image> findImagesByOwnerUserId(Long userId);

    @Query(value = "SELECT * from Image ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Image findRandomImage();
}
