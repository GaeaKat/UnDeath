package com.nekokittygames.modjam.UnDeath;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class TemTestBook extends Item {

	


    public TemTestBook(int par1)
    {
        super(par1);
        this.setCreativeTab(CreativeTabs.tabMisc);
        setUnlocalizedName("bookSpawner");
		func_111206_d("undeath:DebugItem");
    }

    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (par2World.isRemote)
        {
            return par1ItemStack;
        }
        else
        {
        	RandomEnchantedBook book=new RandomEnchantedBook();
        	book.EnchantedBook(par3EntityPlayer);
        }
        return par1ItemStack;
    }
}
