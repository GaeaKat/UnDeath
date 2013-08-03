package com.nekokittygames.modjam.UnDeath;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.nekokittygames.modjam.UnDeath.client.ThreadDownloadZombieImageData;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class EntityPlayerSkellington extends EntityMob implements IEntityAdditionalSpawnData,IRangedAttackMob {

	
	private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
    private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false);
	
	public InventoryPlayerSkellington inventory;
	private int itemInUseCount=100; //TODO: For now until I can experiment with how to deal withthis
	private String SkellingtonName="";

    
    public static final ResourceLocation field_110314_b = new ResourceLocation("textures/entity/steve.png");
    private static final ResourceLocation overlay=new ResourceLocation("undeath","textures/entity/playerSkellington.png");
    private ThreadDownloadZombieImageData field_110316_a;
    private ThreadDownloadZombieImageData field_110315_c;
    private ResourceLocation mmmm;
    private ResourceLocation tsch;
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
		LayeredName="skins/" + StringUtils.stripControlCodes(getSkellingtonName())+"/skellington";
	}
	@SideOnly(Side.CLIENT)
	public ResourceLocation[] getSkins()
	{
		return new ResourceLocation[] {this.func_110306_p(),overlay};
	}
	public String getSkellingtonName() {
		return SkellingtonName;
	}

	public void setSkellingtonName(String zombieName) {
		SkellingtonName = zombieName;
		this.setCustomNameTag(getCorruptedName());
	}

	public String getCorruptedName()
	{
		return SkellingtonName.replace("e", "§ke§r").replace("a", "§ka§r").replace("i", "§ki§r").replace("o", "§ko§r").replace("u", "§ku§r");
	}
	public EntityPlayerSkellington(World par1World) {
		super(par1World);
		inventory=new InventoryPlayerSkellington(this);
		this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIRestrictSun(this));
        this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));

        if (par1World != null && !par1World.isRemote)
        {
            this.setCombatTask();
        }
	}
	@SideOnly(Side.CLIENT)
	protected void func_110302_j()
    {
        System.out.println("Setting up custom skins");

        if (this.getSkellingtonName() != null && !this.getSkellingtonName().isEmpty())
        {
            this.mmmm = func_110311_f(this.getSkellingtonName());
            this.tsch = func_110299_g(this.getSkellingtonName());
            this.field_110316_a = func_110304_a(this.mmmm, this.getSkellingtonName());
            this.field_110315_c = func_110307_b(tsch, this.getSkellingtonName());
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
		return "sskins/" + StringUtils.stripControlCodes(par0Str);
	}
	 @SideOnly(Side.CLIENT)
    public static ResourceLocation func_110299_g(String par0Str)
    {
        return new ResourceLocation("scloaks/" + StringUtils.stripControlCodes(par0Str));
    }
	 @SideOnly(Side.CLIENT)
    public static ResourceLocation func_110305_h(String par0Str)
    {
        return new ResourceLocation("sskull/" + StringUtils.stripControlCodes(par0Str));
    }
	 
	 
	 @Override
		public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	    {
	        super.readEntityFromNBT(par1NBTTagCompound);
	        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Inventory");
	        this.inventory.readFromNBT(nbttaglist);
	        this.inventory.currentItem = par1NBTTagCompound.getInteger("SelectedItemSlot");
	        this.setSkellingtonName(par1NBTTagCompound.getString("skellingtonName"));
	    }
		
		@Override
		public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	    {
	        super.writeEntityToNBT(par1NBTTagCompound);
	        par1NBTTagCompound.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
	        par1NBTTagCompound.setInteger("SelectedItemSlot", this.inventory.currentItem);
	        par1NBTTagCompound.setString("skellingtonName", getSkellingtonName());
	    }
		
		public int getItemInUseCount()
	    {
	        return this.itemInUseCount;
	    }
		public void InitFromPlayer(EntityPlayer par7EntityPlayer) {
			this.setSkellingtonName(par7EntityPlayer.getCommandSenderName());
			//this.setSkellingtonName("nekosune");
			this.inventory.copyInventory(par7EntityPlayer.inventory);
			this.inventory.currentItem=1;
			//TODO: The skellington version of this!
			//findBestEquipment();
			//
		}
		
		
		protected void entityInit()
	    {
	        super.entityInit();
	        this.dataWatcher.addObject(13, new Byte((byte)0));
	    }
		public boolean isAIEnabled()
	    {
	        return true;
	    }
		public EntityLivingData func_110161_a(EntityLivingData par1EntityLivingData)
		{
			return null;
		}
		
		@Override
		public void writeSpawnData(ByteArrayDataOutput data) {
			NBTTagCompound compound=new NBTTagCompound();
			compound.setName("Skellington");
			compound.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
			compound.setInteger("SelectedItemSlot", this.inventory.currentItem);
			compound.setString("skellingtonName", getSkellingtonName());
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
		        this.setSkellingtonName(compound.getString("skellingtonName"));
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
	    	this.inventory.dropAllItems();
	    }
	    
	    public ItemStack getHeldItem()
	    {
	    	if(this.inventory.currentItem==-1)
	    		return null;
	        return this.inventory.mainInventory[this.inventory.currentItem];
	    }
	    
	    public void setCombatTask()
	    {
	    	this.tasks.addTask(4, this.aiArrowAttack);
	    }
		@Override
		public void attackEntityWithRangedAttack(
				EntityLivingBase entitylivingbase, float f) {
			// TODO Auto-generated method stub
			
		}

}
