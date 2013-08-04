package com.nekokittygames.modjam.UnDeath.client;

import org.lwjgl.opengl.GL11;

import com.nekokittygames.modjam.UnDeath.EntityPlayerSlime;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.ResourceLocation;

public class RenderPlayerSlime extends RenderLiving {

	private static final ResourceLocation field_110897_a = new ResourceLocation("undeath","textures/entity/playerSlime.png");
    private ModelBase scaleAmount;

    public RenderPlayerSlime(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
    {
        super(par1ModelBase, par3);
        this.scaleAmount = par2ModelBase;
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
