package hanghae.review;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Sql(value = "/delete-all-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public abstract class IntegrationTestSupport {
}
