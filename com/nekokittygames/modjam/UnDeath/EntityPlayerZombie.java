/**
 * 
 */
package com.nekokittygames.modjam.UnDeath;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * @author Katrina
 *
 */
public class EntityPlayerZombie extends EntityZombie {

	
	public InventoryPlayerZombie inventory;
	private int itemInUseCount=100; //TODO: For now until I can experiment with how to deal withthis
	private String ZombieName="";
	
	public String getZombieName() {
		return ZombieName;
	}

	public void setZombieName(String zombieName) {
		ZombieName = zombieName;
	}

	public EntityPlayerZombie(World par1World) {
		super(par1World);
		inventory=new InventoryPlayerZombie(this);
		
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Inventory");
        this.inventory.readFromNBT(nbttaglist);
        this.inventory.currentItem = par1NBTTagCompound.getInteger("SelectedItemSlot");
    }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
        par1NBTTagCompound.setInteger("SelectedItemSlot", this.inventory.currentItem);
    }
	
	public int getItemInUseCount()
    {
        return this.itemInUseCount;
    }

	public ResourceLocation func_110306_p() {
		// TODO Auto-generated method stub
		return null;
	}
}
