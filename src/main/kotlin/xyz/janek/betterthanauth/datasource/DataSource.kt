package xyz.janek.betterthanauth.datasource

interface DataSource {
    fun setup()

    fun isRegistered(username: String): Boolean

    fun getPasswordHash(username: String): String

    fun saveCredentials(username: String, passwordHash: String): Boolean
}