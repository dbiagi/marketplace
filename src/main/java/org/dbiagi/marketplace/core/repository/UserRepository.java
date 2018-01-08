package org.dbiagi.marketplace.core.repository;

import org.dbiagi.marketplace.core.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
}
