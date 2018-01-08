package org.dbiagi.marketplace.core.repository;

import org.dbiagi.marketplace.core.entity.Vehicle;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VehicleRepository extends PagingAndSortingRepository<Vehicle, Long> {
}
