package mods.betterfoliage.client

import mods.betterfoliage.BetterFoliageMod
import mods.betterfoliage.client.gui.ConfigGuiFactory
import mods.betterfoliage.client.integration.*
import mods.betterfoliage.client.render.*
import mods.betterfoliage.client.texture.*
import mods.octarinecore.client.KeyHandler
import mods.octarinecore.client.resource.CenteringTextureGenerator
import mods.octarinecore.client.resource.GeneratorPack
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.client.FMLClientHandler
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import org.apache.logging.log4j.Level

/**
 * Object responsible for initializing (and holding a reference to) all the infrastructure of the mod
 * except for the call hooks.
 *
 * This and all other singletons are annotated [SideOnly] to avoid someone accidentally partially
 * initializing the mod on a server environment.
 */
@SideOnly(Side.CLIENT)
object Client {

    val configKey = KeyHandler(BetterFoliageMod.MOD_NAME, 66, "key.betterfoliage.gui") {
        FMLClientHandler.instance().showGuiScreen(
            ConfigGuiFactory.ConfigGuiBetterFoliage(Minecraft.getMinecraft().currentScreen)
        )
    }

    val genGrass = GrassGenerator("bf_gen_grass")
    val genLeaves = LeafGenerator("bf_gen_leaves")
    val genReeds = CenteringTextureGenerator("bf_gen_reeds", 1, 2)

    val generatorPack = GeneratorPack(
        "Better Foliage generated",
        genGrass,
        genLeaves,
        genReeds
    )

    val logRenderer = RenderLog()

    val renderers = listOf(
        RenderGrass(),
        RenderMycelium(),
        RenderLeaves(),
        RenderCactus(),
        RenderLilypad(),
        RenderReeds(),
        RenderAlgae(),
        RenderCoral(),
        logRenderer,
        RenderNetherrack(),
        RenderConnectedGrass(),
        RenderConnectedGrassLog()
    )

    val singletons = listOf(
        LeafRegistry,
        GrassRegistry,
        LeafWindTracker,
        RisingSoulTextures,
        ShadersModIntegration,
        OptifineCTM,
        ForestryIntegration,
        IC2Integration,
        TechRebornIntegration,
        StandardLogSupport          // add _after_ all other log registries
    )

    fun log(level: Level, msg: String) {
        BetterFoliageMod.log.log(level, "[BetterFoliage] $msg")
        BetterFoliageMod.logDetail.log(level, msg)
    }

    fun logDetail(msg: String) {
        BetterFoliageMod.logDetail.log(Level.DEBUG, msg)
    }
}

