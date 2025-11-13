package com.ogirafferes.lxp.sales.domain.repository;

import com.ogirafferes.lxp.catalog.domain.model.Course;
import com.ogirafferes.lxp.identity.domain.model.User;
import com.ogirafferes.lxp.sales.domain.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findAllByIdIn(List<Long> cartItemIds);
    List<CartItem> findAllByUserId(Long userId);

    boolean existsByUserIdAndCourseId(Long userId, Long courseId);
//    boolean existsByUserAndCourse(User user, Course course);
}
