package com.nekokittygames.modjam.UnDeath;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityPlayerSlime extends EntitySlime {
	public static int EntityId;
	public ItemStack[] items=new ItemStack[41];
	public EntityPlayerSlime(World par1World) {
		super(par1World);
	}
	
	@Override
	public void setDead()
    {
        this.isDead = true;
    }
	
	@Override
	protected void setSlimeSize(int par1)
    {
        this.dataWatcher.updateObject(16, new Byte((byte)par1));
        this.setSize(0.6F * (float)par1, 0.6F * (float)par1);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a((double)(par1 * par1));
        //this.setEntityHealth(this.func_110138_aP());
        this.experienceValue = par1;
    }
	
	
	@Override
	protected void dropEquipment(boolean par1, int par2) {
		for(ItemStack item:items)
		{
			this.entityDropItem(item, 0.0f);
		}
	}

	public void playerInit(EntityPlayer player,EntitySlime slime)
	{
		if(slime!=null)
		{
			this.setSlimeSize(slime.getSlimeSize());
			slime.isDead=true;
		}
		
		System.arraycopy(player.inventory.mainInventory, 0, items, 0, player.inventory.mainInventory.length);
		System.arraycopy(player.inventory.armorInventory, 0, items, player.inventory.mainInventory.length, player.inventory.armorInventory.length);
		ItemStack head=new ItemStack(Item.skull, 1, 3);
		NBTTagCompound compound= head.getTagCompound();
		if(compound==null)
			compound=new NBTTagCompound();
		compound.setString("SkullOwner", player.username);
		head.setTagCompound(compound);
		items[40]=head;
		
	}
	
	
}
