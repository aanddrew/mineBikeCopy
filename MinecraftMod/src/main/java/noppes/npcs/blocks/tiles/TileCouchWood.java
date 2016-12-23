package noppes.npcs.blocks.tiles;

import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.blocks.tiles.TileColorable;

public class TileCouchWood extends TileColorable {

   public boolean hasLeft = false;
   public boolean hasRight = false;


   public void readFromNBT(NBTTagCompound compound) {
      super.readFromNBT(compound);
      this.hasLeft = compound.getBoolean("CouchLeft");
      this.hasRight = compound.getBoolean("CouchRight");
   }

   public void writeToNBT(NBTTagCompound compound) {
      super.writeToNBT(compound);
      compound.setBoolean("CouchLeft", this.hasLeft);
      compound.setBoolean("CouchRight", this.hasRight);
   }
}
