package com.nekokittygames.modjam.UnDeath;

import java.util.Collection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Multimap;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.nekokittygames.modjam.UnDeath.client.ThreadDownloadZombieImageData;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityPlayerZombiePigmen extends EntityPigZombie implements
        IEntityAdditionalSpawnData {

    public static int                       EntityId;
    public InventoryPigZombie               inventory;
    private final int                       itemInUseCount = 100;                                         // TODO:
                                                                                                           // For
                                                                                                           // now
                                                                                                           // until
                                                                                                           // I
                                                                                                           // can
                                                                                                           // experiment
                                                                                                           // with
                                                                                                           // how
                                                                                                           // to
                                                                                                           // deal
                                                                                                           // withthis
    private String                          PigZombieName  = "";

    public static final ResourceLocation    field_110314_b = new ResourceLocation(
                                                                   "textures/entity/steve.png");
    private static final ResourceLocation   overlay        = new ResourceLocation(
                                                                   "undeath",
                                                                   "textures/entity/playerPigZombie.png");
    protected ThreadDownloadZombieImageData field_110316_a;
    protected ThreadDownloadZombieImageData field_110315_c;
    protected ResourceLocation              mmmm;
    protected ResourceLocation              tsch;
    protected boolean                       dropItems      = true;
    @SideOnly(Side.CLIENT)
    private String                          LayeredName;

    public EntityPlayerZombiePigmen(World par1World) {
        super(par1World);
        inventory = new InventoryPigZombie(this);
        // this.setZombieName("KharonAlpua");

    }

    @SideOnly(Side.CLIENT)
    public void BuildLayeredName() {
        LayeredName = "pzskins/"
                + StringUtils.stripControlCodes(getPigZombieName())
                + "/pigzombie";
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @SuppressWarnings("unchecked")
    public void copyPotionEffects(EntityPlayer player) {
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
            inventory.dropAllItems();
        }
    }

    @SuppressWarnings("unchecked")
    private void findBestEquipment() {

        int bestScore = -1;
        ItemStack bestWeapon = null;
        int bestLocation = 0;
        ItemStack currentCheck;
        int currentScore;
        for (int i = 0; i < inventory.mainInventory.length; i++) {
            currentCheck = inventory.mainInventory[i];
            if (currentCheck == null) {
                continue;
            }
            final Multimap<String, Collection<AttributeModifier>> map = currentCheck
                    .func_111283_C();
            final Collection<Collection<AttributeModifier>> Attributes = map
                    .get(SharedMonsterAttributes.field_111264_e.func_111108_a());

            if (Attributes.size() == 0) {
                currentScore = 0;
            } else {
                currentScore = (int) ((AttributeModifier) Attributes.toArray()[0])
                        .func_111164_d();
            }
            final NBTTagList enchList = currentCheck.getEnchantmentTagList();
            if (enchList == null) {
                currentScore += 0;
            } else {
                for (int j = 0; j < enchList.tagCount(); j++) {
                    final NBTTagCompound comp = (NBTTagCompound) enchList
                            .tagAt(j);
                    final int enchId = comp.getShort("id");
                    final int enchLvl = comp.getShort("lvl");
                    switch (enchId) {
                        case 16:
                            currentScore += 1 * enchLvl;
                            break;
                        case 19:
                            currentScore += 1 * enchLvl;
                            break;
                        case 20:
                            currentScore += 2 * enchLvl;
                            break;
                        default:
                            currentScore += 1;
                    }
                }
            }
            UnDeath.logging.info(String.format("Item %s got score %d",
                    currentCheck.toString(), currentScore));
            if (currentScore > bestScore) {
                bestWeapon = currentCheck;
                bestLocation = i;
                bestScore = currentScore;
            }
        }
        if (bestScore == -1) {
            UnDeath.logging.info("No weapons found");
            inventory.currentItem = -1;
            return;
        }
        UnDeath.logging.info(String.format("Best Weapon is %s with score %d",
                bestWeapon.toString(), bestScore));
        inventory.currentItem = bestLocation;
    }

    @Override
    public EntityLivingData func_110161_a(EntityLivingData par1EntityLivingData) {
        return null;
    }

    @SideOnly(Side.CLIENT)
    public ResourceLocation func_110299_g(String par0Str) {
        return new ResourceLocation("pzcloaks/"
                + StringUtils.stripControlCodes(par0Str));
    }

    @SideOnly(Side.CLIENT)
    public String func_110300_d(String par0Str) {
        return String.format(
                "http://skins.minecraft.net/MinecraftSkins/%s.png",
                new Object[] { StringUtils.stripControlCodes(par0Str) });
    }

    @SideOnly(Side.CLIENT)
    private ThreadDownloadZombieImageData func_110301_a(
            ResourceLocation par0ResourceLocation, String par1Str,
            ResourceLocation par2ResourceLocation, IImageBuffer par3IImageBuffer) {
        final TextureManager texturemanager = Minecraft.getMinecraft()
                .func_110434_K();
        Object object = texturemanager.func_110581_b(par0ResourceLocation);

        if (object == null) {
            object = new ThreadDownloadZombieImageData(par1Str,
                    par2ResourceLocation, par3IImageBuffer);
            texturemanager.func_110579_a(par0ResourceLocation,
                    (TextureObject) object);
        }

        return (ThreadDownloadZombieImageData) object;
    }

    @SideOnly(Side.CLIENT)
    protected void func_110302_j() {
        System.out.println("Setting up custom skins");

        if (this.getPigZombieName() != null
                && !this.getPigZombieName().isEmpty()) {
            mmmm = func_110311_f(this.getPigZombieName());
            tsch = func_110299_g(this.getPigZombieName());
            field_110316_a = func_110304_a(mmmm, this.getPigZombieName());
            field_110315_c = func_110307_b(tsch, this.getPigZombieName());
        }
    }

    @SideOnly(Side.CLIENT)
    public ResourceLocation func_110303_q() {
        return tsch;
    }

    @SideOnly(Side.CLIENT)
    public ThreadDownloadZombieImageData func_110304_a(
            ResourceLocation par0ResourceLocation, String par1Str) {
        return func_110301_a(par0ResourceLocation, func_110300_d(par1Str),
                field_110314_b, new ImageBufferDownload());
    }

    @SideOnly(Side.CLIENT)
    public ResourceLocation func_110305_h(String par0Str) {
        return new ResourceLocation("pzskull/"
                + StringUtils.stripControlCodes(par0Str));
    }

    @SideOnly(Side.CLIENT)
    public ResourceLocation func_110306_p() {
        return mmmm;
    }

    @SideOnly(Side.CLIENT)
    public ThreadDownloadZombieImageData func_110307_b(
            ResourceLocation par0ResourceLocation, String par1Str) {
        return func_110301_a(par0ResourceLocation, func_110308_e(par1Str),
                (ResourceLocation) null, (IImageBuffer) null);
    }

    @SideOnly(Side.CLIENT)
    public String func_110308_e(String par0Str) {
        return String.format(
                "http://skins.minecraft.net/MinecraftCloaks/%s.png",
                new Object[] { StringUtils.stripControlCodes(par0Str) });
    }

    @SideOnly(Side.CLIENT)
    public ThreadDownloadZombieImageData func_110309_l() {
        return field_110316_a;
    }

    @SideOnly(Side.CLIENT)
    public ThreadDownloadZombieImageData func_110310_o() {
        return field_110315_c;
    }

    @SideOnly(Side.CLIENT)
    public ResourceLocation func_110311_f(String par0Str) {
        return new ResourceLocation(getSkinName(par0Str));
    }

    @Override
    public ItemStack func_130225_q(int i) {
        return inventory.armorInventory[i];
    }

    public String getCorruptedName() {
        return PigZombieName.replace("e", "\u00A7ke\u00A7r")
                .replace("a", "\u00A7ka\u00A7r")
                .replace("i", "\u00A7ki\u00A7r")
                .replace("o", "\u00A7ko\u00A7r")
                .replace("u", "\u00A7ku\u00A7r");
    }

    /**
     * 0 = item, 1-n is armor
     */
    @Override
    public ItemStack getCurrentItemOrArmor(int par1) {

        if (par1 == 0)
            if (inventory.currentItem == -1)
                return null;
            else
                return inventory.mainInventory[inventory.currentItem];
        return inventory.armorInventory[par1 - 1];
    }

    @Override
    public ItemStack getHeldItem() {
        if (inventory.currentItem == -1)
            return null;
        return inventory.mainInventory[inventory.currentItem];
    }

    public int getItemInUseCount() {
        return itemInUseCount;
    }

    @Override
    public ItemStack[] getLastActiveItems() {
        if (inventory.currentItem == -1)
            return ArrayUtils.addAll(new ItemStack[] { null },
                    inventory.armorInventory);
        return ArrayUtils
                .addAll(new ItemStack[] { inventory.mainInventory[inventory.currentItem] },
                        inventory.armorInventory);
    }

    @SideOnly(Side.CLIENT)
    public String getLayeredName() {
        if (LayeredName == null) {
            BuildLayeredName();
        }
        return LayeredName;
    }

    public String getPigZombieName() {
        return PigZombieName;
    }

    @SideOnly(Side.CLIENT)
    private String getSkinName(String par0Str) {
        return "pzskins/" + StringUtils.stripControlCodes(par0Str);
    }

    @SideOnly(Side.CLIENT)
    public ResourceLocation[] getSkins() {
        return new ResourceLocation[] { this.func_110306_p(), overlay };
    }

    public void InitFromPlayer(EntityPlayer par7EntityPlayer) {
        this.setPigZombieName(par7EntityPlayer.getCommandSenderName());
        // this.setZombieName("nekosune");
        inventory.copyInventory(par7EntityPlayer.inventory);
        findBestEquipment();
        if (!worldObj.isRemote) {
            setDropItems();
        }
        copyPotionEffects(par7EntityPlayer);
        //
    }

    // Theese zombies can't be converted back
    @Override
    public boolean interact(EntityPlayer par1EntityPlayer) {
        return false;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        final NBTTagList nbttaglist = par1NBTTagCompound
                .getTagList("Inventory");
        inventory.readFromNBT(nbttaglist);
        inventory.currentItem = par1NBTTagCompound
                .getInteger("SelectedItemSlot");
        this.setPigZombieName(par1NBTTagCompound.getString("zombieName"));
        dropItems = par1NBTTagCompound.getBoolean("dropItems");
    }

    @Override
    public void readSpawnData(ByteArrayDataInput data) {
        NBTTagCompound compound;
        try {
            compound = (NBTTagCompound) NBTBase.readNamedTag(data);
            final NBTTagList nbttaglist = compound.getTagList("Inventory");
            inventory.readFromNBT(nbttaglist);
            inventory.currentItem = compound.getInteger("SelectedItemSlot");
            this.setPigZombieName(compound.getString("zombieName"));
            dropItems = compound.getBoolean("dropItems");
            if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
                this.func_110302_j();
            }
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack) {
        if (par1 == 0) {
            inventory.mainInventory[inventory.currentItem] = par2ItemStack;
        } else {
            inventory.armorInventory[par1 - 1] = par2ItemStack;
        }
    }

    private void setDropItems() {
        final GameRules gr = worldObj.getGameRules();
        dropItems = !gr.getGameRuleBooleanValue("keepInventory");
    }

    @SideOnly(Side.CLIENT)
    public void setLayeredName(String layeredName) {
        LayeredName = layeredName;
    }

    public void setPigZombieName(String zombieName) {
        PigZombieName = zombieName;
        this.setCustomNameTag(getCorruptedName());
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setTag("Inventory",
                inventory.writeToNBT(new NBTTagList()));
        par1NBTTagCompound
                .setInteger("SelectedItemSlot", inventory.currentItem);
        par1NBTTagCompound.setString("zombieName", getPigZombieName());
        par1NBTTagCompound.setBoolean("dropItems", dropItems);
    }

    @Override
    public void writeSpawnData(ByteArrayDataOutput data) {
        final NBTTagCompound compound = new NBTTagCompound();
        compound.setName("Zombie");
        compound.setTag("Inventory", inventory.writeToNBT(new NBTTagList()));
        compound.setInteger("SelectedItemSlot", inventory.currentItem);
        compound.setString("zombieName", getPigZombieName());
        compound.setBoolean("dropItems", dropItems);
        try {

            NBTBase.writeNamedTag(compound, data);
        } catch (final Exception ex) {
            ex.printStackTrace();
        }

    }

}
