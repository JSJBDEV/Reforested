package ace.actually.reforested.bees.blocks;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

/**
 * Sometimes you want stuff to happen on the client for block entities, one way to do it is like this, apparently.
 * @param label an NbtCompound to store data in I guess
 */
public record ProgressData(NbtCompound label) {
    public static final PacketCodec<RegistryByteBuf, ProgressData> PACKET_CODEC = PacketCodec.tuple(PacketCodecs.NBT_COMPOUND,
            ProgressData::label,
            ProgressData::new);
}
