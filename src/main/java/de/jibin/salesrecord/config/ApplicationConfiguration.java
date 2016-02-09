package de.jibin.salesrecord.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;

import de.jibin.salesrecord.db.dao.CommunityTypeDAO;
import de.jibin.salesrecord.db.dao.FirstHandRecordDAO;
import de.jibin.salesrecord.db.dao.RoomTypeDAO;
import de.jibin.salesrecord.db.dao.SecondHandRecordDAO;

@Configuration
@EnableMongoRepositories(basePackageClasses = {CommunityTypeDAO.class, FirstHandRecordDAO.class, RoomTypeDAO.class, SecondHandRecordDAO.class})
public class ApplicationConfiguration
{
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        String openshiftMongoDbHost = System.getenv("OPENSHIFT_MONGODB_DB_HOST");
        int openshiftMongoDbPort = Integer.parseInt(System.getenv("OPENSHIFT_MONGODB_DB_PORT"));
        String username = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
        String password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");
        Mongo mongo = new Mongo(openshiftMongoDbHost, openshiftMongoDbPort);
        UserCredentials userCredentials = new UserCredentials(username, password);
        String databaseName = System.getenv("OPENSHIFT_APP_NAME");
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongo, databaseName, userCredentials);
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);
        return mongoTemplate;
    }
}
