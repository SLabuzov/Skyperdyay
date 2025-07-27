package by.skyperdyay.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = AppEntryPoint.class
)
public class AppEntryPointTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoad() {
        assertNotNull(context);
    }
}
