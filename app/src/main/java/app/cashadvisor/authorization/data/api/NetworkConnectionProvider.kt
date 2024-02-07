package app.cashadvisor.authorization.data.api

interface NetworkConnectionProvider {
    fun isConnected(): Boolean
}