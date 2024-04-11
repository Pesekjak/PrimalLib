package org.machinemc.primallib.metadata;

import org.junit.jupiter.api.Test;

public class SerializerTest {

    @Test
    public void serializerToName() {
        Serializer<?> serializer = Serializer.INT;
        assert Serializer.getName(serializer).equals("INT".toLowerCase());

        serializer = Serializer.QUATERNION;
        assert Serializer.getName(serializer).equals("QUATERNION".toLowerCase());
    }

    @Test
    public void nameToSerializer() {
        assert Serializer.getByName("int") == Serializer.INT;
        assert Serializer.getByName("QUATERNION") == Serializer.QUATERNION;
    }

}
