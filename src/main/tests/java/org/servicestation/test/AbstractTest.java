package org.servicestation.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/test-application-context.xml"})
public abstract class AbstractTest {

    private static final String INITIALIZE_TEST_DATA_FILE = "scripts/initialize_test_data_script.sql";

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private boolean initialized = false;

    @Before
    public void prepareData() throws IOException {
        if(initialized) return;

        StringBuffer buffer = new StringBuffer();
        URL resource = getClass().getClassLoader().getResource(INITIALIZE_TEST_DATA_FILE);
        assert resource != null;
        try(Scanner reader = new Scanner(resource.openStream())) {
            while(reader.hasNextLine()) {
                buffer.append(reader.nextLine());
            }
        }
        jdbcTemplate.execute(buffer.toString());
        initialized = true;
    }
}
