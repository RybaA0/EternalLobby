package com.eternalcode.lobby.config.implementation;

import com.cryptomorin.xseries.XSound;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import panda.utilities.StringUtils;

import java.util.Arrays;
import java.util.List;

public class PluginConfiguration {

    @Description("General settings of eternalcode-lobby")
    public Settings settings = new Settings();
    public static Settings.Join join = new Settings.Join();


    @Contextual
    public static class Settings {
        @Contextual
        public static class Join {
            public boolean setGameModeAtJoin = true;
            public GameMode gameModeAtJoin = GameMode.ADVENTURE;
        }

    }


    @Description({ StringUtils.EMPTY, "# Wysokość po której gracz zostaje teleportowany na spawn" })
    public int voidTeleportHeight = -5;

    @Description({ StringUtils.EMPTY, "# Head Display URL" })
    @Description("# zmienne: %name%, %uuid%")
    public String apiUrl = "https://minepic.org/avatar/8/%uuid%";




    @Description({ StringUtils.EMPTY, "# Wysyła poniższy dźwięk do każdej osoby na serwerze, wtedy gdy jakaś osoba wyśle coś na czacie!" })
    public boolean playerChatSoundEnabled = true;
    public Float playerChatVolume = 1.8F;
    public Float playerChatPitch = 1.0F;
    public Sound playerChatSound = XSound.BLOCK_NOTE_BLOCK_BIT.parseSound();

    @Description({ StringUtils.EMPTY, "# Jest to ficzer z inspiracji serwera vertez.pl, podczas poruszania scrollem na hotbarze wydobywa się dźwięk!" })
    public boolean playerHeldItemEnabled = true;
    public Float playerHeldItemVolume = 1.8F;
    public Float playerHeldItemPitch = 1.0F;
    public Sound playerHeldItemSound = XSound.BLOCK_NOTE_BLOCK_HAT.parseSound();

    @Description({ StringUtils.EMPTY, "# Ustawienia double jumpu!" })
    public boolean doubleJumpEnabled = true;
    public Sound doubleJumpSound = XSound.ENTITY_BAT_TAKEOFF.parseSound();
    public Float doubleJumpPower = 1.3F;
    public Float doubleJumpPowerY = 1.3F;
    public Float doubleJumpVolume = 1.8F;
    public Float doubleJumpPitch = 1.0F;

    @Description({ StringUtils.EMPTY, "# Opcje wiadomości wyświetlanej przy wejściu na serwer" })
    public boolean enableWelcomeMessage = true;
    public boolean welcomeMessageHeadDisplay = true;
    public boolean cleanAtJoin = true;
    public List<String> messageAfterJoin = Arrays.asList(
        "Exampelo Configu messagu",
        "Exampela masaga from configa message 2"
    );
}
