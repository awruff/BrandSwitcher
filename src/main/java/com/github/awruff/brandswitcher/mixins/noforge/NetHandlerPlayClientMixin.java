package com.github.awruff.brandswitcher.mixins.noforge;

import com.github.awruff.brandswitcher.ConfigHelper;
import io.netty.buffer.Unpooled;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.world.WorldSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author CCBlueX
 */
@Mixin(NetHandlerPlayClient.class)
public class NetHandlerPlayClientMixin {
    @Shadow private Minecraft gameController;

    @Shadow private WorldClient clientWorldController;

    @Shadow public int currentServerMaxPlayers;

    @Shadow @Final private NetworkManager netManager;

    @Inject(method = "handleJoinGame", at = @At("HEAD"), cancellable = true)
    private void handleJoinGameVanilla(S01PacketJoinGame packet, CallbackInfo ci) {
        if (ConfigHelper.disableForge()) {
            PacketThreadUtil.checkThreadAndEnqueue(packet, (NetHandlerPlayClient) (Object) this, this.gameController);

            this.gameController.playerController = new PlayerControllerMP(this.gameController, (NetHandlerPlayClient) (Object) this);

            this.clientWorldController = new WorldClient((NetHandlerPlayClient) (Object) this, new WorldSettings(0L, packet.getGameType(), false, packet.isHardcoreMode(), packet.getWorldType()), packet.getDimension(), packet.getDifficulty(), this.gameController.mcProfiler);

            this.gameController.gameSettings.difficulty = packet.getDifficulty();
            this.gameController.loadWorld(this.clientWorldController);
            this.gameController.thePlayer.dimension = packet.getDimension();
            this.gameController.displayGuiScreen(new GuiDownloadTerrain((NetHandlerPlayClient) (Object) this));
            this.gameController.thePlayer.setEntityId(packet.getEntityId());

            this.currentServerMaxPlayers = packet.getMaxPlayers();

            this.gameController.thePlayer.setReducedDebug(packet.isReducedDebugInfo());
            this.gameController.playerController.setGameType(packet.getGameType());
            this.gameController.gameSettings.sendSettingsToServer();

            this.netManager.sendPacket(new C17PacketCustomPayload("MC|Brand", (new PacketBuffer(Unpooled.buffer())).writeString(ClientBrandRetriever.getClientModName())));

            ci.cancel();
        }
    }
}
