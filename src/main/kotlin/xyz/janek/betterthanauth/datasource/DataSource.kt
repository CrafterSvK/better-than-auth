package xyz.janek.betterthanauth.datasource

interface DataSource {
    fun setup()

    fun isRegistered(name: String): Boolean

    fun getPasswordHash(name: String): String

    fun saveCredentials(name: String, passwordHash: String): Boolean
}