package ro.cristian.accesaquest;

import ro.cristian.accesaquest.models.Badge;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BadgeTest {
    @Test
    public void createClassTest() {
        String name = "abc";
        String desc = "short description";
        String path = "random path";
        Badge badge = new Badge(name, desc, path);
        assertEquals(name, badge.getName());
        assertEquals(desc, badge.getDescription());
        assertEquals(path, badge.getImagePath());
    }

}
