package app.cashadvisor.authorization.data.api

import app.cashadvisor.authorization.data.models.request.AuthByServiceRequest
import app.cashadvisor.authorization.data.models.request.AuthRequest
import app.cashadvisor.authorization.data.models.request.ConfirmEmailRequest
import app.cashadvisor.authorization.data.models.request.ConfirmPasswordResetRequest
import app.cashadvisor.authorization.data.models.request.ResetPasswordByEmailRequest
import app.cashadvisor.authorization.data.models.request.ResetPasswordRequest
import app.cashadvisor.authorization.data.models.request.ConfirmCodeRequest
import app.cashadvisor.authorization.data.models.response.AuthorizationResponse
import app.cashadvisor.authorization.data.models.response.ConfirmEmailResponse
import app.cashadvisor.authorization.data.models.response.ConfirmPasswordResetResponse
import app.cashadvisor.authorization.data.models.response.LoginByServiceResponse
import app.cashadvisor.authorization.data.models.response.LoginResponse
import app.cashadvisor.authorization.data.models.response.LogoutResponse
import app.cashadvisor.authorization.data.models.response.RegisterResponse
import app.cashadvisor.authorization.data.models.response.ResetPasswordByEmailResponse
import app.cashadvisor.authorization.data.models.response.ResetPasswordResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthApiService {
    @PUT("auth/login/reset/password/put")
    suspend fun putNewPasswordForReset(
        @Body resetPasswordRequest: ResetPasswordRequest
    ): ResetPasswordResponse

    @POST("auth/login/reset/password")
    suspend fun resetPassword(
        @Body resetPasswordByEmailRequest: ResetPasswordByEmailRequest
    ): ResetPasswordByEmailResponse

    @POST("auth/reset/password/confirm")
    suspend fun confirmPasswordResetWithCode(
        @Body confirmPasswordResetRequest: ConfirmPasswordResetRequest
    ): ConfirmPasswordResetResponse

    //TODO: заменить когда поправять на бэке
    @POST("auth/login/google")
    suspend fun loginByGoogle(
        @Body authByServiceRequest: AuthByServiceRequest
    ): LoginByServiceResponse

    //TODO: заменить когда поправять на бэке
    @POST("auth/login/vk")
    suspend fun loginByVk(
        @Body authByServiceRequest: AuthByServiceRequest
    ): LoginByServiceResponse

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    suspend fun loginByEmail(@Body authRequest: AuthRequest): LoginResponse

    @Headers("Content-Type: application/json")
    @POST("auth/login/confirm")
    suspend fun confirmLoginWithCode(
        @Body confirmCodeRequest: ConfirmCodeRequest
    ): AuthorizationResponse

    @POST("auth/logout")
    suspend fun logout(): LogoutResponse

    @Headers("Content-Type: application/json")
    @POST("auth/register")
    suspend fun register(@Body authRequest: AuthRequest): RegisterResponse

    @Headers("Content-Type: application/json")
    @POST("auth/register/confirm-email")
    suspend fun confirmEmailWithCode(
        @Body confirmEmailRequest: ConfirmEmailRequest
    ): ConfirmEmailResponse

}