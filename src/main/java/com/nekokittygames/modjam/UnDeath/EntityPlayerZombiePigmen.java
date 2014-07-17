package com.nekokittygames.modjam.UnDeath;

import com.google.common.collect.Multimap;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Collection;

public class EntityPlayerZombiePigmen extends EntityPigZombie implements IEntityAdditionalSpawnData{

	public static int EntityId;
	public InventoryPigZombie inventory;
	private int itemInUseCount=100; //TODO: For now until I can experiment with how to deal withthis
	private String PigZombieName="";

    
    public static final ResourceLocation field_110314_b = new ResourceLocation("textures/entity/steve.png");
    public static final ResourceLocation overlay=new ResourceLocation("undeath","textures/entity/playerPigZombie.png");
    protected ThreadDownloadImageData field_110316_a;
    protected ThreadDownloadImageData field_110315_c;
    protected ResourceLocation mmmm;
    protected ResourceLocation tsch;
    protected boolean dropItems=true;
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
		LayeredName="pzskins/" + StringUtils.stripControlCodes(getPigZombieName())+"/pigzombie";
	}
	@SideOnly(Side.CLIENT)
	public ResourceLocation[] getSkins()
	{
		return new ResourceLocation[] {this.func_110306_p(),overlay};
	}
	public String getPigZombieName() {
		return PigZombieName;
	}

	public void setPigZombieName(String zombieName) {
		PigZombieName = zombieName;
		this.setCustomNameTag(getCorruptedName());
	}

	public String getCorruptedName()
	{
		return PigZombieName.replace("e", "\u00A7ke\u00A7r").replace("a", "\u00A7ka\u00A7r").replace("i", "\u00A7ki\u00A7r").replace("o", "\u00A7ko\u00A7r").replace("u", "\u00A7ku\u00A7r");
	}
	public EntityPlayerZombiePigmen(World par1World) {
		super(par1World);
		inventory=new InventoryPigZombie(this);
		//this.setZombieName("KharonAlpua");
		
		
		
	}
	 @SideOnly(Side.CLIENT)
	protected void func_110302_j()
    {
        System.out.println("Setting up custom skins");

        if (this.getPigZombieName() != null && !this.getPigZombieName().isEmpty())
        {
            this.mmmm = func_110311_f(this.getPigZombieName());
            this.tsch = func_110299_g(this.getPigZombieName());
            this.field_110316_a = func_110304_a(this.mmmm, this.getPigZombieName());
            this.field_110315_c = func_110307_b(tsch, this.getPigZombieName());
        }
    }
	 @SideOnly(Side.CLIENT)
	public ThreadDownloadImageData func_110309_l()
    {
        return this.field_110316_a;
    }
	 @SideOnly(Side.CLIENT)
    public ThreadDownloadImageData func_110310_o()
    {
        return this.field_110315_c;
    }
	 @SideOnly(Side.CLIENT)
    public ResourceLocation func_110306_p()
    {
        return mmmm;
    }
	 @SideOnly(Side.CLIENT)
    public ResourceLocation func_110303_q()
    {
        return tsch;
    }
	 @SideOnly(Side.CLIENT)
    public ThreadDownloadImageData func_110304_a(ResourceLocation par0ResourceLocation, String par1Str)
    {
        return func_110301_a(par0ResourceLocation, func_110300_d(par1Str), field_110314_b, new ImageBufferDownload());
    }
	 @SideOnly(Side.CLIENT)
    public  ThreadDownloadImageData func_110307_b(ResourceLocation par0ResourceLocation, String par1Str)
    {
        return func_110301_a(par0ResourceLocation, func_110308_e(par1Str), (ResourceLocation)null, (IImageBuffer)null);
    }
	 @SideOnly(Side.CLIENT)
    private ThreadDownloadImageData func_110301_a(ResourceLocation par0ResourceLocation, String par1Str, ResourceLocation par2ResourceLocation, IImageBuffer par3IImageBuffer)
    {
        TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        Object object = texturemanager.getTexture(par0ResourceLocation);

        if (object == null)
        {
            object = new ThreadDownloadImageData(null,par1Str, par2ResourceLocation, par3IImageBuffer);
            texturemanager.loadTexture(par0ResourceLocation, (ITextureObject) object);
        }

        return (ThreadDownloadImageData)object;
    }
	 @SideOnly(Side.CLIENT)
    public String func_110300_d(String par0Str)
    {
        return String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", new Object[] {StringUtils.stripControlCodes(par0Str)});
    }
	 @SideOnly(Side.CLIENT)
    public  String func_110308_e(String par0Str)
    {
        return String.format("http://skins.minecraft.net/MinecraftCloaks/%s.png", new Object[] {StringUtils.stripControlCodes(par0Str)});
    }
	 @SideOnly(Side.CLIENT)
    public  ResourceLocation func_110311_f(String par0Str)
    {
        return new ResourceLocation(getSkinName(par0Str));
    }
	 @SideOnly(Side.CLIENT)
	private  String getSkinName(String par0Str) {
		return "pzskins/" + StringUtils.stripControlCodes(par0Str);
	}
	 @SideOnly(Side.CLIENT)
    public  ResourceLocation func_110299_g(String par0Str)
    {
        return new ResourceLocation("pzcloaks/" + StringUtils.stripControlCodes(par0Str));
    }
	 @SideOnly(Side.CLIENT)
    public  ResourceLocation func_110305_h(String par0Str)
    {
        return new ResourceLocation("pzskull/" + StringUtils.stripControlCodes(par0Str));
    }
	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Inventory", Constants.NBT.TAG_COMPOUND);
        this.inventory.readFromNBT(nbttaglist);
        this.inventory.currentItem = par1NBTTagCompound.getInteger("SelectedItemSlot");
        this.setPigZombieName(par1NBTTagCompound.getString("zombieName"));
        this.dropItems=par1NBTTagCompound.getBoolean("dropItems");
    }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
        par1NBTTagCompound.setInteger("SelectedItemSlot", this.inventory.currentItem);
        par1NBTTagCompound.setString("zombieName", getPigZombieName());
        par1NBTTagCompound.setBoolean("dropItems", dropItems);
    }
	

	public int getItemInUseCount()
    {
        return this.itemInUseCount;
    }
	
	public void InitFromPlayer(EntityPlayer par7EntityPlayer) {
		this.setPigZombieName(par7EntityPlayer.getCommandSenderName());
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
			Multimap map=currentCheck.getAttributeModifiers();
			Collection Attributes=(Collection)map.get(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
			
			if(Attributes.size()==0)
				currentScore=0;
			else
				currentScore=(int)((AttributeModifier)Attributes.toArray()[0]).getAmount();
			NBTTagList enchList=currentCheck.getEnchantmentTagList();
			if(enchList==null)
				currentScore+=0;
			else
			{
				for(int j=0;j<enchList.tagCount();j++)
				{
					NBTTagCompound comp=(NBTTagCompound)enchList.getCompoundTagAt(j);
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

	
	@Override
	public void writeSpawnData(ByteBuf data) {
		NBTTagCompound compound=new NBTTagCompound();
		//compound.setName("Zombie");
		compound.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
		compound.setInteger("SelectedItemSlot", this.inventory.currentItem);
		compound.setString("zombieName", getPigZombieName());
		compound.setBoolean("dropItems", dropItems);
		try {

            ByteBufUtils.writeTag(data, compound);
		} catch (Exception ex) {
	        ex.printStackTrace();
		}
		
	}
	@Override
	public void readSpawnData(ByteBuf data) {
		NBTTagCompound compound;
		try {
            compound=ByteBufUtils.readTag(data);
            NBTTagList nbttaglist = compound.getTagList("Inventory",Constants.NBT.TAG_COMPOUND);
	        this.inventory.readFromNBT(nbttaglist);
	        this.inventory.currentItem = compound.getInteger("SelectedItemSlot");
	        this.setPigZombieName(compound.getString("zombieName"));
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
	@Override
	protected boolean canDespawn() {
		return false;
	}

}
