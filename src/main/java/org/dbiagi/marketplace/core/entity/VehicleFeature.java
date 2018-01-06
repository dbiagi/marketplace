package org.dbiagi.marketplace.core.entity;

import javax.persistence.*;

@Entity
public class VehicleFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Vehicle.TypeEnum type;


}
