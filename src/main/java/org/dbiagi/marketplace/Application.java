package org.dbiagi.marketplace;

import com.github.javafaker.Faker;
import org.dbiagi.marketplace.core.entity.Store;
import org.dbiagi.marketplace.core.entity.User;
import org.dbiagi.marketplace.core.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class Application implements ApplicationRunner {

    @Autowired
    private StoreRepository storeRepository;

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Faker faker = new Faker(new Locale("pt-BR"));

        for (int i = 0; i < 20; i++) {
            Store store = new Store();
            store.setAddress(faker.address().streetAddress());
            store.setCellphone(faker.phoneNumber().cellPhone());
            store.setPhone(faker.phoneNumber().phoneNumber());
            store.setName(faker.lorem().sentence(1));
            store.setCnpj(faker.number().digits(10));
            store.setType(Store.StoreTypeEnum.STORE);
            store.setNumber(faker.address().streetAddressNumber());

            for (int j = 0; j < 2; j++) {
                User user = new User();
                user.setCellphone(faker.phoneNumber().cellPhone());
                user.setConnected(false);
                user.setName(faker.name().name());
                store.addUser(user);
            }

            storeRepository.save(store);
        }
    }
}
