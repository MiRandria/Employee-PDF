package com.example.prog4.repository.configuration;

import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywaySlaveInitializer {
  private final DataSource baseDataSource;
  private final DataSource cnapsDataSource;

  public FlywaySlaveInitializer(DataSource baseDataSource,
                                @Qualifier("cnapsEmployeeDataSource")
                                DataSource cnapsDataSource) {
    this.baseDataSource = baseDataSource;
    this.cnapsDataSource = cnapsDataSource;
  }

  @PostConstruct
  public void migrate() {
    migrateDataSource(baseDataSource, "classpath:/db/migration/base");
    migrateDataSource(cnapsDataSource, "classpath:/db/migration/cnaps");
  }

  private void migrateDataSource(DataSource dataSource, String... locations) {
    Flyway flyway = Flyway.configure()
        .dataSource(dataSource)
        .locations(locations).load();
    flyway.migrate();
  }
}
