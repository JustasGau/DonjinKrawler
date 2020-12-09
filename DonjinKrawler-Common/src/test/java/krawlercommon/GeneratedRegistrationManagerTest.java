package krawlercommon;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.util.ListReferenceResolver;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("generated")
public class GeneratedRegistrationManagerTest {
    @Test
    public void testRegisterKryo() {
        Kryo kryo = new Kryo();
        RegistrationManager.registerKryo(kryo);
        assertEquals(64, kryo.getNextRegistrationId());
    }

    @Test
    public void testRegisterKryo2() {
        Kryo kryo = new Kryo(new ListReferenceResolver());
        RegistrationManager.registerKryo(kryo);
        assertEquals(64, kryo.getNextRegistrationId());
    }
}

