package com.nekokittygames.modjam.UnDeath.client;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.Resource;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Lists;
import com.nekokittygames.modjam.UnDeath.EntityPlayerZombie;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ResourceLayeredTexture extends AbstractTexture {
    public final List<ResourceLocation> field_110567_b;

    public ResourceLayeredTexture(ResourceLocation... par1ArrayOfStr) {
        field_110567_b = Lists.newArrayList(par1ArrayOfStr);
    }

    @Override
    public void func_110551_a(ResourceManager par1ResourceManager)
            throws IOException {
        BufferedImage bufferedimage = null;

        final Iterator<ResourceLocation> iterator = field_110567_b.iterator();

        while (iterator.hasNext()) {
            final ResourceLocation s = (ResourceLocation) iterator.next();

            if (s != null) {

                try {
                    final Resource resource = par1ResourceManager
                            .func_110536_a(s);
                    final InputStream inputstream = resource.func_110527_b();
                    final BufferedImage bufferedimage1 = ImageIO
                            .read(inputstream);

                    if (bufferedimage == null) {
                        bufferedimage = new BufferedImage(
                                bufferedimage1.getWidth(),
                                bufferedimage1.getHeight(), 2);
                    }

                    bufferedimage.getGraphics().drawImage(bufferedimage1, 0, 0,
                            (ImageObserver) null);
                } catch (final IOException e) {
                    final TextureObject object = Minecraft.getMinecraft()
                            .func_110434_K().func_110581_b(s);
                    if (object == null
                            || !(object instanceof ThreadDownloadZombieImageData)) {
                        e.printStackTrace();
                        return;
                    }

                    final ThreadDownloadZombieImageData data = (ThreadDownloadZombieImageData) object;
                    BufferedImage bufferedimage1 = data.getField_110560_d();
                    if (bufferedimage1 != null) {
                        if (bufferedimage == null) {
                            bufferedimage = new BufferedImage(
                                    bufferedimage1.getWidth(),
                                    bufferedimage1.getHeight(), 2);

                        }
                        bufferedimage.getGraphics().drawImage(bufferedimage1,
                                0, 0, (ImageObserver) null);
                    } else {
                        final Resource resource = par1ResourceManager
                                .func_110536_a(EntityPlayerZombie.field_110314_b);
                        final InputStream inputstream = resource
                                .func_110527_b();
                        bufferedimage1 = ImageIO.read(inputstream);

                        if (bufferedimage == null) {
                            bufferedimage = new BufferedImage(
                                    bufferedimage1.getWidth(),
                                    bufferedimage1.getHeight(), 2);
                        }
                        bufferedimage.getGraphics().drawImage(bufferedimage1,
                                0, 0, (ImageObserver) null);
                    }

                }

            }
        }
        TextureUtil.func_110987_a(this.func_110552_b(), bufferedimage);
    }
}
