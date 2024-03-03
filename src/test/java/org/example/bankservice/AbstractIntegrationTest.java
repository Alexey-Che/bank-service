package org.example.bankservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AbstractIntegrationTest {

    public static final String ACCESS_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwiaWQiOjYsInN1YiI6InRlc3RfdXNlcjEiLCJpYXQiOjE3MDk0NjY4MDIsImV4cCI6MTc2MjAyNjgwMn0.XUWcczlZmOcp1qhipIvbip_cbkSXL0qab2KUR_CoPMtZsALX7EIz4JNnKzvcUu518PdnAF-sAGpKR5GL2rxzyw";
    public static final String ACCESS_HEADER = "Authorization";
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    public MockMvc mockMvc;

}
