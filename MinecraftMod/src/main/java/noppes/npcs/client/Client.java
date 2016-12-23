package noppes.npcs.client;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import noppes.npcs.CustomNpcs;
import noppes.npcs.Server;
import noppes.npcs.constants.EnumPacketServer;

public class Client {

   public static void sendData(EnumPacketServer enu, Object ... obs) {
      ByteBuf buffer = Unpooled.buffer();

      try {
         if(!Server.fillBuffer(buffer, enu, obs)) {
            return;
         }

         CustomNpcs.Channel.sendToServer(new FMLProxyPacket(buffer, "CustomNPCs"));
      } catch (IOException var4) {
         var4.printStackTrace();
      }

   }
}
