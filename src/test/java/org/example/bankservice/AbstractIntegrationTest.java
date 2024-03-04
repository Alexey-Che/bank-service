package org.example.bankservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AbstractIntegrationTest {

    public static final String ACCESS_TOKEN_USER1 = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwiaWQiOjYsInN1YiI6InRlc3RfdXNlcjEiLCJpYXQiOjE3MDk0NjY4MDIsImV4cCI6MTc2MjAyNjgwMn0.XUWcczlZmOcp1qhipIvbip_cbkSXL0qab2KUR_CoPMtZsALX7EIz4JNnKzvcUu518PdnAF-sAGpKR5GL2rxzyw";
    public static final String ACCESS_TOKEN_USER2 = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwiaWQiOjcsInN1YiI6InRlc3RfdXNlcjIiLCJpYXQiOjE3MDk0NzMyMTYsImV4cCI6MjIzNTA3MzIxNn0.yovC79-BvneeHOUwhl4RCd5l4px7cAQqicgQ-Gjb069E72Yt7_SZ77liJ4FD1EvVZnUBxWLwQdCtEk0pxGHsvQ";
    public static final String ACCESS_HEADER = "Authorization";
    public final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public MockMvc mockMvc;

}
