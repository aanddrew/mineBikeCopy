package org.ngs.bigx.minecraft;

import java.io.DataInputStream;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import org.ngs.bigx.minecraft.client.ClientProxy;
import org.ngs.bigx.minecraft.item.ItemBike;
import org.ngs.bigx.net.gameplugin.client.BiGXNetClient;
import org.ngs.bigx.net.gameplugin.common.BiGXNetPacket;
import org.ngs.bigx.net.gameplugin.exception.BiGXInternalGamePluginExcpetion;
import org.ngs.bigx.net.gameplugin.exception.BiGXNetException;

import net.minecraft.client.Minecraft;

public class BiGXPacketHandler {
	
	public static boolean Handle(BiGXNetClient client, BiGXNetPacket packet) {
		ByteBuffer buf = ByteBuffer.wrap(packet.data,1,packet.DATALENGTH-1);
		buf.order(java.nio.ByteOrder.LITTLE_ENDIAN);
		Context context = Main.instance().context;
		switch (packet.deviceEvent) {
			case org.ngs.bigx.dictionary.protocol.specification.dataType.ROTATIONSTATE:
				if (Minecraft.getMinecraft().thePlayer!=null) {
					int old_rotation = context.rotation;
					int new_rotation = buf.getInt();
					if (new_rotation!=context.rotation) {
						context.rotation = new_rotation;
						if (old_rotation>330&&new_rotation<30) {
							old_rotation -= 360;
						}
						double change = (new_rotation-old_rotation);

						context.setSpeed( (float) Math.min( ItemBike.MAXBIKESPEED, Math.max( context.getSpeed() + change / 1000 , 0 ) ) );
					} 
				}
			break;
			case org.ngs.bigx.dictionary.protocol.specification.dataType.HEART:
				context.heartrate = buf.getInt();
			break;
			case org.ngs.bigx.dictionary.protocol.specification.dataType.MOVE_FORWARDBACKWARD:
				if (Minecraft.getMinecraft().thePlayer!=null) {
					int change = packet.data[1] | (packet.data[2] << 8);
					
					if(change >= 512) {
						change -= 512;
						change *= -1;
					}
					
					System.out.println("revceived value [" + change + "] Value that will be applied [" + ((double)change) + "]");
					
					if(context.getSpeed() + ((double)change) >= 0){
						context.setSpeed( (float) Math.min( ItemBike.MAXBIKESPEED, Math.max( context.getSpeed() + ((double)change * .1f), 0 ) ) );
					}
					else{
						context.setSpeed( (float) Math.max( ItemBike.MAXBIKESPEED * -1, Math.min( context.getSpeed() + ((double)change * .1f), 0 ) ) );
					}
				}
			break;
			case org.ngs.bigx.dictionary.protocol.specification.dataType.TIMELAPSE_HEARTRATEREQUIREMENT:
				context.timeSpent = packet.data[1];
				context.timeSpentSmall = packet.data[2];
			break;
		}
		return true;
	}
	
	public static void sendPacket(BiGXNetClient client, BiGXNetPacket packet) {
		try {
			client.send(packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized static void connect(BiGXNetClient client) throws SocketException, UnknownHostException, BiGXNetException, BiGXInternalGamePluginExcpetion
	{
		client.connect();
	}
}