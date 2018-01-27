package org.dbiagi.marketplace.core.repository;

import org.dbiagi.marketplace.core.entity.Setting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SettingRepository extends CrudRepository<Setting, Long> {
    @Query("SELECT s.value FROM Setting s WHERE s.key = :key")
    public String findValueByKey(@Param("key") String key);

    public Setting findByKey(String key);
}
