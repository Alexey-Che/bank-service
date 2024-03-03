package org.example.bankservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AbstractIntegrationTest {

    public static final String ACCESS_HEADER = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwiaWQiOjQsInN1YiI6InRlc3RfdXNlcm5hbWUiLCJpYXQiOjE3MDk0NjMyNjUsImV4cCI6MTc2MjAyMzI2NX0.hY8qw7paMjznA0_GJ4PnGIpSP1V7LQuzQHmwapVXjLi7cVfzTloFs2sIky3lv06l_ExvrEQn8Ga813R2o0jMwA";
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    public MockMvc mockMvc;

}
