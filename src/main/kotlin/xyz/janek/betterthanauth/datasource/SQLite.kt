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

        TODO("Make it config")
        connection = DriverManager.getConnection(getJdbcUrl("better-than-auth", "default"))
    }

    fun setup() {
        val st = connection.createStatement()
        st.executeUpdate(
            "CREATE TABLE IF NOT EXISTS $tablename (" +
                    "id INTEGER AUTO_INCREMENT, " +
                    "name VARCHAR(16) NOT NULL, " +
                    "uuid VARCHAR(128), " +
                    "logged_in INT NOT NULL DEFAULT '0'" +
                    "password_hash VARCHAR(255), " +
                    "register_at TIMESTAMP NOT NULL, " +
                    "last_seen_at TIMESTAMP NOT NULL," +
                    "PRIMARY KEY(id, ASC)"
        )
        LOGGER.info("SQLite migrated")
    }

    private fun getJdbcUrl(path: String, database: String): String {
        return "jdbc:sqlite:$path${File.separator}$database.db"
    }

    override fun isRegistered(username: String): Boolean {
        TODO("do some error catching")
        val sql = "SELECT * FROM $tablename WHERE LOWER(name) = LOWER(?)"

        val stmt = connection.prepareStatement(sql)
        stmt.setString(1, username)
        val rs = stmt.executeQuery()

        return rs.next()
    }

    override fun getPassword(username: String): String {
        TODO("do some error catching")
        val sql = "SELECT password_hash FROM $tablename WHERE LOWER(name) = LOWER(?)"

        val stmt = connection.prepareStatement(sql)
        stmt.setString(1, username)
        val rs = stmt.executeQuery()

        rs.next()

        return rs.getString(0)
    }

    override fun saveCredentials(username: String, password: String): Boolean {
        TODO("please save those credentials")
    }
}