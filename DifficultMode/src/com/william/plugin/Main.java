package com.william.plugin;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Main extends JavaPlugin implements Listener{

	@Override
	public void onEnable() {
		System.out.println("Difficult Mode plugin has worked (sorta)");
		
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {
		System.out.println("Difficult Mode disabled");
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		//Damage to player is doubled:
		if (e.getEntity() instanceof Player) {
			double finalDamage = e.getFinalDamage();
			e.setDamage(finalDamage * 2);
		}
		//Damage to blaze and wither skeletons is reduced by 50%:
		else if (e.getEntity() instanceof Blaze || e.getEntity() instanceof WitherSkeleton) {
			double finalDamage = e.getFinalDamage();
			e.setDamage(finalDamage * 0.5);
		}
		//Damage to End mobs is reduced by 30%:
		else if (e.getEntity() instanceof Enderman || e.getEntity() instanceof EnderDragon) {
			double finalDamage = e.getFinalDamage();
			e.setDamage(finalDamage * 0.7);
		}
		//Damage to silverfish is reduced by 80%:
		else if (e.getEntity() instanceof Silverfish) {
			double finalDamage = e.getFinalDamage();
			e.setDamage(finalDamage * 0.2);
		}
	
		
	}
	
	//Player will have only one hunger bar when they leave a bed:
	@EventHandler
	public void onPlayerLeaveBed(PlayerBedLeaveEvent e) {
		
		Player player = e.getPlayer();
		
		player.setFoodLevel(2);
	}
	
	//Player will be given negative potion effects when they eat certain foods:
	@EventHandler
	public void onEntityEat(PlayerItemConsumeEvent e) {
		ItemStack food = e.getItem();
		Player player = e.getPlayer();
		
		//Eating fish will give hunger for 10 or 15 seconds:
		if (food.getType() == Material.RAW_FISH) {
			Random rand = new Random();
			Boolean extraHunger = rand.nextBoolean();
			if (extraHunger) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 300, 0));
			}
			else {
				player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200, 0));
			}
		}
		
		//Eating raw beef will give hunger or poison for 10 seconds:
		else if (food.getType() == Material.RAW_BEEF) {
			Random rand = new Random();
			Boolean poison = rand.nextBoolean();
			if (poison) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 0));
			}
			else {
				player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200, 0));
			}
		}
		
		//Eating raw pork, chicken, or rabbit will give poison for 1 or 2 for 10 seconds:
		else if (food.getType() == Material.PORK || food.getType() == Material.RAW_CHICKEN || food.getType() == Material.RABBIT) {
			Random rand = new Random();
			Boolean extraPoison = rand.nextBoolean();
			if (extraPoison) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 1));
			}
			else {
				player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 0));
			}
		}
		
		//Eating rotten flesh will give poison 2 and hunger for 10 seconds:
		else if (food.getType() == Material.ROTTEN_FLESH) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 1));
			player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200, 0));
		}
		
		//Eating rotten flesh will give poison 3 and hunger for 15 seconds:
		else if (food.getType() == Material.SPIDER_EYE || food.getType() == Material.POISONOUS_POTATO) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 300, 2));
			player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 300, 0));
		}

	}
	
}
