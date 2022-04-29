package intellij;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    public void AppTest(){
        App app=new App();
        App app1=app;
        assertNotNull(app);
        assertEquals(app1,app);
    }

}