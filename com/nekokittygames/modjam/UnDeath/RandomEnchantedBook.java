package com.nekokittygames.modjam.UnDeath;

import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class RandomEnchantedBook
{
	public static double rand;

	private static Random random = new Random();



	public static void EnchantedBook(EntityPlayer par3EntityPlayer)
	{
                 Enchantment enchantment = Enchantment.enchantmentsBookList[random.nextInt(Enchantment.enchantmentsBookList.length)];
        ItemStack itemstack = new ItemStack(Item.enchantedBook.itemID, 1, 0);
        int l = MathHelper.getRandomIntegerInRange(random, enchantment.getMinLevel(), enchantment.getMaxLevel());
       Item.enchantedBook.getEnchantedItemStack_do(itemstack, new EnchantmentData(enchantment, l));
		par3EntityPlayer.inventory.addItemStackToInventory(itemstack);
	}
}