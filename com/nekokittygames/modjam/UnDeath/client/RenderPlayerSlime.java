package com.nekokittygames.modjam.UnDeath.client;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.google.common.primitives.SignedBytes;
import com.nekokittygames.modjam.UnDeath.EntityPlayerSlime;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderPlayerSlime extends RenderLiving {

	private static final ResourceLocation field_110897_a = new ResourceLocation("undeath","textures/entity/playerSlime.png");
    private ModelBase scaleAmount;
    private RenderItem itemRenderer;
    private Random rand;
    private static float[][] posShifts = { { 0.3F, 0.45F, 0.3F }, { 0.7F, 0.45F, 0.3F }, { 0.3F, 0.45F, 0.7F }, { 0.7F, 0.45F, 0.7F }, { 0.3F, 0.1F, 0.3F },{ 0.7F, 0.1F, 0.3F }, { 0.3F, 0.1F, 0.7F }, { 0.7F, 0.1F, 0.7F }, { 0.5F, 0.32F, 0.5F }, };
    public RenderPlayerSlime(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
    {
        super(par1ModelBase, par3);
        this.scaleAmount = par2ModelBase;
        itemRenderer=new RenderItem()
        {

			@Override
			public boolean shouldSpreadItems() {
				return false;
			}

			@Override
			public boolean shouldBob() {
				return true;
			}

			@Override
			public byte getMiniBlockCount(ItemStack stack) {
				return SignedBytes.saturatedCast(Math.min(stack.stackSize/32, 15)+1);
			}

			@Override
			public byte getMiniItemCount(ItemStack stack) {
				return SignedBytes.saturatedCast(Math.min(stack.stackSize/32, 7)+1);
			}
        	
        };
        itemRenderer.setRenderManager(RenderManager.instance);
        rand=new Random();
    }

    @Override
	protected void passSpecialRender(EntityLivingBase par1EntityLivingBase,
			double x, double y, double z) {
    	EntityPlayerSlime pSlime=(EntityPlayerSlime)par1EntityLivingBase;
		// TODO Auto-generated method stub
		super.passSpecialRender(par1EntityLivingBase, x, y, z);
		
		
	}

	/**
     * Determines whether Slime Render should pass or not.
     */
    protected int shouldSlimeRenderPass(EntityPlayerSlime par1EntitySlime, int par2, float par3)
    {
        if (par1EntitySlime.isInvisible())
        {
            return 0;
        }
        else if (par2 == 0)
        {
            this.setRenderPassModel(this.scaleAmount);
            GL11.glEnable(GL11.GL_NORMALIZE);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            return 1;
        }
        else
        {
            if (par2 == 1)
            {
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            }

            return -1;
        }
    }

    /**
     * sets the scale for the slime based on getSlimeSize in EntitySlime
     */
    protected void scaleSlime(EntityPlayerSlime par1EntitySlime, float par2)
    {
        float f1 = (float)par1EntitySlime.getSlimeSize();
        float f2 = (par1EntitySlime.field_70812_c + (par1EntitySlime.field_70811_b - par1EntitySlime.field_70812_c) * par2) / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        GL11.glScalef(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }

    protected ResourceLocation func_110896_a(EntityPlayerSlime par1EntitySlime)
    {
        return field_110897_a;
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.scaleSlime((EntityPlayerSlime)par1EntityLivingBase, par2);
        
    }

    @Override
	public void doRender(Entity par1Entity, double par2, double par4,
			double par6, float par8, float par9) {
    	EntityPlayerSlime pSlime=(EntityPlayerSlime)par1Entity;
  		rand.setSeed(1337L);
  		float shiftX;
          float shiftY;
          float shiftZ;
          int shift = 0;
        float blockScale = 1.0F ;//*(1f/pSlime.getSlimeSize());
  		float timeDelta=(float)(360.0*(double)(System.currentTimeMillis() & 0x3FFFL)/(double)0x3FFFL);
  		GL11.glPushMatrix();
  		GL11.glDisable(GL11.GL_LIGHTING);
  		GL11.glTranslatef((float)par2, (float)par4, (float)par6);
  		EntityItem custItem=new EntityItem(pSlime.worldObj);
  		custItem.hoverStart=0f;
  		for(ItemStack item:pSlime.items)
  		{
  			if(shift>=posShifts.length)
  				break;
  			if(item==null)
  			{
  				shift++;
  				continue;
  			}
  			shiftX=posShifts[shift][0];
  			shiftY=posShifts[shift][1];
  			shiftZ=posShifts[shift][2];
  			shift++;
  			GL11.glPushMatrix();
  			GL11.glTranslatef(shiftX, shiftY, shiftZ); 
  			GL11.glRotatef(timeDelta, 0.0F, 1.0F, 0.0F);
  			GL11.glScalef(blockScale, blockScale, blockScale);
  			custItem.setEntityItemStack(item);
  			itemRenderer.doRenderItem(custItem, 0, 0, 0, 0, 0);
  			GL11.glPopMatrix();
  		}
  		GL11.glEnable(GL11.GL_LIGHTING);
  		GL11.glPopMatrix();
  		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		super.doRender(par1Entity, par2, par4, par6, par8, par9);
	}

	/**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
    {
        return this.shouldSlimeRenderPass((EntityPlayerSlime)par1EntityLivingBase, par2, par3);
    }

    protected ResourceLocation func_110775_a(Entity par1Entity)
    {
        return this.func_110896_a((EntityPlayerSlime)par1Entity);
    }

}
