package com.nekokittygames.modjam.UnDeath;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ReportedException;

public class InventoryPlayerSkellington implements IInventory {

	public ItemStack[] mainInventory = new ItemStack[36];
	 
	 public ItemStack[] armorInventory = new ItemStack[4];
	 public int currentItem;
	    @SideOnly(Side.CLIENT)

	    /** The current ItemStack. */
	    private ItemStack currentItemStack;

	    /** The player whose inventory this is. */
	    public EntityPlayerSkellington playerSkellington;

	    private ItemStack itemStack;
	    
	    
	    public boolean inventoryChanged;
	    
	    
	    public InventoryPlayerSkellington(EntityPlayerSkellington playerSkellington)
	    {
	    	this.playerSkellington=playerSkellington;
	    }
	    
	    public ItemStack getCurrentItem()
	    {
	        return this.currentItem < 36 && this.currentItem >= 0 ? this.mainInventory[this.currentItem] : null;
	    }
	    
	    private int getInventorySlotContainItem(Item par1)
	    {
	        for (int j = 0; j < this.mainInventory.length; ++j)
	        {
	            if (this.mainInventory[j] != null && this.mainInventory[j].getItem() == par1)
	            {
	                return j;
	            }
	        }

	        return -1;
	    }
	    
	    
	    
	    
	    @SideOnly(Side.CLIENT)
	    private int getInventorySlotContainItemAndDamage(Item par1, int par2)
	    {
	        for (int k = 0; k < this.mainInventory.length; ++k)
	        {
	            if (this.mainInventory[k] != null && this.mainInventory[k].getItem() == par1 && this.mainInventory[k].getItemDamage() == par2)
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
	            if (this.mainInventory[i] != null && this.mainInventory[i].getItem() == par1ItemStack.getItem() && this.mainInventory[i].isStackable() && this.mainInventory[i].stackSize < this.mainInventory[i].getMaxStackSize() && this.mainInventory[i].stackSize < this.getInventoryStackLimit() && (!this.mainInventory[i].getHasSubtypes() || this.mainInventory[i].getItemDamage() == par1ItemStack.getItemDamage()) && ItemStack.areItemStackTagsEqual(this.mainInventory[i], par1ItemStack))
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
	    public void setCurrentItem(Item par1, int par2, boolean par3, boolean par4)
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
	            if (par4 && par1 !=null)
	            {
	                int l = this.getFirstEmptyStack();

	                if (l >= 0 && l < 36)
	                {
	                    this.currentItem = l;
	                }

                    this.func_70439_a(par1, par2);
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
	    
	    public int clearInventory(Item par1, int par2)
	    {
	        int k = 0;
	        int l;
	        ItemStack itemstack;

	        for (l = 0; l < this.mainInventory.length; ++l)
	        {
	            itemstack = this.mainInventory[l];

	            if (itemstack != null && (par1 ==null || itemstack.getItem() == par1) && (par2 <= -1 || itemstack.getItemDamage() == par2))
	            {
	                k += itemstack.stackSize;
	                this.mainInventory[l] = null;
	            }
	        }

	        for (l = 0; l < this.armorInventory.length; ++l)
	        {
	            itemstack = this.armorInventory[l];

	            if (itemstack != null && (par1 ==null || itemstack.getItem() == par1) && (par2 <= -1 || itemstack.getItemDamage() == par2))
	            {
	                k += itemstack.stackSize;
	                this.armorInventory[l] = null;
	            }
	        }

	        if (this.itemStack != null)
	        {
	            if (par1 !=null && this.itemStack.getItem() != par1)
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
	            if (this.currentItemStack != null && this.currentItemStack.isItemEnchantable() && this.getInventorySlotContainItemAndDamage(this.currentItemStack.getItem(), this.currentItemStack.getItemDamageForDisplay()) == this.currentItem)
	            {
	                return;
	            }

	            int j = this.getInventorySlotContainItemAndDamage(par1Item, par2);

	            if (j >= 0)
	            {
	                int k = this.mainInventory[j].stackSize;
	                this.mainInventory[j] = this.mainInventory[this.currentItem];
	                this.mainInventory[this.currentItem] = new ItemStack(par1Item, k, par2);
	            }
	            else
	            {
	                this.mainInventory[this.currentItem] = new ItemStack(par1Item, 1, par2);
	            }
	        }
	    }
	    
	    private int storePartialItemStack(ItemStack par1ItemStack)
	    {
	        int j = par1ItemStack.stackSize;
	        int k;

	        if (par1ItemStack.getMaxStackSize() == 1)
	        {
	            k = this.getFirstEmptyStack();

	            if (k < 0)
	            {
	                return j;
	            }
	            else
	            {
	                if (this.mainInventory[k] == null)
	                {
	                    this.mainInventory[k] = ItemStack.copyItemStack(par1ItemStack);
	                }

	                return 0;
	            }
	        }
	        else
	        {
	            k = this.storeItemStack(par1ItemStack);

	            if (k < 0)
	            {
	                k = this.getFirstEmptyStack();
	            }

	            if (k < 0)
	            {
	                return j;
	            }
	            else
	            {
	                if (this.mainInventory[k] == null)
	                {
	                    this.mainInventory[k] = new ItemStack(par1ItemStack.getItem(), 0, par1ItemStack.getItemDamage());

	                    if (par1ItemStack.hasTagCompound())
	                    {
	                        this.mainInventory[k].setTagCompound((NBTTagCompound)par1ItemStack.getTagCompound().copy());
	                    }
	                }

	                int l = j;

	                if (j > this.mainInventory[k].getMaxStackSize() - this.mainInventory[k].stackSize)
	                {
	                    l = this.mainInventory[k].getMaxStackSize() - this.mainInventory[k].stackSize;
	                }

	                if (l > this.getInventoryStackLimit() - this.mainInventory[k].stackSize)
	                {
	                    l = this.getInventoryStackLimit() - this.mainInventory[k].stackSize;
	                }

	                if (l == 0)
	                {
	                    return j;
	                }
	                else
	                {
	                    j -= l;
	                    this.mainInventory[k].stackSize += l;
	                    this.mainInventory[k].animationsToGo = 5;
	                    return j;
	                }
	            }
	        }
	    }
	    
	    
	    public boolean consumeInventoryItem(Item par1)
	    {
	        int j = this.getInventorySlotContainItem(par1);

	        if (j < 0)
	        {
	            return false;
	        }
	        else
	        {
	            if (--this.mainInventory[j].stackSize <= 0)
	            {
	                this.mainInventory[j] = null;
	            }

	            return true;
	        }
	    }
	    
	    public boolean hasItem(Item par1)
	    {
	        int j = this.getInventorySlotContainItem(par1);
	        return j >= 0;
	    }
	    
	    
	    public boolean addItemStackToInventory(ItemStack par1ItemStack)
	    {
	        if (par1ItemStack == null)
	        {
	            return false;
	        }
	        else if (par1ItemStack.stackSize == 0)
	        {
	            return false;
	        }
	        else
	        {
	            try
	            {
	                int i;

	                if (par1ItemStack.isItemDamaged())
	                {
	                    i = this.getFirstEmptyStack();

	                    if (i >= 0)
	                    {
	                        this.mainInventory[i] = ItemStack.copyItemStack(par1ItemStack);
	                        this.mainInventory[i].animationsToGo = 5;
	                        par1ItemStack.stackSize = 0;
	                        return true;
	                    }
	                    else
	                    {
	                        return false;
	                    }
	                }
	                else
	                {
	                    do
	                    {
	                        i = par1ItemStack.stackSize;
	                        par1ItemStack.stackSize = this.storePartialItemStack(par1ItemStack);
	                    }
	                    while (par1ItemStack.stackSize > 0 && par1ItemStack.stackSize < i);


	                    return par1ItemStack.stackSize < i;

	                }
	            }
	            catch (Throwable throwable)
	            {
	                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Adding item to inventory");
	                CrashReportCategory crashreportcategory = crashreport.makeCategory("Item being added");
	                crashreportcategory.addCrashSection("Item ID", par1ItemStack.getItem());
	                crashreportcategory.addCrashSection("Item data", Integer.valueOf(par1ItemStack.getItemDamage()));
	                crashreportcategory.addCrashSectionCallable("Item name", new CallableSkellingtonItemName(this, par1ItemStack));
	                throw new ReportedException(crashreport);
	            }
	        }
	    }
	    
		/* (non-Javadoc)
		 * @see net.minecraft.inventory.IInventory#decrStackSize(int, int)
		 */
	    @Override
	    public ItemStack decrStackSize(int par1, int par2)
	    {
	        ItemStack[] aitemstack = this.mainInventory;

	        if (par1 >= this.mainInventory.length)
	        {
	            aitemstack = this.armorInventory;
	            par1 -= this.mainInventory.length;
	        }

	        if (aitemstack[par1] != null)
	        {
	            ItemStack itemstack;

	            if (aitemstack[par1].stackSize <= par2)
	            {
	                itemstack = aitemstack[par1];
	                aitemstack[par1] = null;
	                return itemstack;
	            }
	            else
	            {
	                itemstack = aitemstack[par1].splitStack(par2);

	                if (aitemstack[par1].stackSize == 0)
	                {
	                    aitemstack[par1] = null;
	                }

	                return itemstack;
	            }
	        }
	        else
	        {
	            return null;
	        }
	    }
	    
	    /* (non-Javadoc)
		 * @see net.minecraft.inventory.IInventory#setInventorySlotContents(int, net.minecraft.item.ItemStack)
		 */
	    @Override
	    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	    {
	        ItemStack[] aitemstack = this.mainInventory;

	        if (par1 >= aitemstack.length)
	        {
	            par1 -= aitemstack.length;
	            aitemstack = this.armorInventory;
	        }

	        aitemstack[par1] = par2ItemStack;
	    }

    @Override
    public String getInventoryName() {
        return "container.inventory";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    public NBTTagList writeToNBT(NBTTagList par1NBTTagList)
	    {
	        int i;
	        NBTTagCompound nbttagcompound;

	        for (i = 0; i < this.mainInventory.length; ++i)
	        {
	            if (this.mainInventory[i] != null)
	            {
	                nbttagcompound = new NBTTagCompound();
	                nbttagcompound.setByte("Slot", (byte)i);
	                this.mainInventory[i].writeToNBT(nbttagcompound);
	                par1NBTTagList.appendTag(nbttagcompound);
	            }
	        }

	        for (i = 0; i < this.armorInventory.length; ++i)
	        {
	            if (this.armorInventory[i] != null)
	            {
	                nbttagcompound = new NBTTagCompound();
	                nbttagcompound.setByte("Slot", (byte)(i + 100));
	                this.armorInventory[i].writeToNBT(nbttagcompound);
	                par1NBTTagList.appendTag(nbttagcompound);
	            }
	        }

	        return par1NBTTagList;
	    }

	    /**
	     * Reads from the given tag list and fills the slots in the inventory with the correct items.
	     */
	    public void readFromNBT(NBTTagList par1NBTTagList)
	    {
	        this.mainInventory = new ItemStack[36];
	        this.armorInventory = new ItemStack[4];

	        for (int i = 0; i < par1NBTTagList.tagCount(); ++i)
	        {
	            NBTTagCompound nbttagcompound = (NBTTagCompound)par1NBTTagList.getCompoundTagAt(i);
	            int j = nbttagcompound.getByte("Slot") & 255;
	            ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);

	            if (itemstack != null)
	            {
	                if (j >= 0 && j < this.mainInventory.length)
	                {
	                    this.mainInventory[j] = itemstack;
	                }

	                if (j >= 100 && j < this.armorInventory.length + 100)
	                {
	                    this.armorInventory[j - 100] = itemstack;
	                }
	            }
	        }
	    }
	    /* (non-Javadoc)
		 * @see net.minecraft.inventory.IInventory#getSizeInventory()
		 */
	    @Override
	    public int getSizeInventory()
	    {
	        return this.mainInventory.length + 4;
	    }
	    
	    /**
	     * Returns the stack in slot i
	     */
	    @Override
	    public ItemStack getStackInSlot(int par1)
	    {
	        ItemStack[] aitemstack = this.mainInventory;

	        if (par1 >= aitemstack.length)
	        {
	            par1 -= aitemstack.length;
	            aitemstack = this.armorInventory;
	        }

	        return aitemstack[par1];
	    }

	    /**
	     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
	     * this more of a set than a get?*
	     */
	    @Override
	    public int getInventoryStackLimit()
	    {
	        return 64;
	    }

    @Override
    public void markDirty() {
            this.inventoryChanged = true;

    }

    public ItemStack armorItemInSlot(int par1)
	    {
	        return this.armorInventory[par1];
	    }
	    
	    
	    public int getTotalArmorValue()
	    {
	        int i = 0;

	        for (int j = 0; j < this.armorInventory.length; ++j)
	        {
	            if (this.armorInventory[j] != null && this.armorInventory[j].getItem() instanceof ItemArmor)
	            {
	                int k = ((ItemArmor)this.armorInventory[j].getItem()).damageReduceAmount;
	                i += k;
	            }
	        }

	        return i;
	    }
	    
	    public void damageArmor(float par1)
	    {
	        par1 /= 4.0F;

	        if (par1 < 1.0F)
	        {
	            par1 = 1.0F;
	        }

	        for (int i = 0; i < this.armorInventory.length; ++i)
	        {
	            if (this.armorInventory[i] != null && this.armorInventory[i].getItem() instanceof ItemArmor)
	            {
	                this.armorInventory[i].damageItem((int)par1, this.playerSkellington);

	                if (this.armorInventory[i].stackSize == 0)
	                {
	                    this.armorInventory[i] = null;
	                }
	            }
	        }
	    }
	    
	    public void dropAllItems()
	    {
	        int i;

	        for (i = 0; i < this.mainInventory.length; ++i)
	        {
	            if (this.mainInventory[i] != null)
	            {
	                this.playerSkellington.entityDropItem(this.mainInventory[i], 0.0f);
	                this.mainInventory[i] = null;
	            }
	        }

	        for (i = 0; i < this.armorInventory.length; ++i)
	        {
	            if (this.armorInventory[i] != null)
	            {
	                this.playerSkellington.entityDropItem(this.armorInventory[i], 0.0f);
	                this.armorInventory[i] = null;
	            }
	        }
	    }
	    

	    public void setItemStack(ItemStack par1ItemStack)
	    {
	        this.itemStack = par1ItemStack;
	    }

	    public ItemStack getItemStack()
	    {
	        return this.itemStack;
	    }
	    @Override
	    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
			return false;
		}

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    public boolean hasItemStack(ItemStack par1ItemStack)
	    {
	        int i;

	        for (i = 0; i < this.armorInventory.length; ++i)
	        {
	            if (this.armorInventory[i] != null && this.armorInventory[i].isItemEqual(par1ItemStack))
	            {
	                return true;
	            }
	        }

	        for (i = 0; i < this.mainInventory.length; ++i)
	        {
	            if (this.mainInventory[i] != null && this.mainInventory[i].isItemEqual(par1ItemStack))
	            {
	                return true;
	            }
	        }

	        return false;
	    }


	    @Override
	    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
	    {
	        return true;
	    }

	    public void copyInventory(InventoryPlayer par1InventoryPlayer)
	    {
	        int i;

	        for (i = 0; i < this.mainInventory.length; ++i)
	        {
	            this.mainInventory[i] = ItemStack.copyItemStack(par1InventoryPlayer.mainInventory[i]);
	        }

	        for (i = 0; i < this.armorInventory.length; ++i)
	        {
	            this.armorInventory[i] = ItemStack.copyItemStack(par1InventoryPlayer.armorInventory[i]);
	        }

	        this.currentItem = par1InventoryPlayer.currentItem;
	    }
	    
	    public void copyInventory(InventoryPlayerZombie par1InventoryPlayer)
	    {
	        int i;

	        for (i = 0; i < this.mainInventory.length; ++i)
	        {
	            this.mainInventory[i] = ItemStack.copyItemStack(par1InventoryPlayer.mainInventory[i]);
	        }

	        for (i = 0; i < this.armorInventory.length; ++i)
	        {
	            this.armorInventory[i] = ItemStack.copyItemStack(par1InventoryPlayer.armorInventory[i]);
	        }

	        this.currentItem = par1InventoryPlayer.currentItem;
	    }






	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}


}
