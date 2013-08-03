package com.nekokittygames.modjam.UnDeath;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.world.World;

public class EntityPlayerSkellington extends EntitySkeleton implements IEntityAdditionalSpawnData {

	public EntityPlayerSkellington(World par1World) {
		super(par1World);
	}

	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		// TODO Auto-generated method stub
		
	}

}
