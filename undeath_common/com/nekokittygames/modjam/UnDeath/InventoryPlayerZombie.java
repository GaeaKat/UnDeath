/**
 * 
 */
package com.nekokittygames.modjam.UnDeath;

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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Katrina
 * 
 */
public class InventoryPlayerZombie implements IInventory {

    public ItemStack[]        mainInventory  = new ItemStack[36];

    public ItemStack[]        armorInventory = new ItemStack[4];
    public int                currentItem;
    @SideOnly(Side.CLIENT)
    /** The current ItemStack. */
    private ItemStack         currentItemStack;

    /** The player whose inventory this is. */
    public EntityPlayerZombie playerZombie;
    private ItemStack         itemStack;

    public boolean            inventoryChanged;

    public InventoryPlayerZombie(EntityPlayerZombie playerZombie) {
        this.playerZombie = playerZombie;
    }

    public boolean addItemStackToInventory(ItemStack par1ItemStack) {
        if (par1ItemStack == null)
            return false;
        else if (par1ItemStack.stackSize == 0)
            return false;
        else {
            try {
                int i;

                if (par1ItemStack.isItemDamaged()) {
                    i = this.getFirstEmptyStack();

                    if (i >= 0) {
                        mainInventory[i] = ItemStack
                                .copyItemStack(par1ItemStack);
                        mainInventory[i].animationsToGo = 5;
                        par1ItemStack.stackSize = 0;
                        return true;
                    } else
                        return false;
                } else {
                    do {
                        i = par1ItemStack.stackSize;
                        par1ItemStack.stackSize = this
                                .storePartialItemStack(par1ItemStack);
                    } while (par1ItemStack.stackSize > 0
                            && par1ItemStack.stackSize < i);

                    return par1ItemStack.stackSize < i;

                }
            } catch (final Throwable throwable) {
                final CrashReport crashreport = CrashReport.makeCrashReport(
                        throwable, "Adding item to inventory");
                final CrashReportCategory crashreportcategory = crashreport
                        .makeCategory("Item being added");
                crashreportcategory.addCrashSection("Item ID",
                        Integer.valueOf(par1ItemStack.itemID));
                crashreportcategory.addCrashSection("Item data",
                        Integer.valueOf(par1ItemStack.getItemDamage()));
                crashreportcategory.addCrashSectionCallable("Item name",
                        new CallableZombieItemName(this, par1ItemStack));
                throw new ReportedException(crashreport);
            }
        }
    }

    public ItemStack armorItemInSlot(int par1) {
        return armorInventory[par1];
    }

    @SideOnly(Side.CLIENT)
    /**
     * Switch the current item to the next one or the previous one
     */
    public void changeCurrentItem(int par1) {
        if (par1 > 0) {
            par1 = 1;
        }

        if (par1 < 0) {
            par1 = -1;
        }

        for (currentItem -= par1; currentItem < 0; currentItem += 36) {
            ;
        }

        while (currentItem >= 36) {
            currentItem -= 36;
        }
    }

    public int clearInventory(int par1, int par2) {
        int k = 0;
        int l;
        ItemStack itemstack;

        for (l = 0; l < mainInventory.length; ++l) {
            itemstack = mainInventory[l];

            if (itemstack != null && (par1 <= -1 || itemstack.itemID == par1)
                    && (par2 <= -1 || itemstack.getItemDamage() == par2)) {
                k += itemstack.stackSize;
                mainInventory[l] = null;
            }
        }

        for (l = 0; l < armorInventory.length; ++l) {
            itemstack = armorInventory[l];

            if (itemstack != null && (par1 <= -1 || itemstack.itemID == par1)
                    && (par2 <= -1 || itemstack.getItemDamage() == par2)) {
                k += itemstack.stackSize;
                armorInventory[l] = null;
            }
        }

        if (itemStack != null) {
            if (par1 > -1 && itemStack.itemID != par1)
                return k;

            if (par2 > -1 && itemStack.getItemDamage() != par2)
                return k;

            k += itemStack.stackSize;
            this.setItemStack((ItemStack) null);
        }

        return k;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.minecraft.inventory.IInventory#closeChest()
     */
    @Override
    public void closeChest() {
    }

    public boolean consumeInventoryItem(int par1) {
        final int j = this.getInventorySlotContainItem(par1);

        if (j < 0)
            return false;
        else {
            if (--mainInventory[j].stackSize <= 0) {
                mainInventory[j] = null;
            }

            return true;
        }
    }

    public void copyInventory(InventoryPlayer par1InventoryPlayer) {
        int i;

        for (i = 0; i < mainInventory.length; ++i) {
            mainInventory[i] = ItemStack
                    .copyItemStack(par1InventoryPlayer.mainInventory[i]);
        }

        for (i = 0; i < armorInventory.length; ++i) {
            armorInventory[i] = ItemStack
                    .copyItemStack(par1InventoryPlayer.armorInventory[i]);
        }

        currentItem = par1InventoryPlayer.currentItem;
    }

    public void copyInventory(InventoryPlayerZombie par1InventoryPlayer) {
        int i;

        for (i = 0; i < mainInventory.length; ++i) {
            mainInventory[i] = ItemStack
                    .copyItemStack(par1InventoryPlayer.mainInventory[i]);
        }

        for (i = 0; i < armorInventory.length; ++i) {
            armorInventory[i] = ItemStack
                    .copyItemStack(par1InventoryPlayer.armorInventory[i]);
        }

        currentItem = par1InventoryPlayer.currentItem;
    }

    public void damageArmor(float par1) {
        par1 /= 4.0F;

        if (par1 < 1.0F) {
            par1 = 1.0F;
        }

        for (int i = 0; i < armorInventory.length; ++i) {
            if (armorInventory[i] != null
                    && armorInventory[i].getItem() instanceof ItemArmor) {
                armorInventory[i].damageItem((int) par1, playerZombie);

                if (armorInventory[i].stackSize == 0) {
                    armorInventory[i] = null;
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.minecraft.inventory.IInventory#decrStackSize(int, int)
     */
    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        ItemStack[] aitemstack = mainInventory;

        if (par1 >= mainInventory.length) {
            aitemstack = armorInventory;
            par1 -= mainInventory.length;
        }

        if (aitemstack[par1] != null) {
            ItemStack itemstack;

            if (aitemstack[par1].stackSize <= par2) {
                itemstack = aitemstack[par1];
                aitemstack[par1] = null;
                return itemstack;
            } else {
                itemstack = aitemstack[par1].splitStack(par2);

                if (aitemstack[par1].stackSize == 0) {
                    aitemstack[par1] = null;
                }

                return itemstack;
            }
        } else
            return null;
    }

    public void dropAllItems() {
        int i;

        for (i = 0; i < mainInventory.length; ++i) {
            if (mainInventory[i] != null) {
                playerZombie.entityDropItem(mainInventory[i], 0.0f);
                mainInventory[i] = null;
            }
        }

        for (i = 0; i < armorInventory.length; ++i) {
            if (armorInventory[i] != null) {
                playerZombie.entityDropItem(armorInventory[i], 0.0f);
                armorInventory[i] = null;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void func_70439_a(Item par1Item, int par2) {
        if (par1Item != null) {
            if (currentItemStack != null
                    && currentItemStack.isItemEnchantable()
                    && this.getInventorySlotContainItemAndDamage(
                            currentItemStack.itemID,
                            currentItemStack.getItemDamageForDisplay()) == currentItem)
                return;

            final int j = this.getInventorySlotContainItemAndDamage(
                    par1Item.itemID, par2);

            if (j >= 0) {
                final int k = mainInventory[j].stackSize;
                mainInventory[j] = mainInventory[currentItem];
                mainInventory[currentItem] = new ItemStack(
                        Item.itemsList[par1Item.itemID], k, par2);
            } else {
                mainInventory[currentItem] = new ItemStack(
                        Item.itemsList[par1Item.itemID], 1, par2);
            }
        }
    }

    public ItemStack getCurrentItem() {
        return currentItem < 36 && currentItem >= 0 ? mainInventory[currentItem]
                : null;
    }

    /**
     * Returns the first item stack that is empty.
     */
    public int getFirstEmptyStack() {
        for (int i = 0; i < mainInventory.length; ++i) {
            if (mainInventory[i] == null)
                return i;
        }

        return -1;
    }

    private int getInventorySlotContainItem(int par1) {
        for (int j = 0; j < mainInventory.length; ++j) {
            if (mainInventory[j] != null && mainInventory[j].itemID == par1)
                return j;
        }

        return -1;
    }

    @SideOnly(Side.CLIENT)
    private int getInventorySlotContainItemAndDamage(int par1, int par2) {
        for (int k = 0; k < mainInventory.length; ++k) {
            if (mainInventory[k] != null && mainInventory[k].itemID == par1
                    && mainInventory[k].getItemDamage() == par2)
                return k;
        }

        return -1;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be
     * 64, possibly will be extended. *Isn't this more of a set than a get?*
     */
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    /**
     * Returns the name of the inventory.
     */
    @Override
    public String getInvName() {
        return "container.inventory";
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.minecraft.inventory.IInventory#getSizeInventory()
     */
    @Override
    public int getSizeInventory() {
        return mainInventory.length + 4;
    }

    /**
     * Returns the stack in slot i
     */
    @Override
    public ItemStack getStackInSlot(int par1) {
        ItemStack[] aitemstack = mainInventory;

        if (par1 >= aitemstack.length) {
            par1 -= aitemstack.length;
            aitemstack = armorInventory;
        }

        return aitemstack[par1];
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        return null;
    }

    public int getTotalArmorValue() {
        int i = 0;

        for (final ItemStack element : armorInventory) {
            if (element != null && element.getItem() instanceof ItemArmor) {
                final int k = ((ItemArmor) element.getItem()).damageReduceAmount;
                i += k;
            }
        }

        return i;
    }

    public boolean hasItem(int par1) {
        final int j = this.getInventorySlotContainItem(par1);
        return j >= 0;
    }

    public boolean hasItemStack(ItemStack par1ItemStack) {
        int i;

        for (i = 0; i < armorInventory.length; ++i) {
            if (armorInventory[i] != null
                    && armorInventory[i].isItemEqual(par1ItemStack))
                return true;
        }

        for (i = 0; i < mainInventory.length; ++i) {
            if (mainInventory[i] != null
                    && mainInventory[i].isItemEqual(par1ItemStack))
                return true;
        }

        return false;
    }

    /**
     * If this returns false, the inventory name will be used as an unlocalized
     * name, and translated into the player's language. Otherwise it will be
     * used directly.
     */
    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
        return true;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return false;
    }

    @Override
    public void onInventoryChanged() {
        inventoryChanged = true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.minecraft.inventory.IInventory#openChest()
     */
    @Override
    public void openChest() {
    }

    /**
     * Reads from the given tag list and fills the slots in the inventory with
     * the correct items.
     */
    public void readFromNBT(NBTTagList par1NBTTagList) {
        mainInventory = new ItemStack[36];
        armorInventory = new ItemStack[4];

        for (int i = 0; i < par1NBTTagList.tagCount(); ++i) {
            final NBTTagCompound nbttagcompound = (NBTTagCompound) par1NBTTagList
                    .tagAt(i);
            final int j = nbttagcompound.getByte("Slot") & 255;
            final ItemStack itemstack = ItemStack
                    .loadItemStackFromNBT(nbttagcompound);

            if (itemstack != null) {
                if (j >= 0 && j < mainInventory.length) {
                    mainInventory[j] = itemstack;
                }

                if (j >= 100 && j < armorInventory.length + 100) {
                    armorInventory[j - 100] = itemstack;
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    /**
     * Sets a specific itemID as the current item being held (only if it exists on the hotbar)
     */
    public void setCurrentItem(int par1, int par2, boolean par3, boolean par4) {
        currentItemStack = this.getCurrentItem();
        int k;

        if (par3) {
            k = this.getInventorySlotContainItemAndDamage(par1, par2);
        } else {
            k = this.getInventorySlotContainItem(par1);
        }

        if (k >= 0 && k < 36) {
            currentItem = k;
        } else {
            if (par4 && par1 > 0) {
                final int l = this.getFirstEmptyStack();

                if (l >= 0 && l < 36) {
                    currentItem = l;
                }

                this.func_70439_a(Item.itemsList[par1], par2);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.minecraft.inventory.IInventory#setInventorySlotContents(int,
     * net.minecraft.item.ItemStack)
     */
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        ItemStack[] aitemstack = mainInventory;

        if (par1 >= aitemstack.length) {
            par1 -= aitemstack.length;
            aitemstack = armorInventory;
        }

        aitemstack[par1] = par2ItemStack;
    }

    public void setItemStack(ItemStack par1ItemStack) {
        itemStack = par1ItemStack;
    }

    private int storeItemStack(ItemStack par1ItemStack) {
        for (int i = 0; i < mainInventory.length; ++i) {
            if (mainInventory[i] != null
                    && mainInventory[i].itemID == par1ItemStack.itemID
                    && mainInventory[i].isStackable()
                    && mainInventory[i].stackSize < mainInventory[i]
                            .getMaxStackSize()
                    && mainInventory[i].stackSize < this
                            .getInventoryStackLimit()
                    && (!mainInventory[i].getHasSubtypes() || mainInventory[i]
                            .getItemDamage() == par1ItemStack.getItemDamage())
                    && ItemStack.areItemStackTagsEqual(mainInventory[i],
                            par1ItemStack))
                return i;
        }

        return -1;
    }

    private int storePartialItemStack(ItemStack par1ItemStack) {
        final int i = par1ItemStack.itemID;
        int j = par1ItemStack.stackSize;
        int k;

        if (par1ItemStack.getMaxStackSize() == 1) {
            k = this.getFirstEmptyStack();

            if (k < 0)
                return j;
            else {
                if (mainInventory[k] == null) {
                    mainInventory[k] = ItemStack.copyItemStack(par1ItemStack);
                }

                return 0;
            }
        } else {
            k = this.storeItemStack(par1ItemStack);

            if (k < 0) {
                k = this.getFirstEmptyStack();
            }

            if (k < 0)
                return j;
            else {
                if (mainInventory[k] == null) {
                    mainInventory[k] = new ItemStack(i, 0,
                            par1ItemStack.getItemDamage());

                    if (par1ItemStack.hasTagCompound()) {
                        mainInventory[k]
                                .setTagCompound((NBTTagCompound) par1ItemStack
                                        .getTagCompound().copy());
                    }
                }

                int l = j;

                if (j > mainInventory[k].getMaxStackSize()
                        - mainInventory[k].stackSize) {
                    l = mainInventory[k].getMaxStackSize()
                            - mainInventory[k].stackSize;
                }

                if (l > this.getInventoryStackLimit()
                        - mainInventory[k].stackSize) {
                    l = this.getInventoryStackLimit()
                            - mainInventory[k].stackSize;
                }

                if (l == 0)
                    return j;
                else {
                    j -= l;
                    mainInventory[k].stackSize += l;
                    mainInventory[k].animationsToGo = 5;
                    return j;
                }
            }
        }
    }

    public NBTTagList writeToNBT(NBTTagList par1NBTTagList) {
        int i;
        NBTTagCompound nbttagcompound;

        for (i = 0; i < mainInventory.length; ++i) {
            if (mainInventory[i] != null) {
                nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) i);
                mainInventory[i].writeToNBT(nbttagcompound);
                par1NBTTagList.appendTag(nbttagcompound);
            }
        }

        for (i = 0; i < armorInventory.length; ++i) {
            if (armorInventory[i] != null) {
                nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) (i + 100));
                armorInventory[i].writeToNBT(nbttagcompound);
                par1NBTTagList.appendTag(nbttagcompound);
            }
        }

        return par1NBTTagList;
    }

}
