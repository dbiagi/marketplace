package org.dbiagi.marketplace.repository;

import org.dbiagi.marketplace.entity.Setting;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SettingRepository extends CrudRepository<Setting, Long> {
    Optional<Setting> findByKey(String key);
}
