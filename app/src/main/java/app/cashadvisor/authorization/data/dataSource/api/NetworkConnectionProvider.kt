package app.cashadvisor.authorization.data.dataSource.api

interface NetworkConnectionProvider {
    fun isConnected(): Boolean
}