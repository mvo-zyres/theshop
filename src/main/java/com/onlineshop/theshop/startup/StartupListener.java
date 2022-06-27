package com.onlineshop.theshop.startup;

import com.onlineshop.theshop.typesense.SearchService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class StartupListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private InitDAO initDAO;

    @Autowired
    SearchService searchService;

    final Logger logger = LoggerFactory.getLogger(StartupListener.class);

    @Override
    public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {
        initDAO.initTables();
        searchService.addCollection();
        searchService.deleteAllProducts();
        searchService.addAllProducts();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        logger.info("Application started at: " + dateTimeFormatter.format(localDateTime));
    }
}
