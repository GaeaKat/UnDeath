package com.nekokittygames.modjam.UnDeath.client;

import com.google.common.collect.Lists;
import com.nekokittygames.modjam.UnDeath.EntityPlayerZombie;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
@SideOnly(Side.CLIENT)
public class ResourceLayeredTexture extends AbstractTexture {
	public final List field_110567_b;

	public ResourceLayeredTexture(ResourceLocation ... par1ArrayOfStr)
	{
		this.field_110567_b = Lists.newArrayList(par1ArrayOfStr);
	}

	@Override
	public void loadTexture(IResourceManager par1ResourceManager) throws IOException
	{
		BufferedImage bufferedimage = null;

		Iterator iterator = this.field_110567_b.iterator();

		while (iterator.hasNext())
		{
			ResourceLocation s = (ResourceLocation)iterator.next();

			if (s != null)
			{

				try
				{
					IResource resource = par1ResourceManager.getResource(s);
					InputStream inputstream = resource.getInputStream();
					BufferedImage bufferedimage1 = ImageIO.read(inputstream);

					if (bufferedimage == null)
					{
						bufferedimage = new BufferedImage(bufferedimage1.getWidth(), bufferedimage1.getHeight(), 2);
					}


					bufferedimage.getGraphics().drawImage(bufferedimage1, 0, 0, (ImageObserver)null);
				}
				catch(IOException e)
				{
					ITextureObject object = Minecraft.getMinecraft().getTextureManager().getTexture(s);
					if(object==null || ! (object instanceof ThreadDownloadImageData))
					{
						e.printStackTrace();
						return;
					}

					ThreadDownloadImageData data=(ThreadDownloadImageData)object;
					BufferedImage bufferedimage1;
                    bufferedimage1= ReflectionHelper.getPrivateValue(ThreadDownloadImageData.class,data, "bufferedImage", "field_110560_d", "f" );
					if(bufferedimage1!=null)
					{
						if (bufferedimage == null)
						{
							bufferedimage = new BufferedImage(bufferedimage1.getWidth(), bufferedimage1.getHeight(), 2);

						}
						bufferedimage.getGraphics().drawImage(bufferedimage1, 0, 0, (ImageObserver)null);
					}
					else
					{
						IResource resource = par1ResourceManager.getResource(EntityPlayerZombie.field_110314_b);
						InputStream inputstream = resource.getInputStream();
						bufferedimage1 = ImageIO.read(inputstream);

						if (bufferedimage == null)
						{
							bufferedimage = new BufferedImage(bufferedimage1.getWidth(), bufferedimage1.getHeight(), 2);
						}
						bufferedimage.getGraphics().drawImage(bufferedimage1, 0, 0, (ImageObserver)null);
					}

				}

			}
		}
		//TextureUtil.func_110987_a(this.func_110552_b(), bufferedimage);
        TextureUtil.uploadTextureImage(this.glTextureId,bufferedimage);
	}
}
