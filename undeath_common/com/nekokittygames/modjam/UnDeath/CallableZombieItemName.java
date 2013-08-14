package com.nekokittygames.modjam.UnDeath;

import java.util.concurrent.Callable;

import net.minecraft.item.ItemStack;

public class CallableZombieItemName implements Callable<Object> {

    final ItemStack             theItemStack;

    final InventoryPlayerZombie playerInventory;

    CallableZombieItemName(InventoryPlayerZombie par1InventoryPlayer,
            ItemStack par2ItemStack) {
        playerInventory = par1InventoryPlayer;
        theItemStack = par2ItemStack;
    }

    @Override
    public Object call() {
        return this.callItemDisplayName();
    }

    public String callItemDisplayName() {
        return theItemStack.getDisplayName();
    }
}
