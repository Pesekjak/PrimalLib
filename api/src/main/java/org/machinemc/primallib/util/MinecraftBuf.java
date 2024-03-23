package org.machinemc.primallib.util;

import com.google.common.base.Preconditions;
import io.netty.buffer.*;
import io.papermc.paper.math.BlockPosition;
import io.papermc.paper.math.Position;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntFunction;

/**
 * A packet byte buf is a specialized byte buf with utility methods adapted
 * to Minecraft's protocol. It has serialization and deserialization of
 * custom objects.
 */
@SuppressWarnings("deprecation") // duplicated byte buf is safe to extend
public class MinecraftBuf extends DuplicatedByteBuf {

    /**
     * Creates new buffer that delegates all methods calls to
     * another, already existing one.
     *
     * @param delegate delegate buffer
     */
    public MinecraftBuf(final ByteBuf delegate) {
        super(Preconditions.checkNotNull(delegate, "Delegate buffer can not be null"));
    }

    /**
     * Creates new byte buffer from Unpooled buffer with
     * unlimited capacity.
     *
     * @return buffer
     */
    public static MinecraftBuf unpooled() {
        return new MinecraftBuf(Unpooled.buffer());
    }

    /**
     * Returns input stream wrapped around this buffer.
     *
     * @return input stream
     */
    public InputStream asInputStream() {
        return new ByteBufInputStream(this);
    }

    /**
     * Returns output stream wrapped around this buffer.
     *
     * @return output stream
     */
    public OutputStream asOutputstream() {
        return new ByteBufOutputStream(this);
    }

    /**
     * Uses function to read custom element from the buffer.
     *
     * @param function function
     * @return read element
     * @param <T> element
     */
    public <T> T read(final Function<MinecraftBuf, T> function) {
        return Preconditions.checkNotNull(function).apply(this);
    }

    /**
     * Uses bi-consumer to write custom element to the buffer.
     *
     * @param element element to write
     * @param consumer consumer
     * @return this
     * @param <T> type of the element
     */
    @Contract("_, _ -> this")
    public <T> MinecraftBuf write(final @Nullable T element, final BiConsumer<MinecraftBuf, T> consumer) {
        Preconditions.checkNotNull(consumer).accept(this, element);
        return this;
    }

    /**
     * Writes writable instance to this buffer.
     *
     * @param writable element to write
     * @return this
     * @param <T> type of the writable element
     */
    @Contract("_ -> this")
    public <T extends Writable> MinecraftBuf write(final T writable) {
        writable.write(this);
        return this;
    }

    /**
     * Reads with var int length-prefixed array from the buffer.
     *
     * @param generator array generator
     * @param function function for reading elements
     * @return array
     * @param <T> type of the array
     */
    public <T> T[] readArray(final IntFunction<T[]> generator, final Function<MinecraftBuf, T> function) {
        Preconditions.checkNotNull(generator);
        Preconditions.checkNotNull(function);
        final T[] array = generator.apply(readVarInt());
        for (int i = 0; i < array.length; i++) array[i] = function.apply(this);
        return array;
    }

    /**
     * Writes array to the buffer length-prefixed with var int.
     *
     * @param array array to write
     * @param consumer bi-consumer for writing elements
     * @return this
     * @param <T> type of the array
     */
    @Contract("_, _ -> this")
    public <T> MinecraftBuf writeArray(final T[] array, final BiConsumer<MinecraftBuf, T> consumer) {
        Preconditions.checkNotNull(array);
        Preconditions.checkNotNull(consumer);
        writeVarInt(array.length);
        for (final T value : array) consumer.accept(this, value);
        return this;
    }

    /**
     * Reads with var int length-prefixed list from the buffer.
     *
     * @param function function for reading elements
     * @return list
     * @param <T> type of the list
     */
    public <T> List<T> readList(final Function<MinecraftBuf, T> function) {
        Preconditions.checkNotNull(function);
        final List<T> list = new ArrayList<>();
        final int length = readVarInt();
        for (int i = 0; i < length; i++)
            list.add(function.apply(this));
        return Collections.unmodifiableList(list);
    }

    /**
     * Writes list to the buffer length-prefixed with var int.
     *
     * @param list list to write
     * @param consumer bi-consumer for writing elements
     * @return this
     * @param <T> type of the list
     */
    @Contract("_, _ -> this")
    public <T> MinecraftBuf writeList(final List<T> list, final BiConsumer<MinecraftBuf, T> consumer) {
        Preconditions.checkNotNull(list);
        Preconditions.checkNotNull(consumer);
        writeVarInt(list.size());
        for (final T item : list)
            consumer.accept(this, item);
        return this;
    }

    /**
     * Reads optional element from the buffer.
     *
     * @param function function to read the element
     * @return optional
     * @param <T> type of the element
     */
    public <T> Optional<T> readOptional(final Function<MinecraftBuf, T> function) {
        Preconditions.checkNotNull(function);
        if (!readBoolean()) return Optional.empty();
        return Optional.ofNullable(function.apply(this));
    }

    /**
     * Writes optional element to the buffer.
     *
     * @param value value to write
     * @param consumer consumer to write the element
     * @return this
     * @param <T> type of the element
     */
    @Contract("_, _ -> this")
    public <T> MinecraftBuf writeOptional(final @Nullable T value, final BiConsumer<MinecraftBuf, T> consumer) {
        Preconditions.checkNotNull(consumer);
        writeBoolean(value != null);
        if (value != null) consumer.accept(this, value);
        return this;
    }

    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

    /**
     * Reads var int from the buffer.
     * <p>
     * For more information about var integers see
     * <a href="https://wiki.vg/Protocol#VarInt_and_VarLong">VarInt and VarLong</a>.
     *
     * @return next var int
     */
    public int readVarInt() {
        int value = 0;
        int position = 0;
        byte currentByte;
        while (true) {
            currentByte = readByte();
            value |= (currentByte & SEGMENT_BITS) << position;
            if ((currentByte & CONTINUE_BIT) == 0) break;
            position += 7;
            if (position >= 32) throw new RuntimeException("VarInt is too big");
        }
        return value;
    }

    /**
     * Writes var int to the buffer.
     * <p>
     * For more information about var integers see
     * <a href="https://wiki.vg/Protocol#VarInt_and_VarLong">VarInt and VarLong</a>.
     *
     * @param value value to write
     * @return this
     */
    @Contract("_ -> this")
    public MinecraftBuf writeVarInt(final int value) {
        int i = value;
        while (true) {
            if ((i & ~SEGMENT_BITS) == 0) {
                writeByte((byte) i);
                return this;
            }
            writeByte((byte) ((i & SEGMENT_BITS) | CONTINUE_BIT));
            i >>>= 7;
        }
    }

    /**
     * Reads var long from the buffer.
     * <p>
     * For more information about var longs see
     * <a href="https://wiki.vg/Protocol#VarInt_and_VarLong">VarInt and VarLong</a>.
     *
     * @return next var long
     */
    public long readVarLong() {
        long value = 0;
        int position = 0;
        byte currentByte;
        while (true) {
            currentByte = readByte();
            value |= (long) (currentByte & SEGMENT_BITS) << position;
            if ((currentByte & CONTINUE_BIT) == 0) break;
            position += 7;
            if (position >= 64) throw new RuntimeException("VarLong is too big");
        }
        return value;
    }

    /**
     * Writes var long to the buffer.
     * <p>
     * For more information about var longs see
     * <a href="https://wiki.vg/Protocol#VarInt_and_VarLong">VarInt and VarLong</a>.
     *
     * @param value value to write
     * @return this
     */
    @Contract("_ -> this")
    public MinecraftBuf writeVarLong(final long value) {
        long i = value;
        while (true) {
            if ((i & ~((long) SEGMENT_BITS)) == 0) {
                writeByte((byte) i);
                return this;
            }
            writeByte((byte) ((i & SEGMENT_BITS) | CONTINUE_BIT));
            i >>>= 7;
        }
    }

    /**
     * Reads byte array prefixed with var int from this buffer.
     *
     * @return byte array
     */
    public byte[] readByteArray() {
        return readBytes(new byte[readVarInt()]).array();
    }

    /**
     * Writes byte array prefixed with var int to this buffer.
     *
     * @param bytes byte array to write
     * @return this
     */
    @Contract("_ -> this")
    public MinecraftBuf writeByteArray(final byte[] bytes) {
        Preconditions.checkNotNull(bytes);
        writeVarInt(bytes.length);
        writeBytes(bytes);
        return this;
    }

    /**
     * Reads long array prefixed with var int from this buffer.
     *
     * @return long array
     */
    public long[] readLongArray() {
        final int length = readVarInt();
        final long[] longs = new long[length];
        for (int i = 0; i < length; i++) longs[i] = readLong();
        return longs;
    }

    /**
     * Writes long array prefixed with var int to this buffer.
     *
     * @param longs long array to write
     * @return this
     */
    @Contract("_ -> this")
    public MinecraftBuf writeLongArray(final long[] longs) {
        Preconditions.checkNotNull(longs);
        writeVarInt(longs.length);
        for (final long l : longs) writeLong(l);
        return this;
    }

    /**
     * Reads var int array prefixed with var int from this buffer.
     *
     * @return var int array
     */
    public int[] readVarIntArray() {
        final int length = readVarInt();
        final int[] ints = new int[length];
        for (int i = 0; i < length; i++) ints[i] = readVarInt();
        return ints;
    }

    /**
     * Writes var int array prefixed with var int to this buffer.
     *
     * @param ints int array to write
     * @return this
     */
    @Contract("_ -> this")
    public MinecraftBuf writeVarIntArray(final int[] ints) {
        Preconditions.checkNotNull(ints);
        writeVarInt(ints.length);
        for (final int i : ints) writeVarInt(i);
        return this;
    }

    /**
     * Reads next string from the buffer using provided charset.
     *
     * @param charset charset to use
     * @return next string
     */
    public String readString(final Charset charset) {
        Preconditions.checkNotNull(charset);
        final int length = readVarInt();
        if (length < 0) throw new IllegalStateException("String has illegal length of: " + length);
        final byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++)
            bytes[i] = readByte();
        return new String(bytes, charset);
    }

    /**
     * Writes next string to the buffer.
     *
     * @param value string to write
     * @param charset charset to use
     * @return this
     */
    @Contract("_, _ -> this")
    public MinecraftBuf writeString(final String value, final Charset charset) {
        Preconditions.checkNotNull(value);
        Preconditions.checkNotNull(charset);
        final byte[] bytes = value.getBytes(charset);
        writeVarInt(bytes.length);
        writeBytes(bytes);
        return this;
    }

    /**
     * Reads next UUID from this buffer.
     *
     * @return next uuid
     */
    public UUID readUUID() {
        return new UUID(readLong(), readLong());
    }

    /**
     * Writes UUID to this buffer.
     *
     * @param uuid uuid to write
     * @return this
     */
    @Contract("_ -> this")
    public MinecraftBuf writeUUID(final UUID uuid) {
        Preconditions.checkNotNull(uuid);
        writeLong(uuid.getMostSignificantBits());
        writeLong(uuid.getLeastSignificantBits());
        return this;
    }

    /**
     * Reads next bitset from the buffer.
     *
     * @return next bitset
     */
    public BitSet readBitSet() {
        return BitSet.valueOf(readLongArray());
    }

    /**
     * Reads next bitset from the buffer.
     *
     * @param size fixed size of the bitset
     * @return next bitset
     */
    public BitSet readBitSet(final int size) {
        final byte[] bytes = readBytes(-Math.floorDiv(-size, 8)).array();
        return BitSet.valueOf(bytes);
    }

    /**
     * Writes bitset to this buffer.
     *
     * @param bitSet bitset to write
     * @return this
     */
    @Contract("_ -> this")
    public MinecraftBuf writeBitSet(final BitSet bitSet) {
        Preconditions.checkNotNull(bitSet);
        writeLongArray(bitSet.toLongArray());
        return this;
    }

    /**
     * Writes bitset to this buffer.
     *
     * @param bitSet bitset to write
     * @param size fixed size of the bitset
     * @return this
     */
    @Contract("_, _ -> this")
    public MinecraftBuf writeBitSet(final BitSet bitSet, final int size) {
        Preconditions.checkNotNull(bitSet);
        if (bitSet.length() > size)
            throw new RuntimeException("BitSet is larger than expected size");
        final byte[] bytes = bitSet.toByteArray();
        writeBytes(Arrays.copyOf(bytes, -Math.floorDiv(-size, 8)));
        return this;
    }

    /**
     * Reads next instant from this buffer.
     *
     * @return next instant
     */
    public Instant readInstant() {
        return Instant.ofEpochMilli(readLong());
    }

    /**
     * Writes instant to this buffer.
     *
     * @param instant instant to write
     * @return this
     */
    @Contract("_ -> this")
    public MinecraftBuf writeInstant(final Instant instant) {
        Preconditions.checkNotNull(instant);
        writeLong(instant.toEpochMilli());
        return this;
    }

    /**
     * Reads next enum constant from this buffer.
     *
     * @param enumClass class of the enum
     * @return enum constant
     * @param <T> enum type
     */
    public <T extends Enum<T>> T readEnum(final Class<T> enumClass) {
        Preconditions.checkNotNull(enumClass);
        final T[] constants = enumClass.getEnumConstants();
        final int index = readVarInt();
        if (index >= constants.length)
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for enum " + enumClass.getName());
        return constants[index];
    }

    /**
     * Writes enum constant to this buffer.
     *
     * @param enumConstant enum constant to write
     * @return this
     * @param <T> enum type
     */
    @Contract("_ -> this")
    public <T extends Enum<T>> MinecraftBuf writeEnum(final T enumConstant) {
        Preconditions.checkNotNull(enumConstant);
        return writeVarInt(enumConstant.ordinal());
    }

    private static final long PACKED_X_MASK = 0x3FFFFFF; // max x-coordinate value
    private static final long PACKED_Y_MASK = 0xFFF; // max y-coordinate value
    private static final long PACKED_Z_MASK = 0x3FFFFFF; // max z-coordinate value

    /**
     * Reads next block position from this buffer.
     *
     * @return next block position
     */
    @SuppressWarnings("UnstableApiUsage")
    public BlockPosition readBlockPosition() {
        long packedPos = readLong();
        return Position.block(
                (int) (packedPos >> 38),
                (int) ((packedPos << 52) >> 52),
                (int) ((packedPos << 26) >> 38)
        );
    }

    /**
     * Writes block position to this buffer.
     *
     * @param position position to write
     * @return this
     */
    @Contract("_ -> this")
    @SuppressWarnings("UnstableApiUsage")
    public MinecraftBuf writeBlockPosition(BlockPosition position) {
        writeLong((((long) position.blockX() & PACKED_X_MASK) << 38)
                | (((long) position.blockY() & PACKED_Y_MASK))
                | (((long) position.blockZ() & PACKED_Z_MASK) << 12));
        return this;
    }

}