package org.dbiagi.marketplace.core.importer;

import org.dbiagi.marketplace.core.repository.VehicleRepository;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;

public class VehicleImporterTest {

    private static VehicleRepository vehicleRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public static void setUp() {
        vehicleRepository = mock(VehicleRepository.class);
    }

    @Test
    public void testRun() {
        ClassPathResource classPathResource = new ClassPathResource("todos-veiculos-utf-8.csv");
        VehicleImporter vehicleImporter = new VehicleImporter(vehicleRepository);

        try {
            ImportResult result = vehicleImporter.run(classPathResource.getFile(), getMapping());

            Assert.assertTrue(result.getCount() > 0);
        } catch (Exception e) {
            logger.error("Error running import vehicle", e);
        }
    }

    private Map<String, String> getMapping() {
        Map<String, String> mapping = new HashMap<>();

        mapping.put("type", "TIPO");
        mapping.put("brand", "MARCA");
        mapping.put("model", "MODELO");
        mapping.put("version", "VERSÃO");

        return mapping;
    }
}
