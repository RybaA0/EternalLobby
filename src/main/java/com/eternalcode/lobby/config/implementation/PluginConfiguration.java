package com.eternalcode.lobby.config.implementation;

import net.dzikoysk.cdn.entity.Description;
import org.bukkit.Sound;
import panda.utilities.StringUtils;

import java.util.Arrays;
import java.util.List;

public class PluginConfiguration {


    @Description("# ")
    @Description("#  _____ _       EternalMC        _ __  __  ____ ")
    @Description("# | ____| |_ ___ _ __ _ __   __ _| |  \\/  |/ ___|")
    @Description("# |  _| | __/ _ \\ '__| '_ \\ / _` | | |\\/| | |    ")
    @Description("# | |___| ||  __/ |  | | | | (_| | | |  | | |___ ")
    @Description("# |_____|\\__\\___|_|  |_| |_|\\__,_|_|_|  |_|\\____|")
    @Description("# ")

    @Description({ StringUtils.EMPTY, "# Wysokość po której gracz zostaje teleportowany na spawn" })
    public int voidTeleportHeight = -5;

    @Description({ StringUtils.EMPTY, "# URL Do api zwracającego główkę gracza, w przypadku awarii możesz zmienić" })
    @Description("# zmienne: %name%, %uuid%")
    public String apiUrl = "https://minepic.org/avatar/8/%uuid%";


    @Description({ StringUtils.EMPTY, "# Wysyła poniższy dźwięk do każdej osoby na serwerze, wtedy gdy jakaś osoba wyśle coś na czacie!" })
    public boolean playerChatSoundEnabled = true;
    public Float playerChatVolume = 1.8F;
    public Float playerChatPitch = 1.0F;
    public Sound playerChatSound = Sound.BLOCK_NOTE_BLOCK_BIT;

    @Description({ StringUtils.EMPTY, "# Jest to ficzer z inspiracji serwera vertez.pl, podczas poruszania scrollem na hotbarze wydobywa się dźwięk!" })
    public boolean playerHeldItemEnabled = true;
    public Float playerHeldItemVolume = 1.8F;
    public Float playerHeldItemPitch = 1.0F;
    public Sound playerHeldItemSound = Sound.BLOCK_NOTE_BLOCK_HAT;

    @Description({ StringUtils.EMPTY, "# Ustawienia double jumpu!" })
    public boolean doubleJumpEnabled = true;
    public Sound doubleJumpSound = Sound.ENTITY_BAT_TAKEOFF;
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
