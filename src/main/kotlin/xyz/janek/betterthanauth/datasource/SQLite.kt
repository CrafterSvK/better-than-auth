package xyz.janek.betterthanauth.datasource

import xyz.janek.betterthanauth.Mod.Companion.LOGGER
import java.io.File
import java.sql.Connection
import java.sql.DriverManager


class SQLite : DataSource {
    private var tablename: String = "users"
    private lateinit var connection: Connection

    init {
        connect()
    }

    private fun connect() {
        try {
            Class.forName("org.sqlite.JDBC")
        } catch (e: ClassNotFoundException) {
            throw IllegalStateException("Failed to load SQLite JDBC class", e)
        }

        connection = DriverManager.getConnection(getJdbcUrl("mods/better-than-auth", "default"))
    }

    override fun setup() {
        val st = connection.createStatement()
        st.executeUpdate(
            "CREATE TABLE IF NOT EXISTS $tablename (" +
                    "id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(16) NOT NULL, " +
                    "uuid VARCHAR(128), " +
                    "logged_in INTEGER NOT NULL DEFAULT '0', " +
                    "password_hash VARCHAR(255), " +
                    "register_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                    "last_seen_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)"
        )
        LOGGER.info("SQLite migrated")
    }

    private fun getJdbcUrl(path: String, database: String): String {
        return "jdbc:sqlite:$path${File.separator}$database.db"
    }

    override fun isRegistered(name: String): Boolean {
        val sql = "SELECT * FROM $tablename WHERE LOWER(name) = LOWER(?)"

        val stmt = connection.prepareStatement(sql)
        stmt.setString(1, name)
        val rs = stmt.executeQuery()

        return rs.next()
    }

    override fun getPasswordHash(name: String): String {
        val sql = "SELECT password_hash FROM $tablename WHERE LOWER(name) = LOWER(?)"

        val stmt = connection.prepareStatement(sql)
        stmt.setString(1, name)
        val rs = stmt.executeQuery()

        rs.next()

        return rs.getString(1)
    }

    override fun saveCredentials(name: String, passwordHash: String): Boolean {
        val sql = "INSERT INTO $tablename (name, password_hash) VALUES (?, ?)"

        val stmt = connection.prepareStatement(sql)
        stmt.setString(1, name)
        stmt.setString(2, passwordHash)
        stmt.executeUpdate()

        return true
    }
}