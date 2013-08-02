package com.nekokittygames.modjam.UnDeath.client;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED;
import static net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D;

import org.lwjgl.opengl.GL11;

import com.nekokittygames.modjam.UnDeath.EntityPlayerZombie;

import net.minecraft.block.Block;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;

public class RenderPlayerZombie extends RenderBiped {

	 private static final ResourceLocation field_110826_a = new ResourceLocation("textures/entity/steve.png");
	    private ModelBiped modelBipedMain;
	    private ModelBiped modelArmorChestplate;
	    private ModelBiped modelArmor;

	public RenderPlayerZombie() {
		super(new ModelBiped(0.0F), 0.5F);
        this.modelBipedMain = (ModelBiped)this.mainModel;
        this.modelArmorChestplate = new ModelBiped(1.0F);
        this.modelArmor = new ModelBiped(0.5F);
	}
	protected int setArmorModel(EntityPlayerZombie par1EtityPlayerZombie, int par2, float par3)
    {
        ItemStack itemstack = par1EtityPlayerZombie.inventory.armorItemInSlot(3 - par2);
        
        if (itemstack != null)
        {
            Item item = itemstack.getItem();

            if (item instanceof ItemArmor)
            {
                ItemArmor itemarmor = (ItemArmor)item;
                this.func_110776_a(RenderBiped.getArmorResource(par1EtityPlayerZombie, itemstack, par2, null));
                ModelBiped modelbiped = par2 == 2 ? this.modelArmor : this.modelArmorChestplate;
                modelbiped.bipedHead.showModel = par2 == 0;
                modelbiped.bipedHeadwear.showModel = par2 == 0;
                modelbiped.bipedBody.showModel = par2 == 1 || par2 == 2;
                modelbiped.bipedRightArm.showModel = par2 == 1;
                modelbiped.bipedLeftArm.showModel = par2 == 1;
                modelbiped.bipedRightLeg.showModel = par2 == 2 || par2 == 3;
                modelbiped.bipedLeftLeg.showModel = par2 == 2 || par2 == 3;
                modelbiped = ForgeHooksClient.getArmorModel(par1EtityPlayerZombie, itemstack, par2, modelbiped);
                this.setRenderPassModel(modelbiped);
                modelbiped.onGround = this.mainModel.onGround;
                modelbiped.isRiding = this.mainModel.isRiding;
                modelbiped.isChild = this.mainModel.isChild;
                float f1 = 1.0F;

                //Move outside if to allow for more then just CLOTH
                int j = itemarmor.getColor(itemstack);
                if (j != -1)
                {
                    float f2 = (float)(j >> 16 & 255) / 255.0F;
                    float f3 = (float)(j >> 8 & 255) / 255.0F;
                    float f4 = (float)(j & 255) / 255.0F;
                    GL11.glColor3f(f1 * f2, f1 * f3, f1 * f4);

                    if (itemstack.isItemEnchanted())
                    {
                        return 31;
                    }

                    return 16;
                }

                GL11.glColor3f(f1, f1, f1);

                if (itemstack.isItemEnchanted())
                {
                    return 15;
                }

                return 1;
            }
        }

        return -1;
    }
	
	protected void func_130220_b(EntityPlayerZombie par1EtityPlayerZombie, int par2, float par3)
    {
        ItemStack itemstack = par1EtityPlayerZombie.inventory.armorItemInSlot(3 - par2);

        if (itemstack != null)
        {
            Item item = itemstack.getItem();

            if (item instanceof ItemArmor)
            {
                this.func_110776_a(RenderBiped.getArmorResource(par1EtityPlayerZombie, itemstack, par2, "overlay"));
                float f1 = 1.0F;
                GL11.glColor3f(f1, f1, f1);
            }
        }
    }
	
	public void func_130009_a(EntityPlayerZombie par1EntityPlayerZombie, double par2, double par4, double par6, float par8, float par9)
    {
        float f2 = 1.0F;
        GL11.glColor3f(f2, f2, f2);
        ItemStack itemstack = par1EntityPlayerZombie.inventory.getCurrentItem();
        this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = itemstack != null ? 1 : 0;

        if (itemstack != null && par1EntityPlayerZombie.getItemInUseCount() > 0)
        {
            EnumAction enumaction = itemstack.getItemUseAction();

            if (enumaction == EnumAction.block)
            {
                this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = 3;
            }
            else if (enumaction == EnumAction.bow)
            {
                this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = true;
            }
        }

        this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = par1EntityPlayerZombie.isSneaking();
        double d3 = par4 - (double)par1EntityPlayerZombie.yOffset;

        if (par1EntityPlayerZombie.isSneaking())
        {
            d3 -= 0.125D;
        }

        super.func_130000_a(par1EntityPlayerZombie, par2, d3, par6, par8, par9);
        this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = false;
        this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = false;
        this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = 0;
    }
	
	protected void renderSpecials(EntityPlayerZombie par1EntityPlayerZombie, float par2)
    {
        float f1 = 1.0F;
        GL11.glColor3f(f1, f1, f1);
        super.renderEquippedItems(par1EntityPlayerZombie, par2);
        super.renderArrowsStuckInEntity(par1EntityPlayerZombie, par2);
        ItemStack itemstack = par1EntityPlayerZombie.inventory.armorItemInSlot(3);

        if (itemstack != null)
        {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedHead.postRender(0.0625F);
            float f2;

            if (itemstack != null && itemstack.getItem() instanceof ItemBlock)
            {
                IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, EQUIPPED);
                boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(EQUIPPED, itemstack, BLOCK_3D));

                if (is3D || RenderBlocks.renderItemIn3d(Block.blocksList[itemstack.itemID].getRenderType()))
                {
                    f2 = 0.625F;
                    GL11.glTranslatef(0.0F, -0.25F, 0.0F);
                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glScalef(f2, -f2, -f2);
                }

                this.renderManager.itemRenderer.renderItem(par1EntityPlayerZombie, itemstack, 0);
            }
            else if (itemstack.getItem().itemID == Item.skull.itemID)
            {
                f2 = 1.0625F;
                GL11.glScalef(f2, -f2, -f2);
                String s = "";

                if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("SkullOwner"))
                {
                    s = itemstack.getTagCompound().getString("SkullOwner");
                }

                TileEntitySkullRenderer.skullRenderer.func_82393_a(-0.5F, 0.0F, -0.5F, 1, 180.0F, itemstack.getItemDamage(), s);
            }

            GL11.glPopMatrix();
        }

        if (par1EntityPlayerZombie.getZombieName().equals("deadmau5") && par1EntityPlayerZombie.func_110309_l().func_110557_a())
        {
            this.func_110776_a(par1EntityPlayerZombie.func_110306_p());

            for (int i = 0; i < 2; ++i)
            {
                float f3 = par1EntityPlayerZombie.prevRotationYaw + (par1EntityPlayerZombie.rotationYaw - par1EntityPlayerZombie.prevRotationYaw) * par2 - (par1EntityPlayerZombie.prevRenderYawOffset + (par1EntityPlayerZombie.renderYawOffset - par1EntityPlayerZombie.prevRenderYawOffset) * par2);
                float f4 = par1EntityPlayerZombie.prevRotationPitch + (par1EntityPlayerZombie.rotationPitch - par1EntityPlayerZombie.prevRotationPitch) * par2;
                GL11.glPushMatrix();
                GL11.glRotatef(f3, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(f4, 1.0F, 0.0F, 0.0F);
                GL11.glTranslatef(0.375F * (float)(i * 2 - 1), 0.0F, 0.0F);
                GL11.glTranslatef(0.0F, -0.375F, 0.0F);
                GL11.glRotatef(-f4, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(-f3, 0.0F, 1.0F, 0.0F);
                float f5 = 1.3333334F;
                GL11.glScalef(f5, f5, f5);
                this.modelBipedMain.renderEars(0.0625F);
                GL11.glPopMatrix();
            }
        }

        boolean flag = par1EntityPlayerZombie.func_110310_o().func_110557_a();
        boolean flag1 = !par1EntityPlayerZombie.isInvisible();
        boolean flag2 = true;
        float f6;

        if (flag && flag1 && flag2)
        {
            this.func_110776_a(par1EntityPlayerZombie.func_110303_q());
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 0.0F, 0.125F);
            double d0 = par1EntityPlayerZombie.field_71091_bM + (par1EntityPlayerZombie.field_71094_bP - par1EntityPlayerZombie.field_71091_bM) * (double)par2 - (par1EntityPlayerZombie.prevPosX + (par1EntityPlayerZombie.posX - par1EntityPlayerZombie.prevPosX) * (double)par2);
            double d1 = par1EntityPlayerZombie.field_71096_bN + (par1EntityPlayerZombie.field_71095_bQ - par1EntityPlayerZombie.field_71096_bN) * (double)par2 - (par1EntityPlayerZombie.prevPosY + (par1EntityPlayerZombie.posY - par1EntityPlayerZombie.prevPosY) * (double)par2);
            double d2 = par1EntityPlayerZombie.field_71097_bO + (par1EntityPlayerZombie.field_71085_bR - par1EntityPlayerZombie.field_71097_bO) * (double)par2 - (par1EntityPlayerZombie.prevPosZ + (par1EntityPlayerZombie.posZ - par1EntityPlayerZombie.prevPosZ) * (double)par2);
            f6 = par1EntityPlayerZombie.prevRenderYawOffset + (par1EntityPlayerZombie.renderYawOffset - par1EntityPlayerZombie.prevRenderYawOffset) * par2;
            double d3 = (double)MathHelper.sin(f6 * (float)Math.PI / 180.0F);
            double d4 = (double)(-MathHelper.cos(f6 * (float)Math.PI / 180.0F));
            float f7 = (float)d1 * 10.0F;

            if (f7 < -6.0F)
            {
                f7 = -6.0F;
            }

            if (f7 > 32.0F)
            {
                f7 = 32.0F;
            }

            float f8 = (float)(d0 * d3 + d2 * d4) * 100.0F;
            float f9 = (float)(d0 * d4 - d2 * d3) * 100.0F;

            if (f8 < 0.0F)
            {
                f8 = 0.0F;
            }

            float f10 = 0; //par1EntityPlayerZombie.prevCameraYaw + (par1EntityPlayerZombie.cameraYaw - par1EntityPlayerZombie.prevCameraYaw) * par2;
            f7 += MathHelper.sin((par1EntityPlayerZombie.prevDistanceWalkedModified + (par1EntityPlayerZombie.distanceWalkedModified - par1EntityPlayerZombie.prevDistanceWalkedModified) * par2) * 6.0F) * 32.0F * f10;

            if (par1EntityPlayerZombie.isSneaking())
            {
                f7 += 25.0F;
            }

            GL11.glRotatef(6.0F + f8 / 2.0F + f7, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(f9 / 2.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-f9 / 2.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            this.modelBipedMain.renderCloak(0.0625F);
            GL11.glPopMatrix();
        }

        ItemStack itemstack1 = par1EntityPlayerZombie.inventory.getCurrentItem();

        if (itemstack1 != null)
        {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedRightArm.postRender(0.0625F);
            GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);

           

            EnumAction enumaction = null;

            if (par1EntityPlayerZombie.getItemInUseCount() > 0)
            {
                enumaction = itemstack1.getItemUseAction();
            }

            float f11;

            IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack1, EQUIPPED);
            boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(EQUIPPED, itemstack1, BLOCK_3D));
            boolean isBlock = itemstack1.itemID < Block.blocksList.length && itemstack1.getItemSpriteNumber() == 0;

            if (is3D || (isBlock && RenderBlocks.renderItemIn3d(Block.blocksList[itemstack1.itemID].getRenderType())))
            {
                f11 = 0.5F;
                GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
                f11 *= 0.75F;
                GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(-f11, -f11, f11);
            }
            else if (itemstack1.itemID == Item.bow.itemID)
            {
                f11 = 0.625F;
                GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
                GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(f11, -f11, f11);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            }
            else if (Item.itemsList[itemstack1.itemID].isFull3D())
            {
                f11 = 0.625F;

                if (Item.itemsList[itemstack1.itemID].shouldRotateAroundWhenRendering())
                {
                    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glTranslatef(0.0F, -0.125F, 0.0F);
                }

                if (par1EntityPlayerZombie.getItemInUseCount() > 0 && enumaction == EnumAction.block)
                {
                    GL11.glTranslatef(0.05F, 0.0F, -0.1F);
                    GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
                }

                GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
                GL11.glScalef(f11, -f11, f11);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            }
            else
            {
                f11 = 0.375F;
                GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
                GL11.glScalef(f11, f11, f11);
                GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
            }

            float f12;
            float f13;
            int j;

            if (itemstack1.getItem().requiresMultipleRenderPasses())
            {
                for (j = 0; j < itemstack1.getItem().getRenderPasses(itemstack1.getItemDamage()); ++j)
                {
                    int k = itemstack1.getItem().getColorFromItemStack(itemstack1, j);
                    f13 = (float)(k >> 16 & 255) / 255.0F;
                    f12 = (float)(k >> 8 & 255) / 255.0F;
                    f6 = (float)(k & 255) / 255.0F;
                    GL11.glColor4f(f13, f12, f6, 1.0F);
                    this.renderManager.itemRenderer.renderItem(par1EntityPlayerZombie, itemstack1, j);
                }
            }
            else
            {
                j = itemstack1.getItem().getColorFromItemStack(itemstack1, 0);
                float f14 = (float)(j >> 16 & 255) / 255.0F;
                f13 = (float)(j >> 8 & 255) / 255.0F;
                f12 = (float)(j & 255) / 255.0F;
                GL11.glColor4f(f14, f13, f12, 1.0F);
                this.renderManager.itemRenderer.renderItem(par1EntityPlayerZombie, itemstack1, 0);
            }

            GL11.glPopMatrix();
        }
    }
	
	protected void renderPlayerScale(EntityPlayerZombie par1EntityPlayerZombie, float par2)
    {
        float f1 = 0.9375F;
        GL11.glScalef(f1, f1, f1);
    }
	
	protected void func_96450_a(EntityPlayerZombie par1EntityPlayerZombie, double par2, double par4, double par6, String par8Str, float par9, double par10)
    {
        if (par10 < 100.0D)
        {
            Scoreboard scoreboard = par1AbstractClientPlayer.getWorldScoreboard();
            ScoreObjective scoreobjective = scoreboard.func_96539_a(2);

            if (scoreobjective != null)
            {
                Score score = scoreboard.func_96529_a(par1AbstractClientPlayer.getEntityName(), scoreobjective);

                if (par1AbstractClientPlayer.isPlayerSleeping())
                {
                    this.renderLivingLabel(par1AbstractClientPlayer, score.getScorePoints() + " " + scoreobjective.getDisplayName(), par2, par4 - 1.5D, par6, 64);
                }
                else
                {
                    this.renderLivingLabel(par1AbstractClientPlayer, score.getScorePoints() + " " + scoreobjective.getDisplayName(), par2, par4, par6, 64);
                }

                par4 += (double)((float)this.getFontRendererFromRenderManager().FONT_HEIGHT * 1.15F * par9);
            }
        }

        super.func_96449_a(par1AbstractClientPlayer, par2, par4, par6, par8Str, par9, par10);
    }
	protected ResourceLocation func_110817_a(EntityPlayerZombie par1EntityPlayerZombie)
    {
        return par1EntityPlayerZombie.func_110306_p();
    }
	@Override
	protected ResourceLocation func_110775_a(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
