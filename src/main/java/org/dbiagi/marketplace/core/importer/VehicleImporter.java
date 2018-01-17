package org.dbiagi.marketplace.core.importer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
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
import java.io.Reader;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class VehicleImporter extends AbstractImporter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private VehicleRepository vehicleRepository;

    VehicleImporter() {
    }

    @Autowired
    VehicleImporter(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Async
    public CompletableFuture<ImportResult> runAsync(File file, Map<String, String> mapping) throws IOException {
        return CompletableFuture.completedFuture(run(file, mapping));
    }

    public ImportResult run(File file, Map<String, String> mapping) throws IOException {
        ImportResult result = new ImportResult(0, 0);
        result.start();

        Reader reader = new FileReader(file);
        Iterable<CSVRecord> iterable = CSVFormat.DEFAULT
                .withDelimiter(';')
                .withAllowMissingColumnNames(false)
                .withFirstRecordAsHeader()
                .parse(reader);

        for (CSVRecord record : iterable) {
            try {
                Vehicle v = new Vehicle();
                v.setType(extractType(record.get(mapping.get("type"))));
                v.setBrand(record.get(mapping.get("brand")));
                v.setModel(record.get(mapping.get("model")));
                v.setVersion(record.get(mapping.get("version")));

                vehicleRepository.save(v);

                result.add();
            } catch (Exception e) {
                result.addError(String.format("Error saving record number %d", record.getRecordNumber()));
            }
        }

        reader.close();

        result.stop();

        return result;
    }

    @Override
    public ImportResult run(File file) throws Exception {
        throw new Exception("Mapping should be provided");
    }

    @Override
    public CompletableFuture<ImportResult> runAsync(File file) throws Exception {
        throw new Exception("Mapping should be provided");
    }
}
