package org.dbiagi.marketplace.core.importer;

import org.dbiagi.marketplace.core.repository.VehicleFeatureRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class VehicleFeaturesImporterTest {

    private static VehicleFeatureRepository vehicleFeatureRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public static void setUp() {
        vehicleFeatureRepository = mock(VehicleFeatureRepository.class);
    }

    @Test
    public void testRun() {
        VehicleFeaturesImporter vehicleFeaturesImporter = new VehicleFeaturesImporter(vehicleFeatureRepository);

        try {
            ClassPathResource classPathResource = new ClassPathResource("vehicle-features.json");

            ImportResult result = vehicleFeaturesImporter.run(classPathResource.getFile());

            assertTrue(result.getCount() > 0);
        } catch (Exception e) {
            logger.error("Error importing vehicle features", e);
        }
    }
}
