package com.obs.dqsc.api.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


/**
 * @author KDFL4681
 * @since 11-09-2021
 * @see org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
 */
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
    @Value("${spring.data.mongodb.database}")
    private String dbname;
    @Value("${spring.data.mongodb.username}")
    private String username;
    @Value("${spring.data.mongodb.password}")
    private String password;
    @Value("${spring.data.mongodb.host.dev1}")
    private String dev1;
    @Value("${spring.data.mongodb.host.dev2}")
    private String dev2;
    @Value("${spring.data.mongodb.port}")
    private String port;
    @Value("${spring.data.mongodb.replica-set-name}")
    private String replica;
    @Value("${spring.data.mongodb.authentication-database}")
    private String authSource;

    private static final ZoneId ZONE_UTC = ZoneId.of("UTC");
    private static final String PACKAGE_LEVEL ="com.obs";


    @Override
    protected String getDatabaseName() {
        return dbname;
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(
                "mongodb://" + username + ":" + password +
                        "@" + dev1 + ":" + port
                        + "," + dev2 + ":" + port +
                        "/?authSource=" + authSource +
                        "&replicaSet=" + replica
        );

        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Override
    public Collection<String> getMappingBasePackages() {
        return Collections.singleton(PACKAGE_LEVEL);
    }


    @Bean
    public MongoCustomConversions mongoCustomConversions() {

        return new MongoCustomConversions(
                Arrays.asList(
                        new LocalDateToDateConverter(),
                        new DateToLocalDate()));
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient(), dbname);
        MappingMongoConverter mongoMapping = (MappingMongoConverter) mongoTemplate.getConverter();
        mongoMapping.setCustomConversions(customConversions());
        mongoMapping.afterPropertiesSet();
        return mongoTemplate;
    }

    @Override
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new LocalDateToDateConverter());
        converters.add(new DateToLocalDate());
        return new MongoCustomConversions(converters);
    }

    @WritingConverter
    public static class LocalDateToDateConverter implements Converter<LocalDate, Date> {
        @Override
        public Date convert(LocalDate localDate) {
            return Date.from(
                    localDate.atStartOfDay(ZONE_UTC).toInstant()
            );
        }
    }

    @ReadingConverter
    public static class DateToLocalDate implements Converter<Date, LocalDate> {
        @Override
        public LocalDate convert(Date date) {
            return date.toInstant().atZone(ZONE_UTC).toLocalDate();
        }
    }


}