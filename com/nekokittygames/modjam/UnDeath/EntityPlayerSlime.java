package com.nekokittygames.modjam.UnDeath;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.world.World;

public class EntityPlayerSlime extends EntitySlime {
	public static int EntityId;
	public EntityPlayerSlime(World par1World) {
		super(par1World);
	}
	
	@Override
	public void setDead()
    {
        this.isDead = true;
    }
	@Override
	protected void setSlimeSize(int par1)
    {
        this.dataWatcher.updateObject(16, new Byte((byte)par1));
        this.setSize(0.6F * (float)par1, 0.6F * (float)par1);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a((double)(par1 * par1));
        //this.setEntityHealth(this.func_110138_aP());
        this.experienceValue = par1;
    }
}
