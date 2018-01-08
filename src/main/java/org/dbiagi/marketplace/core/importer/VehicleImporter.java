package org.dbiagi.marketplace.core.importer;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import org.dbiagi.marketplace.core.entity.Vehicle;
import org.dbiagi.marketplace.core.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class VehicleImporter {

    @Autowired
    private VehicleRepository vehicleRepository;


    // @TODO improve this thing to read from a stream and test
    @Async
    public CompletableFuture<ImportResult> run() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("todos-veiculos.csv");

        File file = classPathResource.getFile();

        FileReader fr = new FileReader(file);

        HeaderColumnNameTranslateMappingStrategy strategy = new HeaderColumnNameTranslateMappingStrategy();
        strategy.setType(Vehicle.class);
        strategy.setColumnMapping(getMapping());

        CsvToBean csvToBean = new CsvToBeanBuilder(fr)
                .withMappingStrategy(strategy)
                .withSkipLines(1)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        try {
            List<Vehicle> vehicles = csvToBean.parse();
            vehicleRepository.save(vehicles);
        } catch (Exception e) {
            System.out.println("#### Alguma coisa deu ruim");
        }

        return CompletableFuture.completedFuture(new ImportResult());
    }

    public Map<String, String> getMapping() {
        Map<String, String> mapping = new HashMap<>();

        mapping.put("TIPO", "type");
        mapping.put("MARCA", "brand");
        mapping.put("MODELO", "model");
        mapping.put("VERSAO", "version");

        return mapping;
    }
}
