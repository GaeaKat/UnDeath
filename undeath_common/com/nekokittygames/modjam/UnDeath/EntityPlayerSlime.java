package com.nekokittygames.modjam.UnDeath;

import java.util.Collection;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityPlayerSlime extends EntitySlime implements
        IEntityAdditionalSpawnData {
    public static int  EntityId;
    public ItemStack[] items = new ItemStack[41];
    private boolean    dropItems;

    public EntityPlayerSlime(World par1World) {
        super(par1World);
        this.setSlimeSize(4);
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    public void copyInventory(InventoryPlayer playerInv) {
        int i;
        for (i = 0; i < playerInv.mainInventory.length; ++i) {
            items[i] = ItemStack.copyItemStack(playerInv.mainInventory[i]);
        }

        for (i = 0; i < playerInv.armorInventory.length; ++i) {
            items[36 + i] = ItemStack
                    .copyItemStack(playerInv.armorInventory[i]);
        }

    }

    public void copyPotionEffects(EntityPlayer player) {
        @SuppressWarnings("unchecked")
        final Collection<PotionEffect> effects = player
                .getActivePotionEffects();
        for (final PotionEffect effect : effects) {
            final PotionEffect toEffect = new PotionEffect(effect);
            this.addPotionEffect(toEffect);
        }

    }

    @Override
    protected void dropEquipment(boolean par1, int par2) {
        if (dropItems) {
            for (final ItemStack item : items) {
                if (item != null) {
                    this.entityDropItem(item, 0.0f);
                }
            }
        } else {
            if (items[40] != null) {
                this.entityDropItem(items[40], 0);
            }
        }
    }

    public void InitFromPlayer(EntityPlayer player, EntitySlime slime) {
        if (slime != null) {
            this.setSlimeSize(slime.getSlimeSize());
            slime.isDead = true;
        }

        copyInventory(player.inventory);
        final ItemStack head = new ItemStack(Item.skull, 1, 3);
        NBTTagCompound compound = head.getTagCompound();
        if (compound == null) {
            compound = new NBTTagCompound();
        }
        compound.setString("SkullOwner", player.username);
        // compound.setString("SkullOwner", "nekosune");
        head.setTagCompound(compound);
        items[40] = head;
        setDropItems();
        copyPotionEffects(player);

    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
        // TODO Auto-generated method stub
        super.readEntityFromNBT(par1nbtTagCompound);
        dropItems = par1nbtTagCompound.getBoolean("dropItem");
        final NBTTagList nbttaglist = par1nbtTagCompound
                .getTagList("Inventory");
        items = new ItemStack[41];
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            final NBTTagCompound nbttagcompound = (NBTTagCompound) nbttaglist
                    .tagAt(i);
            final ItemStack itemstack = ItemStack
                    .loadItemStackFromNBT(nbttagcompound);
            items[i] = itemstack;
        }
    }

    @Override
    public void readSpawnData(ByteArrayDataInput data) {
        NBTTagCompound compound;
        try {
            compound = (NBTTagCompound) NBTBase.readNamedTag(data);
            final NBTTagList nbttaglist = compound.getTagList("Inventory");
            items = new ItemStack[41];
            for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                final NBTTagCompound nbttagcompound = (NBTTagCompound) nbttaglist
                        .tagAt(i);
                final ItemStack itemstack = ItemStack
                        .loadItemStackFromNBT(nbttagcompound);
                items[i] = itemstack;
            }
            dropItems = compound.getBoolean("dropItems");

        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void setDead() {
        isDead = true;
    }

    private void setDropItems() {
        final GameRules gr = worldObj.getGameRules();
        dropItems = !gr.getGameRuleBooleanValue("keepInventory");
    }

    @Override
    protected void setSlimeSize(int par1) {
        dataWatcher.updateObject(16, new Byte((byte) par1));
        this.setSize(0.6F * par1, 0.6F * par1);
        this.setPosition(posX, posY, posZ);
        this.func_110148_a(SharedMonsterAttributes.field_111267_a)
                .func_111128_a(par1 * par1);
        // this.setEntityHealth(this.func_110138_aP());
        experienceValue = par1;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
        // TODO Auto-generated method stub
        super.writeEntityToNBT(par1nbtTagCompound);
        par1nbtTagCompound.setBoolean("dropItems", dropItems);
        final NBTTagList nbtTagList = new NBTTagList();
        for (final ItemStack item : items) {
            if (item != null) {
                final NBTTagCompound comp = new NBTTagCompound();
                item.writeToNBT(comp);
                nbtTagList.appendTag(comp);
            }
        }
        par1nbtTagCompound.setTag("Inventory", nbtTagList);
    }

    @Override
    public void writeSpawnData(ByteArrayDataOutput data) {
        final NBTTagCompound compound = new NBTTagCompound();
        compound.setName("Skellington");
        compound.setBoolean("dropItems", dropItems);
        final NBTTagList nbtTagList = new NBTTagList();
        for (final ItemStack item : items) {
            if (item != null) {
                final NBTTagCompound comp = new NBTTagCompound();
                item.writeToNBT(comp);
                nbtTagList.appendTag(comp);
            }
        }
        compound.setTag("Inventory", nbtTagList);

        try {

            NBTBase.writeNamedTag(compound, data);
        } catch (final Exception ex) {
            ex.printStackTrace();
        }

    }

}
