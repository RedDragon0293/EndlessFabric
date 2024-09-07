package cn.asone.endless.mixin;

import cn.asone.endless.Endless;
import cn.asone.endless.script.ScriptManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public class MixinChatScreen extends MixinScreen {
    @Inject(method = "sendMessage", at = @At("HEAD"), cancellable = true)
    private void onSendMessage(String chatText, boolean addToHistory, CallbackInfo ci) {
        if (".reloadEndless".equalsIgnoreCase(chatText)) {
            if (client != null) {
                client.inGameHud.getChatHud().addToMessageHistory(chatText);
                Endless.INSTANCE.setEnabled(false);
                var instance = ScriptManager.loadScript();
                Endless.INSTANCE.setInstance(instance);
                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("成功加载 " + instance.length + " 个外部模块."));
            }
            ci.cancel();
        }
    }
}
