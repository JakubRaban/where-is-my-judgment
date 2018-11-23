package pl.jakubraban.whereismyjudgment.storage;

import org.junit.Test;
import pl.jakubraban.whereismyjudgement.data.judgment.Judgment;
import pl.jakubraban.whereismyjudgement.storage.JudgmentDatabase;
import pl.jakubraban.whereismyjudgement.storage.JudgmentDatabaseProvider;
import java.util.Random;
import static org.junit.Assert.*;

public class JudgmentDatabaseTest {

    @Test
    public void testDatabaseIntegrity() {
        JudgmentDatabase d1 = JudgmentDatabaseProvider.getDatabase();
        assertNotNull(d1);
        JudgmentDatabase d2 = JudgmentDatabaseProvider.getDatabase();
        assertNotNull(d2);
        d1.add(Integer.toString(new Random().nextInt()), new Judgment());
        assertEquals(1, d1.size());
        assertEquals(d1.size(), d2.size());
        d2.add(Integer.toString(new Random().nextInt()), new Judgment());
        assertEquals(d1.size(), d2.size());
    }

}
