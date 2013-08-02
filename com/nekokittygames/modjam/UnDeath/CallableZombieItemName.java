package com.nekokittygames.modjam.UnDeath;

import java.util.concurrent.Callable;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class CallableZombieItemName implements Callable {

	final ItemStack theItemStack;

    final InventoryPlayerZombie playerInventory;

    CallableZombieItemName(InventoryPlayerZombie par1InventoryPlayer, ItemStack par2ItemStack)
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
