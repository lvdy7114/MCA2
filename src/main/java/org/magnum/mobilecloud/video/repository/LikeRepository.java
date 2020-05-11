package org.magnum.mobilecloud.video.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
import java.util.Set;

/**
 * User: lin Date: 5.08.2020 Time: 19:45*/
 
@RepositoryRestResource
public interface LikeRepository extends CrudRepository<Like, Long> {

    Set<Like> findByVideo_id(@Param("id") long id);

    Like findByVideo_idAndUser(@Param("id") long id, @Param("user") String user);

    @Query("SELECT DISTINCT l.user FROM Like l WHERE l.video in " +
            "(SELECT v FROM Video v WHERE v.id = :id)")
    Collection<String> findUsersByVideoId(@Param("id") long id);
}