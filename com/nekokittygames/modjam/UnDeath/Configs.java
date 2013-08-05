package com.nekokittygames.modjam.UnDeath;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;

public class Configs {

	@Retention(RetentionPolicy.RUNTIME)
	private static @interface CfgId {
		public boolean block() default false;
	}
	@Retention(RetentionPolicy.RUNTIME)
	private static @interface CfgBool {}
	
	@Retention(RetentionPolicy.RUNTIME)
	private static @interface CfgString {}
	
	@Retention(RetentionPolicy.RUNTIME)
	private static @interface CfgDouble {}
	

	
	@CfgId(block=false)
	public static int debugStick=2032;
	@CfgId(block=false)
	public static int testItem=2033;
	
	@CfgDouble
	public static double ZombificationChance=1.0;
	
	@CfgDouble
	public static double SkellificationChance=1.0;
	
	@CfgDouble
	public static double slimeEngulfChance=1.0;
	
	public static void  load(Configuration config) {
		try {
			config.load();
			Field[] fields = Configs.class.getFields();
			for(Field field : fields) {
				CfgId annotation = field.getAnnotation(CfgId.class);
				if(annotation != null) {
					int id = field.getInt(null);
					if(annotation.block()){
						id = config.getBlock(field.getName(), id).getInt();
					}else{
						id = config.getItem(field.getName(), id).getInt();
					}
					field.setInt(null, id);
				} else {
					if(field.isAnnotationPresent(CfgBool.class)){
						boolean bool = field.getBoolean(null);
						bool = config.get(Configuration.CATEGORY_GENERAL,
								field.getName(), bool).getBoolean(bool);
						field.setBoolean(null, bool);
					}
					else if(field.isAnnotationPresent(CfgString.class))
					{
						String string=(String)field.get(null);
						string=config.get(Configuration.CATEGORY_GENERAL, field.getName(), string).getString();
						field.set(null, string);
					}
					else if(field.isAnnotationPresent(CfgDouble.class))
					{
						double doub=field.getDouble(null);
						doub=config.get(Configuration.CATEGORY_GENERAL, field.getName(), doub).getDouble(doub);
						field.set(null, doub);
					}
				}
			}
		} catch(Exception e) {
			UnDeath.logging.log(Level.SEVERE, "Got an error in loading config!", e);
		} finally {
			config.save();
		}
	}
}
