package sunsetsatellite.energyapi.mp.packets;

import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;
import sunsetsatellite.energyapi.interfaces.mixins.INetClientHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketUpdateEnergy extends Packet {
    public int windowId;
    public int energy;
    public int capacity;
    public int maxReceive;
    public int maxProvide;

    public PacketUpdateEnergy() {
    }

    public PacketUpdateEnergy(int windowId, int energy, int capacity, int maxReceive, int maxProvide) {
        this.windowId = windowId;
        this.energy = energy;
        this.capacity = capacity;
        this.maxProvide = maxProvide;
        this.maxReceive = maxReceive;
    }

    public void processPacket(NetHandler nethandler) {
        ((INetClientHandler)nethandler).handleUpdateEnergy(this);
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException {
        this.windowId = datainputstream.readByte();
        this.energy = datainputstream.readInt();
        this.capacity = datainputstream.readInt();
        this.maxReceive = datainputstream.readInt();
        this.maxProvide = datainputstream.readInt();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeByte(this.windowId);
        dataoutputstream.writeInt(this.energy);
        dataoutputstream.writeInt(this.capacity);
        dataoutputstream.writeInt(this.maxProvide);
        dataoutputstream.writeInt(this.maxReceive);
    }

    public int getPacketSize() {
        return 17;
    }
}
