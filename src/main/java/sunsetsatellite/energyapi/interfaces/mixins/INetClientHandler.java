package sunsetsatellite.energyapi.interfaces.mixins;


import sunsetsatellite.energyapi.mp.packets.PacketUpdateEnergy;

public interface INetClientHandler {

    void handleUpdateEnergy(PacketUpdateEnergy packetUpdateEnergy);
}
