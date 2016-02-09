package de.jibin.poi.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;

import de.jibin.commons.Constants;

@Configuration
@EnableMongoRepositories(basePackages = "de.jibin.salesrecord.db.dao")
public class TestApplicationConfig 
{
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        String localMongoDbHost = Constants.LOCAL_IP_ADDR;
        int openshiftMongoDbPort = 27017;
//        String username = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
//        String password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");
//        UserCredentials userCredentials = new UserCredentials(username, password);
        Mongo mongo = new Mongo(localMongoDbHost, openshiftMongoDbPort);
        String databaseName = Constants.TEST_DB_NAME;
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongo, databaseName);
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);
        return mongoTemplate;
    }
}
