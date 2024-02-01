package app.cashadvisor.authorization.domain.api

import app.cashadvisor.authorization.data.dto.CredentialsDto

interface CredentialsStorage {

    fun saveCredentials(credentials: CredentialsDto)
    fun getCredentials(): CredentialsDto?
    fun hasCredentials(): Boolean
    fun clearCredentials()

}