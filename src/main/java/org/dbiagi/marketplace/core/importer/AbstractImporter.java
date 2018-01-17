package org.dbiagi.marketplace.core.importer;

import org.dbiagi.marketplace.core.entity.Vehicle;

import java.io.File;
import java.util.concurrent.CompletableFuture;

abstract class AbstractImporter {
    public abstract ImportResult run(File file) throws Exception;

    public abstract CompletableFuture<ImportResult> runAsync(File file) throws Exception;

    Vehicle.TypeEnum extractType(String type) throws Exception {
        switch (type.toLowerCase()) {
            case "carro":
                return Vehicle.TypeEnum.CAR;
            case "caminhão":
                return Vehicle.TypeEnum.TRUCK;
            case "moto":
                return Vehicle.TypeEnum.MOTORCICLE;
            case "barco":
                return Vehicle.TypeEnum.BOAT;
            default:
                throw new Exception(String.format("Type %s is not a valid type.", type));
        }
    }
}
