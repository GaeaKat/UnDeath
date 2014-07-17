package com.nekokittygames.modjam.UnDeath.client;

import com.nekokittygames.modjam.UnDeath.UnDeath;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.InputStream;
import java.util.logging.Level;

@SideOnly(Side.CLIENT)
public class RResourceLayeredTexture extends AbstractTexture {
    public final ResourceLocation data;
    public final ResourceLocation loc;
    public final String user;
    public RResourceLayeredTexture(ResourceLocation par1ArrayOfStr, ResourceLocation location,String username)
    {
        data= par1ArrayOfStr;
        loc=location;
        user=username;
    }

    @Override
    public void loadTexture(IResourceManager par1ResourceManager)
    {
        BufferedImage bufferedimage = null;



                try
                {
                    UnDeath.logging.warning("Got: "+data.toString());
                    InputStream inputstream = par1ResourceManager.getResource(data).getInputStream();
                    TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
                    Object object = texturemanager.getTexture(data);
                    ThreadDownloadImageData tddata=AbstractClientPlayer.getDownloadImageSkin(data,user);

                    BufferedImage bufferedimage1 = ReflectionHelper.getPrivateValue(ThreadDownloadImageData.class, tddata, "bufferedImage");

                    if (bufferedimage == null)
                    {
                        bufferedimage = new BufferedImage(bufferedimage1.getWidth(), bufferedimage1.getHeight(), 2);
                    }


                    bufferedimage.getGraphics().drawImage(bufferedimage1, 0, 0, (ImageObserver)null);
                    inputstream = par1ResourceManager.getResource(loc).getInputStream();
                    bufferedimage1 = ImageIO.read(inputstream);
                    bufferedimage.getGraphics().drawImage(bufferedimage1, 0, 0, (ImageObserver)null);
                }
                catch(Exception e)
                {
                    UnDeath.logging.log(Level.SEVERE, "Couldn\'t load layered image", e);
                    e.printStackTrace();
                    return;
                }
        //TextureUtil.func_110987_a(this.func_110552_b(), bufferedimage);
        TextureUtil.uploadTextureImage(this.getGlTextureId(),bufferedimage);
    }
}