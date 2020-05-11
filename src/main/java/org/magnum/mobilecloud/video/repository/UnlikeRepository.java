package org.magnum.mobilecloud.video.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

/**
 * User: lin Date: 5.08.2020 Time: 19:45*/
 
@RepositoryRestResource
public interface UnlikeRepository extends CrudRepository<Unlike, Long> {

    Set<Unlike> findByVideo_id(@Param("id") long id);

    Unlike findByVideo_idAndUser(@Param("id") long id, @Param("user") String user);
}