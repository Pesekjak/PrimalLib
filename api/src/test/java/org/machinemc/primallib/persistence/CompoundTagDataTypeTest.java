package org.machinemc.primallib.persistence;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import lombok.experimental.ExtensionMethod;
import net.kyori.adventure.nbt.*;
import org.bukkit.NamespacedKey;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@ExtensionMethod(PersistentDataContainerExtension.class)
public class CompoundTagDataTypeTest {

    private PlayerMock player;

    @BeforeEach
    public void setUp() {
        ServerMock server = MockBukkit.mock();
        player = server.addPlayer();
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void testSingle() {
        CompoundBinaryTag tag = CompoundBinaryTag.builder()
                .put("Hello", StringBinaryTag.stringBinaryTag("world!"))
                .build();
        player.getPersistentDataContainer().setTag(NamespacedKey.minecraft("test"), tag);
        CompoundBinaryTag copy = player.getPersistentDataContainer().getTag(NamespacedKey.minecraft("test"), BinaryTagTypes.COMPOUND);

        assert copy.getString("Hello").equals("world!");
    }

    @Test
    public void testMultiple() {
        CompoundBinaryTag tag = CompoundBinaryTag.builder()
                .put("Hello", StringBinaryTag.stringBinaryTag("world!"))
                .put("aNumber", ByteBinaryTag.byteBinaryTag((byte) 15))
                .put("anArray", IntArrayBinaryTag.intArrayBinaryTag(1, 2, 3))
                .build();
        player.getPersistentDataContainer().setTag(NamespacedKey.minecraft("test"), tag);
        CompoundBinaryTag copy = player.getPersistentDataContainer().getTag(NamespacedKey.minecraft("test"), BinaryTagTypes.COMPOUND);

        assert copy.getString("Hello").equals("world!");
        assert copy.getByte("aNumber") == 15;
        assert Arrays.compare(copy.getIntArray("anArray"), new int[] {1, 2, 3}) == 0;
    }

    @Test
    public void testList() {
        ListBinaryTag tag = ListBinaryTag.builder()
                .add(IntBinaryTag.intBinaryTag(1))
                .add(IntBinaryTag.intBinaryTag(2))
                .add(IntBinaryTag.intBinaryTag(3))
                .build();
        player.getPersistentDataContainer().setTag(NamespacedKey.minecraft("test"), tag);
        ListBinaryTag copy = player.getPersistentDataContainer().getTag(NamespacedKey.minecraft("test"), BinaryTagTypes.LIST);

        assert copy.getInt(0) == 1;
        assert copy.getInt(1) == 2;
        assert copy.getInt(2) == 3;
    }

}
