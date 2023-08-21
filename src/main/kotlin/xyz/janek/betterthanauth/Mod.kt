package xyz.janek.betterthanauth

import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import xyz.janek.betterthanauth.datasource.DataSource
import xyz.janek.betterthanauth.datasource.SQLite


class Mod : ModInitializer {
    companion object {
        const val MODID = "betterthanauth"

        @JvmField
        var LOGGER: Logger = LoggerFactory.getLogger(MODID)

        @JvmField
        val dataSource: DataSource = SQLite()
    }

    override fun onInitialize() {
        LOGGER.info("Better Than Auth Initialized")
    }
}
