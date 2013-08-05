package com.nekokittygames.modjam.UnDeath;

import java.util.concurrent.Callable;

import net.minecraft.item.ItemStack;

public class CallablePigZombieItemName implements Callable {
	final ItemStack theItemStack;

    final InventoryPigZombie playerInventory;

    CallablePigZombieItemName(InventoryPigZombie par1InventoryPlayer, ItemStack par2ItemStack)
    {
        this.playerInventory = par1InventoryPlayer;
        this.theItemStack = par2ItemStack;
    }

    public String callItemDisplayName()
    {
        return this.theItemStack.getDisplayName();
    }

    public Object call()
    {
        return this.callItemDisplayName();
    }

}
