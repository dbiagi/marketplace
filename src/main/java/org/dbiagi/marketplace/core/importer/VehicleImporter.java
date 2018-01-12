package org.dbiagi.marketplace.core.importer;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.sun.istack.internal.Nullable;
import org.dbiagi.marketplace.core.entity.Vehicle;
import org.dbiagi.marketplace.core.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class VehicleImporter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private VehicleRepository vehicleRepository;

    @Autowired
    VehicleImporter(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Async
    public CompletableFuture<ImportResult> runAsync(@Nullable Map<String, String> mapping, File file) {
        try {
            return CompletableFuture.completedFuture(run(mapping, file));
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }

        return null;
    }

    public ImportResult run(@Nullable Map<String, String> mapping, File file) throws IOException {
        HeaderColumnNameTranslateMappingStrategy<Vehicle> strategy = new HeaderColumnNameTranslateMappingStrategy<>();
        strategy.setType(Vehicle.class);
        strategy.setColumnMapping(mapping);

        ImportResult result = new ImportResult(0, 0);

        CsvToBean<Vehicle> csvToBean = new CsvToBeanBuilder<Vehicle>(new FileReader(file))
                .withMappingStrategy(strategy)
                .withIgnoreLeadingWhiteSpace(true)
                .withSeparator(';')
                .build();

        csvToBean.forEach(v -> {
            try {
                vehicleRepository.save((Vehicle) v);
                result.addCount();
            } catch (Exception e) {
                logger.error("Erro na importação do veículo.", e);
            }
        });

        return result;
    }
}
