package com.ogirafferes.lxp.community.domain.repository;

import com.ogirafferes.lxp.community.domain.model.PostType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTypeRepository extends CrudRepository<PostType, Long> {
}
