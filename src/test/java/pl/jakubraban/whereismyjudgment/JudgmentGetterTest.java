package pl.jakubraban.whereismyjudgment;

import org.junit.Test;
import pl.jakubraban.whereismyjudgement.JudgmentGetter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.*;

public class JudgmentGetterTest {

    @Test
    public void testGetAllJSONs() throws IOException {
        var getter = new JudgmentGetter(System.getProperty("user.dir") + "\\judgments\\dummy");
        List<Path> paths = getter.getAllJSONs();
        assertEquals(10, paths.size());
    }

}
