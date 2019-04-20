package org.dbiagi.marketplace.repository.rest;

import org.dbiagi.marketplace.entity.Setting;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface SettingRepository extends PagingAndSortingRepository<Setting, Long> {
    Optional<Setting> findByKey(String key);
}
