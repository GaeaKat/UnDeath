/**
 * 
 */
package com.nekokittygames.modjam.UnDeath;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Multimap;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.nekokittygames.modjam.UnDeath.client.ThreadDownloadZombieImageData;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.server.FMLServerHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

/**
 * @author Katrina
 *
 */
public class EntityPlayerZombie extends EntityZombie implements IEntityAdditionalSpawnData {

	public static int EntityId;
	public InventoryPlayerZombie inventory;
	private int itemInUseCount=100; //TODO: For now until I can experiment with how to deal withthis
	private String ZombieName="";

    
    public static final ResourceLocation field_110314_b = new ResourceLocation("textures/entity/steve.png");
    private static final ResourceLocation overlay=new ResourceLocation("undeath","textures/entity/playerZombie.png");
    private ThreadDownloadZombieImageData field_110316_a;
    private ThreadDownloadZombieImageData field_110315_c;
    private ResourceLocation mmmm;
    private ResourceLocation tsch;
    private boolean dropItems=true;
    @SideOnly(Side.CLIENT)
    private String LayeredName;
    @SideOnly(Side.CLIENT)
	public String getLayeredName() {
		if(LayeredName==null)
			BuildLayeredName();
    	return LayeredName;
	}
	@SideOnly(Side.CLIENT)
	public void setLayeredName(String layeredName) {
		LayeredName = layeredName;
	}
	@SideOnly(Side.CLIENT)
	public void BuildLayeredName()
	{
		LayeredName="skins/" + StringUtils.stripControlCodes(getZombieName())+"/zombie";
	}
	@SideOnly(Side.CLIENT)
	public ResourceLocation[] getSkins()
	{
		return new ResourceLocation[] {this.func_110306_p(),overlay};
	}
	public String getZombieName() {
		return ZombieName;
	}

	public void setZombieName(String zombieName) {
		ZombieName = zombieName;
		this.setCustomNameTag(getCorruptedName());
	}

	public String getCorruptedName()
	{
		return ZombieName.replace("e", "\u00A7ke\u00A7r").replace("a", "\u00A7ka\u00A7r").replace("i", "\u00A7ki\u00A7r").replace("o", "\u00A7ko\u00A7r").replace("u", "\u00A7ku\u00A7r");
	}
	public EntityPlayerZombie(World par1World) {
		super(par1World);
		inventory=new InventoryPlayerZombie(this);
		//this.setZombieName("KharonAlpua");
		
		
		
	}
	 @SideOnly(Side.CLIENT)
	protected void func_110302_j()
    {
        System.out.println("Setting up custom skins");

        if (this.getZombieName() != null && !this.getZombieName().isEmpty())
        {
            this.mmmm = func_110311_f(this.getZombieName());
            this.tsch = func_110299_g(this.getZombieName());
            this.field_110316_a = func_110304_a(this.mmmm, this.getZombieName());
            this.field_110315_c = func_110307_b(tsch, this.getZombieName());
        }
    }
	 @SideOnly(Side.CLIENT)
	public ThreadDownloadZombieImageData func_110309_l()
    {
        return this.field_110316_a;
    }
	 @SideOnly(Side.CLIENT)
    public ThreadDownloadZombieImageData func_110310_o()
    {
        return this.field_110315_c;
    }
	 @SideOnly(Side.CLIENT)
    public ResourceLocation func_110306_p()
    {
        return this.mmmm;
    }
	 @SideOnly(Side.CLIENT)
    public ResourceLocation func_110303_q()
    {
        return this.tsch;
    }
	 @SideOnly(Side.CLIENT)
    public static ThreadDownloadZombieImageData func_110304_a(ResourceLocation par0ResourceLocation, String par1Str)
    {
        return func_110301_a(par0ResourceLocation, func_110300_d(par1Str), field_110314_b, new ImageBufferDownload());
    }
	 @SideOnly(Side.CLIENT)
    public static ThreadDownloadZombieImageData func_110307_b(ResourceLocation par0ResourceLocation, String par1Str)
    {
        return func_110301_a(par0ResourceLocation, func_110308_e(par1Str), (ResourceLocation)null, (IImageBuffer)null);
    }
	 @SideOnly(Side.CLIENT)
    private static ThreadDownloadZombieImageData func_110301_a(ResourceLocation par0ResourceLocation, String par1Str, ResourceLocation par2ResourceLocation, IImageBuffer par3IImageBuffer)
    {
        TextureManager texturemanager = Minecraft.getMinecraft().func_110434_K();
        Object object = texturemanager.func_110581_b(par0ResourceLocation);

        if (object == null)
        {
            object = new ThreadDownloadZombieImageData(par1Str, par2ResourceLocation, par3IImageBuffer);
            texturemanager.func_110579_a(par0ResourceLocation, (TextureObject)object);
        }

        return (ThreadDownloadZombieImageData)object;
    }
	 @SideOnly(Side.CLIENT)
    public static String func_110300_d(String par0Str)
    {
        return String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", new Object[] {StringUtils.stripControlCodes(par0Str)});
    }
	 @SideOnly(Side.CLIENT)
    public static String func_110308_e(String par0Str)
    {
        return String.format("http://skins.minecraft.net/MinecraftCloaks/%s.png", new Object[] {StringUtils.stripControlCodes(par0Str)});
    }
	 @SideOnly(Side.CLIENT)
    public static ResourceLocation func_110311_f(String par0Str)
    {
        return new ResourceLocation(getSkinName(par0Str));
    }
	 @SideOnly(Side.CLIENT)
	private static String getSkinName(String par0Str) {
		return "zskins/" + StringUtils.stripControlCodes(par0Str);
	}
	 @SideOnly(Side.CLIENT)
    public static ResourceLocation func_110299_g(String par0Str)
    {
        return new ResourceLocation("zcloaks/" + StringUtils.stripControlCodes(par0Str));
    }
	 @SideOnly(Side.CLIENT)
    public static ResourceLocation func_110305_h(String par0Str)
    {
        return new ResourceLocation("zskull/" + StringUtils.stripControlCodes(par0Str));
    }
	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Inventory");
        this.inventory.readFromNBT(nbttaglist);
        this.inventory.currentItem = par1NBTTagCompound.getInteger("SelectedItemSlot");
        this.setZombieName(par1NBTTagCompound.getString("zombieName"));
        this.dropItems=par1NBTTagCompound.getBoolean("dropItems");
    }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
        par1NBTTagCompound.setInteger("SelectedItemSlot", this.inventory.currentItem);
        par1NBTTagCompound.setString("zombieName", getZombieName());
        par1NBTTagCompound.setBoolean("dropItems", dropItems);
    }
	

	public int getItemInUseCount()
    {
        return this.itemInUseCount;
    }
	
	public void InitFromPlayer(EntityPlayer par7EntityPlayer) {
		this.setZombieName(par7EntityPlayer.getCommandSenderName());
		//this.setZombieName("nekosune");
		this.inventory.copyInventory(par7EntityPlayer.inventory);
		findBestEquipment();
		Side side1=FMLCommonHandler.instance().getEffectiveSide();
		Side side2=FMLCommonHandler.instance().getSide();
		if(!this.worldObj.isRemote)
		{
			setDropItems();
		}
		copyPotionEffects(par7EntityPlayer);
		//
	}
	public void copyPotionEffects(EntityPlayer player)
	{
		Collection<PotionEffect> effects=player.getActivePotionEffects();
		for(PotionEffect effect:effects)
		{
			PotionEffect toEffect=new PotionEffect(effect);
			this.addPotionEffect(toEffect);
		}
		
	}
	private void setDropItems() {
		GameRules gr=this.worldObj.getGameRules();
		dropItems=!gr.getGameRuleBooleanValue("keepInventory");
	}
	private void findBestEquipment() {
		
		int bestScore=-1;
		ItemStack bestWeapon=null;
		int bestLocation=0;
		ItemStack currentCheck;
		int currentScore;
		for(int i=0;i<this.inventory.mainInventory.length;i++)
		{
			currentCheck=this.inventory.mainInventory[i];
			if(currentCheck==null)
				continue;
			Multimap map=currentCheck.func_111283_C();
			Collection Attributes=(Collection)map.get(SharedMonsterAttributes.field_111264_e.func_111108_a());
			
			if(Attributes.size()==0)
				currentScore=0;
			else
				currentScore=(int)((AttributeModifier)Attributes.toArray()[0]).func_111164_d();
			NBTTagList enchList=currentCheck.getEnchantmentTagList();
			if(enchList==null)
				currentScore+=0;
			else
			{
				for(int j=0;j<enchList.tagCount();j++)
				{
					NBTTagCompound comp=(NBTTagCompound)enchList.tagAt(j);
					int enchId=comp.getShort("id");
					int enchLvl=comp.getShort("lvl");
					switch(enchId)
					{
					case 16:
						currentScore+=(1*enchLvl);
						break;
					case 19:
						currentScore+=(1*enchLvl);
						break;
					case 20:
						currentScore+=(2*enchLvl);
						break;
					default:
						currentScore+=1;
					}
				}
			}
			UnDeath.logging.info(String.format("Item %s got score %d", currentCheck.toString(),currentScore));
			if(currentScore>bestScore)
			{
				bestWeapon=currentCheck;
				bestLocation=i;
				bestScore=currentScore;
			}
		}
		if(bestScore==-1)
		{
			UnDeath.logging.info("No weapons found");
			this.inventory.currentItem=-1;
			return;
		}
		UnDeath.logging.info(String.format("Best Weapon is %s with score %d", bestWeapon.toString(),bestScore));
		this.inventory.currentItem=bestLocation;
	}
	
	public EntityLivingData func_110161_a(EntityLivingData par1EntityLivingData)
	{
		return null;
	}
	
	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		NBTTagCompound compound=new NBTTagCompound();
		compound.setName("Zombie");
		compound.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
		compound.setInteger("SelectedItemSlot", this.inventory.currentItem);
		compound.setString("zombieName", getZombieName());
		compound.setBoolean("dropItems", dropItems);
		try {
			
	        NBTBase.writeNamedTag(compound, data);
		} catch (Exception ex) {
	        ex.printStackTrace();
		}
		
	}
	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		NBTTagCompound compound;
		try {
			compound = (NBTTagCompound) NBTBase.readNamedTag(data);
			NBTTagList nbttaglist = compound.getTagList("Inventory");
	        this.inventory.readFromNBT(nbttaglist);
	        this.inventory.currentItem = compound.getInteger("SelectedItemSlot");
	        this.setZombieName(compound.getString("zombieName"));
	        dropItems=compound.getBoolean("dropItems");
	        if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT)
			{
				this.func_110302_j();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	// Theese zombies can't be converted back
	@Override
	public boolean interact(EntityPlayer par1EntityPlayer)
	{
		return false;
	}
	
	/**
     * 0 = item, 1-n is armor
     */
    public ItemStack getCurrentItemOrArmor(int par1)
    {
    	
        if(par1==0)
        	if(this.inventory.currentItem==-1)
        		return null;
        	else
        		return this.inventory.mainInventory[this.inventory.currentItem];
        return this.inventory.armorInventory[par1-1];
    }
    
    public ItemStack func_130225_q(int i)
    {
        return this.inventory.armorInventory[i];
    }
    
    public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack)
    {
    	if(par1==0)
        	this.inventory.mainInventory[this.inventory.currentItem]=par2ItemStack;
    	else
    		this.inventory.armorInventory[par1-1]=par2ItemStack;
    }

    public ItemStack[] getLastActiveItems()
    {
    	if(this.inventory.currentItem==-1)
    		return ArrayUtils.addAll(new ItemStack[] { null},this.inventory.armorInventory);
        return ArrayUtils.addAll(new ItemStack[] { this.inventory.mainInventory[this.inventory.currentItem]},this.inventory.armorInventory);
    }
    
    @Override
    protected void dropEquipment(boolean par1, int par2)
    {
    	if(dropItems)
    		this.inventory.dropAllItems();
    }
    
    public ItemStack getHeldItem()
    {
    	if(this.inventory.currentItem==-1)
    		return null;
        return this.inventory.mainInventory[this.inventory.currentItem];
    }
}
