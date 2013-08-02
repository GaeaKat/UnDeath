package com.nekokittygames.modjam.UnDeath.client;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.Resource;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.util.ResourceLocation;

public class ResourceLayeredTexture extends AbstractTexture {
	public final List field_110567_b;

    public ResourceLayeredTexture(ResourceLocation ... par1ArrayOfStr)
    {
        this.field_110567_b = Lists.newArrayList(par1ArrayOfStr);
    }

    public void func_110551_a(ResourceManager par1ResourceManager) throws IOException
    {
        BufferedImage bufferedimage = null;

        try
        {
            Iterator iterator = this.field_110567_b.iterator();

            while (iterator.hasNext())
            {
                ResourceLocation s = (ResourceLocation)iterator.next();

                if (s != null)
                {
                	
                	Resource resource = par1ResourceManager.func_110536_a(s);
                    InputStream inputstream = resource.func_110527_b();
                    BufferedImage bufferedimage1 = ImageIO.read(inputstream);

                    if (bufferedimage == null)
                    {
                        bufferedimage = new BufferedImage(bufferedimage1.getWidth(), bufferedimage1.getHeight(), 2);
                    }

                    bufferedimage.getGraphics().drawImage(bufferedimage1, 0, 0, (ImageObserver)null);
                }
            }
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
            return;
        }

        TextureUtil.func_110987_a(this.func_110552_b(), bufferedimage);
    }

}
