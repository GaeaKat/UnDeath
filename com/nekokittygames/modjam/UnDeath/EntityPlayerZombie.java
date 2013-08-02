/**
 * 
 */
package com.nekokittygames.modjam.UnDeath;

import net.minecraft.client.renderer.ThreadDownloadImageData;
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
	public double field_71091_bM;
    public double field_71096_bN;
    public double field_71097_bO;
    public double field_71094_bP;
    public double field_71095_bQ;
    public double field_71085_bR;
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
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		this.field_71091_bM = this.field_71094_bP;
        this.field_71096_bN = this.field_71095_bQ;
        this.field_71097_bO = this.field_71085_bR;
        double d0 = this.posX - this.field_71094_bP;
        double d1 = this.posY - this.field_71095_bQ;
        double d2 = this.posZ - this.field_71085_bR;
        double d3 = 10.0D;

        if (d0 > d3)
        {
            this.field_71091_bM = this.field_71094_bP = this.posX;
        }

        if (d2 > d3)
        {
            this.field_71097_bO = this.field_71085_bR = this.posZ;
        }

        if (d1 > d3)
        {
            this.field_71096_bN = this.field_71095_bQ = this.posY;
        }

        if (d0 < -d3)
        {
            this.field_71091_bM = this.field_71094_bP = this.posX;
        }

        if (d2 < -d3)
        {
            this.field_71097_bO = this.field_71085_bR = this.posZ;
        }

        if (d1 < -d3)
        {
            this.field_71096_bN = this.field_71095_bQ = this.posY;
        }

        this.field_71094_bP += d0 * 0.25D;
        this.field_71085_bR += d2 * 0.25D;
        this.field_71095_bQ += d1 * 0.25D;
	}
	public int getItemInUseCount()
    {
        return this.itemInUseCount;
    }

	public ResourceLocation func_110306_p() {
		// TODO Auto-generated method stub
		return null;
	}
	public ThreadDownloadImageData func_110309_l()
    {
		//TODO Add in the proepr stuff here to grab skin
        return null;
    }
	public ThreadDownloadImageData func_110310_o()
    {
		//TODO: Add in skin grabbing part here
        return null;
    }
	public ResourceLocation func_110303_q()
    {
		//TODO: More skin grabber
        return null;
    }
}
