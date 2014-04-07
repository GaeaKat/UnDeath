package com.nekokittygames.modjam.UnDeath;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Collection;

public class EntityPlayerSlime extends EntitySlime implements IEntityAdditionalSpawnData {
	public static int EntityId;
	public ItemStack[] items=new ItemStack[41];
	private boolean dropItems;
	public EntityPlayerSlime(World par1World) {
		super(par1World);
		this.setSlimeSize(4);
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
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((double) (par1 * par1));
        this.setHealth(this.getMaxHealth());
        this.experienceValue = par1;
    }
	
	
	@Override
	protected void dropEquipment(boolean par1, int par2) {
		if(dropItems)
		{
			for(ItemStack item:items)
			{
				if(item!=null)
				this.entityDropItem(item, 0.0f);
			}
		}
		else
		{
			this.entityDropItem(items[40], 0);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
		// TODO Auto-generated method stub
		super.writeEntityToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setBoolean("dropItems", dropItems);
		NBTTagList nbtTagList=new NBTTagList();
		for(int i=0;i<items.length;i++)
		{
			if(items[i]!=null)
			{
				NBTTagCompound comp=new NBTTagCompound();
				items[i].writeToNBT(comp);
				nbtTagList.appendTag(comp);
			}
		}
		par1nbtTagCompound.setTag("Inventory", nbtTagList);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
		// TODO Auto-generated method stub
		super.readEntityFromNBT(par1nbtTagCompound);
		this.dropItems=par1nbtTagCompound.getBoolean("dropItem");
		NBTTagList nbttaglist = par1nbtTagCompound.getTagList("Inventory", Constants.NBT.TAG_COMPOUND);
		items=new ItemStack[41];
		for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.getCompoundTagAt(i);
            ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);
            items[i]=itemstack;
        }
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	public void InitFromPlayer(EntityPlayer player,EntitySlime slime)
	{
		if(slime!=null)
		{
			this.setSlimeSize(slime.getSlimeSize());
			slime.isDead=true;
		}
		
		copyInventory(player.inventory);
		ItemStack head=new ItemStack(Items.skull, 1, 3);
		NBTTagCompound compound= head.getTagCompound();
		if(compound==null)
			compound=new NBTTagCompound();
		compound.setString("SkullOwner", player.getGameProfile().getName());
		//compound.setString("SkullOwner", "nekosune");
		head.setTagCompound(compound);
		items[40]=head;
		setDropItems();
		copyPotionEffects(player);
		
	}
	private void setDropItems() {
		GameRules gr=this.worldObj.getGameRules();
		dropItems=!gr.getGameRuleBooleanValue("keepInventory");
	}
	public void copyPotionEffects(EntityPlayer player)
	{
		Collection<PotionEffect> effects=player.getActivePotionEffects();
		for(PotionEffect effect:effects)
		{
			PotionEffect toEffect=new PotionEffect(effect);
			this.addPotionEffect(toEffect);
		}
		
	}
	public void copyInventory(InventoryPlayer playerInv)
	{
		int i;
		for (i = 0; i < playerInv.mainInventory.length; ++i)
        {
            items[i] = ItemStack.copyItemStack(playerInv.mainInventory[i]);
        }

        for (i = 0; i < playerInv.armorInventory.length; ++i)
        {
            items[36+i] = ItemStack.copyItemStack(playerInv.armorInventory[i]);
        }

	}
	@Override
	public void writeSpawnData(ByteBuf data) {
		NBTTagCompound compound=new NBTTagCompound();
		//compound.setName("Skellington");
		compound.setBoolean("dropItems", dropItems);
		NBTTagList nbtTagList=new NBTTagList();
		for(int i=0;i<items.length;i++)
		{
			if(items[i]!=null)
			{
				NBTTagCompound comp=new NBTTagCompound();
				items[i].writeToNBT(comp);
				nbtTagList.appendTag(comp);
			}
		}
		compound.setTag("Inventory", nbtTagList);
		
		try {

            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            CompressedStreamTools.writeCompressed(compound, bos);
            data.setBytes(0, bos.toByteArray());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	@Override
	public void readSpawnData(ByteBuf data) {
		NBTTagCompound compound;
		try {
            compound = (NBTTagCompound) CompressedStreamTools.readCompressed(new ByteArrayInputStream(data.array()));
			NBTTagList nbttaglist = compound.getTagList("Inventory",Constants.NBT.TAG_COMPOUND);
			items=new ItemStack[41];
			for (int i = 0; i < nbttaglist.tagCount(); ++i)
	        {
				NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.getCompoundTagAt(i);
	            ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);
	            items[i]=itemstack;
	        }
			this.dropItems=compound.getBoolean("dropItems");
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
