package ace.actually.reforested.bees.blocks;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record ProgressData(NbtCompound label) {
    public static final PacketCodec<RegistryByteBuf, ProgressData> PACKET_CODEC = PacketCodec.tuple(PacketCodecs.NBT_COMPOUND,
            ProgressData::label,
            ProgressData::new);
}
