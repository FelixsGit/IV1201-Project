package se.kth.iv1201.recruitmentsystem.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
public class DBUtil {

    @Autowired
    private Environment env;
    private static DataSource db;
    private boolean autoCommit;

    @PostConstruct
    public void createPool() {
        autoCommit = false;
        if (db == null) {
            HikariConfig config = new HikariConfig();
            config.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
            config.setJdbcUrl(env.getProperty("spring.datasource.url"));
            config.setUsername(env.getProperty("spring.datasource.username"));
            config.setPassword(env.getProperty("spring.datasource.password"));
            db = new HikariDataSource(config);
        }
    }

    public void emptyDb() throws IOException {
        runScript(new BufferedReader(new FileReader(env.getProperty("se.kth.iv1201.db.createTables"))));
    }

    private void runScript(BufferedReader bufferedReader) {
        
    }


}
