package net.crystalix.teleport;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.crystalix.teleport.command.TeleportCommand;
import net.crystalix.teleport.command.TeleportIgnoreCommand;
import net.crystalix.teleport.command.cloud.PaperCommandSource;
import net.crystalix.teleport.command.cloud.PaperPlayerCommandSource;
import net.crystalix.teleport.util.IgnoreManager;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.translation.MiniMessageTranslationStore;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.util.UTF8ResourceBundleControl;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;
import org.jetbrains.annotations.NotNull;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.util.Locale;
import java.util.ResourceBundle;

public class TeleportPlugin extends JavaPlugin {

    private final IgnoreManager ignoreManager = new IgnoreManager(this);
    private final ObjectMapper mapper = JsonMapper.builder()
            .enable(SerializationFeature.INDENT_OUTPUT) // Pretty printing
            .build();

    @Override
    public void onLoad() {
        createTranslations();
    }

    @Override
    public void onEnable() {
        ignoreManager.readConfig();

        registerCommand();
        getLogger().info("TeleportPlugin wurde erfolgreich aktiviert");
    }

    @Override
    public void onDisable() {
        ignoreManager.saveAll();
    }

    private void createTranslations() {
        final MiniMessageTranslationStore store = MiniMessageTranslationStore.create(Key.key("magicmail:translations"));

        store.defaultLocale(Locale.GERMAN);
        store.registerAll(Locale.GERMAN, ResourceBundle.getBundle("messages", Locale.GERMANY, UTF8ResourceBundleControl.utf8ResourceBundleControl()), true);
        store.registerAll(Locale.ENGLISH, ResourceBundle.getBundle("messages", Locale.US, UTF8ResourceBundleControl.utf8ResourceBundleControl()), true);

        GlobalTranslator.translator().addSource(store);
    }

    private void registerCommand() {
        final PaperCommandManager<PaperCommandSource> commandManager = PaperCommandManager.builder(senderMapper())
                .executionCoordinator(ExecutionCoordinator.<PaperCommandSource>builder().build())
                .buildOnEnable(this);

        new TeleportCommand(this).registerTo(commandManager);
        new TeleportIgnoreCommand(this).registerTo(commandManager);
    }

    private @NotNull SenderMapper<CommandSourceStack, PaperCommandSource> senderMapper() {
        return SenderMapper.create(commandSourceStack -> {
            final CommandSender sender = commandSourceStack.getSender();
            return sender instanceof Player player ?
                    new PaperPlayerCommandSource(player, commandSourceStack) :
                    new PaperCommandSource(sender, commandSourceStack);

        }, PaperCommandSource::commandSourceStack);
    }

    public IgnoreManager ignoreManager() {
        return ignoreManager;
    }

    public ObjectMapper jsonMapper() {
        return mapper;
    }
}
