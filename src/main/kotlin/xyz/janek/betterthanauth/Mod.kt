package xyz.janek.betterthanauth

import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import xyz.janek.betterthanauth.datasource.DataSource
import xyz.janek.betterthanauth.datasource.SQLite
import xyz.janek.betterthanauth.utility.PasswordAuthentication


class Mod : ModInitializer {
    companion object {
        const val MODID = "betterthanauth"

        @JvmField
        var LOGGER: Logger = LoggerFactory.getLogger(MODID)

        @JvmField
        val dataSource: DataSource = SQLite()

        @JvmField
        val passwordAuthentication: PasswordAuthentication = PasswordAuthentication()
    }

    override fun onInitialize() {
        LOGGER.info("Better Than Auth Initialized")
        dataSource.setup()
    }
}
