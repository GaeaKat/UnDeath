package com.nekokittygames.modjam.UnDeath;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemSpawner extends Item {

	public ItemSpawner(int par1) {
		super(par1);
		setMaxStackSize(64);
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName("debugSpawner");
	}

}
