package com.nekokittygames.modjam.UnDeath.client;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ThreadDownloadImageData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by nekosune on 24/06/14.
 */
public class ClientUtils {
    public static BufferedImage getBufferedImageSkin(ThreadDownloadImageData imageData) {
        BufferedImage bufferedImage = null;
        boolean isUploaded= ObfuscationReflectionHelper.getPrivateValue(ThreadDownloadImageData.class, imageData, "textureUploaded", "field_110559_g", "bpr.h");
        if (isUploaded) {
            bufferedImage = ObfuscationReflectionHelper.getPrivateValue(ThreadDownloadImageData.class, imageData, "bufferedImage", "field_110560_d", "bpr.j");
        }
        else {
                try {
                    bufferedImage = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(AbstractClientPlayer.locationStevePng).getInputStream());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
        }

        imageData.setBufferedImage(bufferedImage);
        return bufferedImage;
    }
}
