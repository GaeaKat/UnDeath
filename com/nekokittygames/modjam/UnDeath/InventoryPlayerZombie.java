/**
 * 
 */
package com.nekokittygames.modjam.UnDeath;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @author Katrina
 *
 */
public class InventoryPlayerZombie implements IInventory {

	
	 public ItemStack[] mainInventory = new ItemStack[36];
	 
	 public ItemStack[] armorInventory = new ItemStack[4];
	 public int currentItem;
	    @SideOnly(Side.CLIENT)

	    /** The current ItemStack. */
	    private ItemStack currentItemStack;

	    /** The player whose inventory this is. */
	    public EntityPlayerZombie playerZombie;
	    private ItemStack itemStack;
	    
	    
	    public boolean inventoryChanged;
	    
	    
	    public InventoryPlayerZombie(EntityPlayerZombie playerZombie)
	    {
	    	this.playerZombie=playerZombie;
	    }
	    
	    public ItemStack getCurrentItem()
	    {
	        return this.currentItem < 36 && this.currentItem >= 0 ? this.mainInventory[this.currentItem] : null;
	    }
	    
	    private int getInventorySlotContainItem(int par1)
	    {
	        for (int j = 0; j < this.mainInventory.length; ++j)
	        {
	            if (this.mainInventory[j] != null && this.mainInventory[j].itemID == par1)
	            {
	                return j;
	            }
	        }

	        return -1;
	    }
	    
	    
	    
	    
	    @SideOnly(Side.CLIENT)
	    private int getInventorySlotContainItemAndDamage(int par1, int par2)
	    {
	        for (int k = 0; k < this.mainInventory.length; ++k)
	        {
	            if (this.mainInventory[k] != null && this.mainInventory[k].itemID == par1 && this.mainInventory[k].getItemDamage() == par2)
	            {
	                return k;
	            }
	        }

	        return -1;
	    }
	    
	    
	    
	    private int storeItemStack(ItemStack par1ItemStack)
	    {
	        for (int i = 0; i < this.mainInventory.length; ++i)
	        {
	            if (this.mainInventory[i] != null && this.mainInventory[i].itemID == par1ItemStack.itemID && this.mainInventory[i].isStackable() && this.mainInventory[i].stackSize < this.mainInventory[i].getMaxStackSize() && this.mainInventory[i].stackSize < this.getInventoryStackLimit() && (!this.mainInventory[i].getHasSubtypes() || this.mainInventory[i].getItemDamage() == par1ItemStack.getItemDamage()) && ItemStack.areItemStackTagsEqual(this.mainInventory[i], par1ItemStack))
	            {
	                return i;
	            }
	        }

	        return -1;
	    }
	    
	    /**
	     * Returns the first item stack that is empty.
	     */
	    public int getFirstEmptyStack()
	    {
	        for (int i = 0; i < this.mainInventory.length; ++i)
	        {
	            if (this.mainInventory[i] == null)
	            {
	                return i;
	            }
	        }

	        return -1;
	    }
	    
	    @SideOnly(Side.CLIENT)

	    /**
	     * Sets a specific itemID as the current item being held (only if it exists on the hotbar)
	     */
	    public void setCurrentItem(int par1, int par2, boolean par3, boolean par4)
	    {
	        boolean flag2 = true;
	        this.currentItemStack = this.getCurrentItem();
	        int k;

	        if (par3)
	        {
	            k = this.getInventorySlotContainItemAndDamage(par1, par2);
	        }
	        else
	        {
	            k = this.getInventorySlotContainItem(par1);
	        }

	        if (k >= 0 && k < 36)
	        {
	            this.currentItem = k;
	        }
	        else
	        {
	            if (par4 && par1 > 0)
	            {
	                int l = this.getFirstEmptyStack();

	                if (l >= 0 && l < 36)
	                {
	                    this.currentItem = l;
	                }

	                this.func_70439_a(Item.itemsList[par1], par2);
	            }
	        }
	    }
	    
	    @SideOnly(Side.CLIENT)

	    /**
	     * Switch the current item to the next one or the previous one
	     */
	    public void changeCurrentItem(int par1)
	    {
	        if (par1 > 0)
	        {
	            par1 = 1;
	        }

	        if (par1 < 0)
	        {
	            par1 = -1;
	        }

	        for (this.currentItem -= par1; this.currentItem < 0; this.currentItem += 36)
	        {
	            ;
	        }

	        while (this.currentItem >= 36)
	        {
	            this.currentItem -= 36;
	        }
	    }
	    
	    public int clearInventory(int par1, int par2)
	    {
	        int k = 0;
	        int l;
	        ItemStack itemstack;

	        for (l = 0; l < this.mainInventory.length; ++l)
	        {
	            itemstack = this.mainInventory[l];

	            if (itemstack != null && (par1 <= -1 || itemstack.itemID == par1) && (par2 <= -1 || itemstack.getItemDamage() == par2))
	            {
	                k += itemstack.stackSize;
	                this.mainInventory[l] = null;
	            }
	        }

	        for (l = 0; l < this.armorInventory.length; ++l)
	        {
	            itemstack = this.armorInventory[l];

	            if (itemstack != null && (par1 <= -1 || itemstack.itemID == par1) && (par2 <= -1 || itemstack.getItemDamage() == par2))
	            {
	                k += itemstack.stackSize;
	                this.armorInventory[l] = null;
	            }
	        }

	        if (this.itemStack != null)
	        {
	            if (par1 > -1 && this.itemStack.itemID != par1)
	            {
	                return k;
	            }

	            if (par2 > -1 && this.itemStack.getItemDamage() != par2)
	            {
	                return k;
	            }

	            k += this.itemStack.stackSize;
	            this.setItemStack((ItemStack)null);
	        }

	        return k;
	    }
	    
	    @SideOnly(Side.CLIENT)
	    public void func_70439_a(Item par1Item, int par2)
	    {
	        if (par1Item != null)
	        {
	            if (this.currentItemStack != null && this.currentItemStack.isItemEnchantable() && this.getInventorySlotContainItemAndDamage(this.currentItemStack.itemID, this.currentItemStack.getItemDamageForDisplay()) == this.currentItem)
	            {
	                return;
	            }

	            int j = this.getInventorySlotContainItemAndDamage(par1Item.itemID, par2);

	            if (j >= 0)
	            {
	                int k = this.mainInventory[j].stackSize;
	                this.mainInventory[j] = this.mainInventory[this.currentItem];
	                this.mainInventory[this.currentItem] = new ItemStack(Item.itemsList[par1Item.itemID], k, par2);
	            }
	            else
	            {
	                this.mainInventory[this.currentItem] = new ItemStack(Item.itemsList[par1Item.itemID], 1, par2);
	            }
	        }
	    }
	/* (non-Javadoc)
	 * @see net.minecraft.inventory.IInventory#getSizeInventory()
	 */
	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.inventory.IInventory#getStackInSlot(int)
	 */
	@Override
	public ItemStack getStackInSlot(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.inventory.IInventory#decrStackSize(int, int)
	 */
	@Override
	public ItemStack decrStackSize(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.inventory.IInventory#getStackInSlotOnClosing(int)
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.inventory.IInventory#setInventorySlotContents(int, net.minecraft.item.ItemStack)
	 */
	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.minecraft.inventory.IInventory#getInvName()
	 */
	@Override
	public String getInvName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.inventory.IInventory#isInvNameLocalized()
	 */
	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.inventory.IInventory#getInventoryStackLimit()
	 */
	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.inventory.IInventory#onInventoryChanged()
	 */
	@Override
	public void onInventoryChanged() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.minecraft.inventory.IInventory#isUseableByPlayer(net.minecraft.entity.player.EntityPlayer)
	 */
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.inventory.IInventory#openChest()
	 */
	@Override
	public void openChest() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.minecraft.inventory.IInventory#closeChest()
	 */
	@Override
	public void closeChest() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.minecraft.inventory.IInventory#isItemValidForSlot(int, net.minecraft.item.ItemStack)
	 */
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return false;
	}

}
