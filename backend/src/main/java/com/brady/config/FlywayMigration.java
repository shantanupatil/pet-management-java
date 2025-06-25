package com.brady.config;

import javax.sql.DataSource;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.*;
import org.flywaydb.core.Flyway;

@Startup
@Singleton
public class FlywayMigration {

    @Resource(lookup = "java:/jdbc/H2DS")
    private DataSource dataSource;

    @PostConstruct
    public void migrate() {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .load();

        flyway.migrate();
        System.out.println("[DONE] Flyway migrations applied successfully!");
    }
}
