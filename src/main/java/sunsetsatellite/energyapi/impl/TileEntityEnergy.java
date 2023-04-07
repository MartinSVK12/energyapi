package sunsetsatellite.energyapi.impl;

import net.minecraft.src.Block;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import sunsetsatellite.energyapi.api.IEnergy;
import sunsetsatellite.energyapi.util.*;
import sunsetsatellite.sunsetutils.util.Connection;
import sunsetsatellite.sunsetutils.util.Direction;
import sunsetsatellite.sunsetutils.util.TickTimer;
import sunsetsatellite.sunsetutils.util.Vec3i;

import java.util.HashMap;

public class TileEntityEnergy extends TileEntity implements IEnergy {
    public int energy = 0;
    public int capacity = 0;
    public TileEntityEnergy lastProvided;
    public TileEntityEnergy lastReceived;
    public TickTimer lastTransferMemory;
    public HashMap<Direction, Connection> connections = new HashMap<>();

    public TileEntityEnergy(){
        try {
            this.lastTransferMemory = new TickTimer(this,this.getClass().getMethod("clearLastTransfers"),10,true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        for (Direction dir : Direction.values()) {
            connections.put(dir,Connection.NONE);
        }
    }

    @Override
    public void updateEntity() {
        lastTransferMemory.tick();
    }

    public void clearLastTransfers(){
        lastProvided = null;
        lastReceived = null;
    }

    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public int getEnergy(Direction dir) {
        if(dir.getTileEntity(worldObj,this) instanceof IEnergy){
            return ((IEnergy)dir.getTileEntity(worldObj,this)).getEnergy();
        }
        return 0;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setInteger("energy",energy);
        nbttagcompound.setInteger("capacity",capacity);
        super.writeToNBT(nbttagcompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        energy = nbttagcompound.getInteger("energy");
        capacity = nbttagcompound.getInteger("capacity");
        super.readFromNBT(nbttagcompound);
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getCapacity(Direction dir) {
        if(dir.getTileEntity(worldObj,this) instanceof IEnergy){
            return ((IEnergy)dir.getTileEntity(worldObj,this)).getCapacity();
        }
        return 0;
    }

    @Override
    public void setEnergy(int amount) {
        energy = amount;
        if (this.energy > this.capacity) {
            this.energy = this.capacity;
        } else if (this.energy < 0) {
            this.energy = 0;
        }
    }

    @Override
    public void modifyEnergy(int amount) {
        energy += amount;
        if (this.energy > this.capacity) {
            this.energy = this.capacity;
        } else if (this.energy < 0) {
            this.energy = 0;
        }
    }

    @Override
    public void setCapacity(int amount) {
        capacity = amount;
    }

    @Override
    public void setConnection(Direction dir, Connection connection) {
        connections.replace(dir,connection);
    }

    @Override
    public boolean canConnect(Direction dir, Connection connection) {
        if(connections.get(dir).equals(Connection.BOTH) && !connection.equals(Connection.NONE)){
            return true;
        }
        return connections.get(dir).equals(connection);
    }

    public BlockInstance toInstance(){
        return new BlockInstance(Block.blocksList[worldObj.getBlockId(xCoord,yCoord,zCoord)],new Vec3i(xCoord,yCoord,zCoord),this);
    }
}
