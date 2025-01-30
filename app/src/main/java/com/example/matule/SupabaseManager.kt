package com.example.matule

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.providers.builtin.OTP
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.serializer.JacksonSerializer
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class SupabaseManager {

    val supabase = createSupabaseClient(
        supabaseUrl = "",
        supabaseKey = ""
    )
    {
        install(Auth)
        install(Postgrest)
        defaultSerializer = JacksonSerializer()
    }

    suspend fun singUp(Name: String, Mail: String, Password: String): Result<Unit> {
        return try {
            supabase.auth.signUpWith(Email) {
                email = Mail
                password = Password
                data = buildJsonObject {
                    put("first_name", Name)
                }
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun singIn(Mail: String, Password: String): Result<Unit> {
        return try {
            supabase.auth.signInWith(Email) {
                email = Mail
                password = Password
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun forgotPassword (Mail: String): Result<Unit> {
        return try {
            supabase.auth.signInWith(OTP) {
                email = Mail
            }
            //supabase.auth.verifyEmailOtp(type = OtpType.Email.EMAIL, email = Mail, token = Otp)

            Result.success(Unit)
        }
        catch (e: Exception)
        {
            Result.failure(e)
        }
    }

    suspend fun otpSingIn (Mail: String, Otp: String): Result<Unit> {
        return try {
            supabase.auth.verifyEmailOtp(type = OtpType.Email.EMAIL, email = Mail, token = Otp)

            Result.success(Unit)
        }
        catch (e: Exception)
        {
            Result.failure(e)
        }
    }

    suspend fun resetPassword (Password: String) : Result<Unit> {
        return try {
            supabase.auth.updateUser {
                password = Password
            }

            Result.success(Unit)
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getProfile():profile {
        var user = supabase.from("profile").select(Columns.list("first_name", "last_name", "address", "phone")).decodeSingle<profile>()

        return user
    }

    suspend fun updateUserProfile(Name: String, Surname: String, Address: String, Phone: String) {
        supabase.auth.updateUser {
            "first_name" to Name
            "last_name" to Surname
            "address" to Address
            "phone" to Phone
        }
    }

    data class profile (
        val first_name: String,
        val last_name: String,
        val address: String,
        val phone: String
    )
}