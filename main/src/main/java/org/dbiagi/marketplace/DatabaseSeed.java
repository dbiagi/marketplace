package org.dbiagi.marketplace;

import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import lombok.extern.log4j.Log4j2;
import org.dbiagi.marketplace.entity.Account;
import org.dbiagi.marketplace.entity.Listing;
import org.dbiagi.marketplace.entity.Setting;
import org.dbiagi.marketplace.entity.classification.Category;
import org.dbiagi.marketplace.entity.classification.Context;
import org.dbiagi.marketplace.repository.CategoryRepository;
import org.dbiagi.marketplace.repository.ContextRepository;
import org.dbiagi.marketplace.repository.ListingRepository;
import org.dbiagi.marketplace.repository.SettingRepository;
import org.dbiagi.marketplace.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
@Log4j2
public class DatabaseSeed implements ApplicationRunner {
    private static final int LISTINGS = 30;

    private final ContextRepository contextRepository;
    private final Faker faker;
    private final ListingRepository listingRepository;
    private final SettingRepository settingRepository;
    private final CategoryRepository categoryRepository;
    private AccountService accountService;
    private Slugify slugify;

    @Autowired
    public DatabaseSeed(
        AccountService accountService,
        SettingRepository settingRepository,
        ContextRepository contextRepository,
        ListingRepository listingRepository,
        CategoryRepository categoryRepository,
        Faker faker,
        Slugify slugify) {
        this.accountService = accountService;
        this.settingRepository = settingRepository;
        this.contextRepository = contextRepository;
        this.listingRepository = listingRepository;
        this.categoryRepository = categoryRepository;
        this.faker = faker;
        this.slugify = slugify;
    }

    private void createUsers() {
        List<Account> accounts = new LinkedList<>();

        for (Account.Role role : Account.Role.values()) {
            Account account = new Account();
            account.setRole(role);
            account.setName(role.name());
            account.setUsername(role.name());
            account.setPlainPassword("123");
            account.setEmail(faker.internet().emailAddress());
            account.setEnabled(true);
            account.setExpired(false);
            accounts.add(account);
        }

        accountService.save(accounts);
    }

    private void createListings() {
        String[] categoriesTitle = {
            "Auto", "Beauty", "Food & Dine", "Entertainment", "Health & Care",
            "Restaurant", "Medical", "Music", "Theater"
        };

        Context context = new Context("listing");
        contextRepository.save(context);

        ArrayList<Category> categories = new ArrayList<>();

        for (String categoryTitle : categoriesTitle) {
            Category category = new Category();
            category.setContext(context);
            category.setName(categoryTitle);
            category.setSlug(slugify.slugify(category.getName()));

            for (int i = faker.number().numberBetween(0, 3); i > 0; i--) {
                Category child = new Category();
                child.setContext(context);
                child.setName(faker.lorem().word());
                child.setSlug(slugify.slugify(child.getName()));
                category.addChild(child);
            }

            categories.add(category);
        }

        categoryRepository.saveAll(categories);

        ArrayList<Listing> listings = new ArrayList<>();
        for (int i = 0; i < LISTINGS; i++) {
            Listing l = new Listing();
            l.setLongDescription(faker.lorem().sentence(20, 10));
            l.setShortDescription(faker.lorem().sentence());
            l.setTitle(faker.lorem().sentence(2));
            l.setSlug(slugify.slugify(l.getTitle()));
            l.setFeatured(faker.bool().bool());

            for (int j = faker.number().numberBetween(1, 3); j > 0; j--) {
                l.getCategories().add(categories.get(faker.number().numberBetween(1, categoriesTitle.length - 1)));
            }

            listings.add(l);
        }

        listingRepository.saveAll(listings);
    }

    @Override
    public void run(ApplicationArguments args) {
        Optional<Setting> optional = settingRepository.findByKey("database_version");

        if (optional.isPresent() && "1.0.0".equals(optional.get().getValue())) {
            log.info("Database already have initial data. Skipping seed.");

            return;
        }

        createUsers();
        createListings();

        settingRepository.save(new Setting("database_version", "1.0.0"));
    }
}
