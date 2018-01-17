package org.dbiagi.marketplace.core.importer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dbiagi.marketplace.core.entity.VehicleFeature;
import org.dbiagi.marketplace.core.repository.VehicleFeatureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.CompletableFuture;

@Service
public class VehicleFeaturesImporter extends AbstractImporter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private VehicleFeatureRepository vehicleFeatureRepository;

    public VehicleFeaturesImporter(VehicleFeatureRepository vehicleFeatureRepository) {
        this.vehicleFeatureRepository = vehicleFeatureRepository;
    }

    public CompletableFuture<ImportResult> runAsync(File file) throws Exception {
        return CompletableFuture.completedFuture(run(file));
    }

    public ImportResult run(File file) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(file);
        String[] supports = {"Carro", "Caminhão", "Moto"};
        ImportResult result = new ImportResult();
        result.start();

        for (String type : supports) {
            root.get(type).forEach(f -> {
                try {
                    VehicleFeature vehicleFeature = new VehicleFeature(f.asText(), extractType(type));
                    vehicleFeatureRepository.save(vehicleFeature);
                    result.add();
                } catch (Exception e) {
                    logger.error("Error persisting car feature", e);
                }
            });
        }

        result.stop();

        return result;
    }
}
