package com.nekokittygames.modjam.UnDeath;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid=UnDeath.ID,name=UnDeath.NAME,version=UnDeath.VERSION,modLanguage="java")
@NetworkMod(clientSideRequired=true,serverSideRequired=false)
public class UnDeath {
	public static final String ID = "UnDeath";
	public static final String VERSION = "1.0";
	public static final String NAME = "Un Death";
	
	
	@Instance(ID)
	public static UnDeath Instance;
}
