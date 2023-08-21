package xyz.janek.betterthanauth.datasource

interface DataSource {
    fun isRegistered(username: String): Boolean

    fun getPassword(username: String): String

    fun saveCredentials(username: String, password: String): Boolean
}