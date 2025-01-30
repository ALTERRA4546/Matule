package com.example.matule

import android.provider.ContactsContract.Contacts.Photo
import com.fasterxml.jackson.annotation.Nulls
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.providers.builtin.OTP
import io.github.jan.supabase.auth.user.UserSession
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.serializer.JacksonSerializer
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class SupabaseManager {

    companion object {
        var userMail: String = ""
        var userPassword: String = ""
    }

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
            }

            val user = supabase.auth.sessionManager.loadSession()?.user?.id
            val newUser = profile(user!!, first_name = Name, last_name = null, address = null, phone = null, phone_region = 0, email = Mail, password = Password, photo = null)
            supabase.from("profile").insert(newUser)

            userMail = Mail
            userPassword = Password

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

            userMail = Mail
            userPassword = Password

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

    suspend fun getProfile() : profile {
        var userData = supabase.from("profile").select() {
            filter {
                profile::email eq userMail
                profile::password eq userPassword
            }
        }.decodeSingle<profile>()

        return userData
    }

    suspend fun updateUserProfile(Name: String, Surname: String?, Address: String?, Phone: String?, PhoneRegion: Int) {
        val user = supabase.auth.sessionManager.loadSession()?.user?.id
        val newUser = profile(user!!, first_name = Name, last_name = Surname, address = Address, phone = Phone, phone_region = PhoneRegion, email = userMail, password = userPassword, photo = null)

        supabase.from("profile").update(newUser)
        {
            filter {
                profile::id eq user
            }
        }
    }

    suspend fun updateUserProfileMain(UserName: String, Mail: String, Password: String) {
        val user = supabase.auth.sessionManager.loadSession()?.user?.id

        var userCurrentData = getProfile()

        var userNameSplit = UserName.split(" ")

        val newUser = profile(user!!, first_name = userNameSplit[0], last_name = userNameSplit[1], address = userCurrentData.address, phone = userCurrentData.phone, phone_region = userCurrentData.phone_region, email = Mail, password = userCurrentData.password, photo = userCurrentData.photo)

        supabase.auth.updateUser {
            email = Mail
        }

        supabase.from("profile").update(newUser)
        {
            filter {
                profile::id eq user
            }
        }
    }

    data class profile (
        val id: String,
        val first_name: String,
        val last_name: String?,
        val address: String?,
        val phone: String?,
        val phone_region: Int,
        val email: String,
        val password: String,
        val photo: String?,
    )
}