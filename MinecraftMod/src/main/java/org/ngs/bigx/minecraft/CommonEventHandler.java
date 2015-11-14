package org.ngs.bigx.minecraft;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.ngs.bigx.minecraft.item.ModItems;
import org.ngs.bigx.minecraft.quests.worlds.QuestTeleporter;
import org.ngs.bigx.minecraft.quests.worlds.WorldProviderQuests;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerOpenContainerEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.event.world.WorldEvent;

public class CommonEventHandler {
	
	int server_tick = 0;
	
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		BikeWorldData data = BikeWorldData.get(event.world);
	}
	
	@SubscribeEvent
	public void onPlayerJoin(PlayerUseItemEvent.Start event) {
		WorldServer ws = MinecraftServer.getServer().worldServerForDimension(WorldProviderQuests.dimID);
		if (ws!=null&&event.entity instanceof EntityPlayerMP) {
			new QuestTeleporter(ws).teleport(event.entity, ws);
		}
	}
	
	 //Called when the server ticks. Usually 20 ticks a second. 
	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {
		if (MinecraftServer.getServer()!=null&&event.phase==TickEvent.Phase.END) {
			boolean isServer = MinecraftServer.getServer().isDedicatedServer();
			server_tick++;
			//20 ticks = 1 second
			if (server_tick==20) {
				server_tick = 0;
			}
			WorldServer[] ws = MinecraftServer.getServer().worldServers;
			for (WorldServer w:ws) {
					if (w.provider.dimensionId==WorldProviderQuests.dimID) { //check if this is a quest world
						List<EntityPlayerMP> lp = w.playerEntities;
						for (EntityPlayerMP p:lp) {
							//Build stone wherever the player walks, at a certain y value (hardcoded)
//							w.setBlock((int) p.posX, 64, (int)  p.posZ, Blocks.stone);
						}
					}
			}
		}
	}
		

	
}