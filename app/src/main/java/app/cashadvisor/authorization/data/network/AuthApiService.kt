package app.cashadvisor.authorization.data.network

import app.cashadvisor.authorization.data.model.request.AuthByServiceRequest
import app.cashadvisor.authorization.data.model.request.AuthRequest
import app.cashadvisor.authorization.data.model.request.ConfirmEmailRequest
import app.cashadvisor.authorization.data.model.request.ResetPasswordByEmailRequest
import app.cashadvisor.authorization.data.model.request.ResetPasswordRequest
import app.cashadvisor.authorization.data.model.response.ConfirmEmailResponse
import app.cashadvisor.authorization.data.model.response.LoginByServiceResponse
import app.cashadvisor.authorization.data.model.response.LoginResponse
import app.cashadvisor.authorization.data.model.response.LogoutResponse
import app.cashadvisor.authorization.data.model.response.RegisterResponse
import app.cashadvisor.authorization.data.model.response.ResetPasswordByEmailResponse
import app.cashadvisor.authorization.data.model.response.ResetPasswordResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthApiService {
    @PUT("auth/login/reset/password/put")
    suspend fun putNewPasswordForReset(
        @Header(HEADER_NAME) token: String,
        @Body resetPasswordRequest: ResetPasswordRequest
    ): ResetPasswordResponse

    @POST("auth/login/reset/password")
    suspend fun resetPassword(
        @Body resetPasswordByEmailRequest: ResetPasswordByEmailRequest
    ): ResetPasswordByEmailResponse

    @POST("auth/reset/password/confirm")
    suspend fun confirmPasswordResetWithCode(
        @Body confirmEmailRequest: ConfirmEmailRequest
    ): ConfirmEmailResponse

    @POST("auth/login/google")
    suspend fun loginByGoogle(
        @Body authByServiceRequest: AuthByServiceRequest
    ): LoginByServiceResponse

    @POST("auth/login/vk")
    suspend fun loginByVk(
        @Body authByServiceRequest: AuthByServiceRequest
    ): LoginByServiceResponse

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    suspend fun loginByEmail(@Body authRequest: AuthRequest): LoginResponse

    @POST("auth/logout")
    suspend fun logout(): LogoutResponse

    @POST("auth/register")
    suspend fun register(@Body authRequest: AuthRequest): RegisterResponse

    @POST("auth/confirm-email")
    suspend fun confirmEmailWithCode(
        @Body confirmEmailRequest: ConfirmEmailRequest
    ): ConfirmEmailResponse

    companion object {
        const val HEADER_NAME = "Authorization"
    }
}