package com.example.emptyactivity.login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emptyactivity.Auth.AuthRepository
import kotlinx.coroutines.launch

/**
 * viewModel class for handling login functionality
 * @param repository responsible for authentication operations
 */
class LoginViewModel(private val repository: AuthRepository = AuthRepository()): ViewModel(){

    // current user information from the authentication repository
    val currentUser = repository.currentUser

    // check if a user is already authenticated
    val hasUser: Boolean
        get() = repository.hasUser()


    // state variable for the login UI
    var loginUiState by mutableStateOf(LoginUiState())
        private set


    // to change the state of the listed variables in the sign up UI
    fun onUsernameChange(username: String){
        loginUiState = loginUiState.copy(username = username)
    }

    fun onPasswordChange(password: String){
        loginUiState = loginUiState.copy(password = password)
    }

    fun onUsernameSignUpChange(username: String){
        loginUiState = loginUiState.copy(usernameSignUp = username)
    }

    fun onPasswordSignUpChange(password: String){
        loginUiState = loginUiState.copy(passwordSignUp = password)
    }

    fun onConfirmPasswordChange(password: String){
        loginUiState = loginUiState.copy(confirmPasswordSignUp = password)
    }

    // to change the state of the user interface ^


    /**
     * function to validate the login form
     */
    private fun validateLoginForm() =
        loginUiState.username.isBlank() && loginUiState.password.isBlank()


    /**
     * function to validate the sign up form
     */
    private fun validateSignUpForm() =
        loginUiState.usernameSignUp.isNotBlank() && loginUiState.passwordSignUp.isNotBlank() && loginUiState.confirmPasswordSignUp.isNotBlank()


    /**
     * function to create a new user
     */
    fun createUser(context: Context) = viewModelScope.launch {
        try {
            if (!validateSignUpForm()) {
                throw IllegalArgumentException("email and password cannot be empty")
            }

            loginUiState = loginUiState.copy(isLoading = true)

            if(loginUiState.passwordSignUp != loginUiState.confirmPasswordSignUp){
                throw IllegalArgumentException("passwords do not match")
            }
            loginUiState = loginUiState.copy(signUpError = null)

            repository.createUser(
                loginUiState.usernameSignUp,
                loginUiState.passwordSignUp
            ) { isSuccessful ->
                if (isSuccessful) {
                    Toast.makeText(context, "success login", Toast.LENGTH_SHORT).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = true)
                }
                else {
                    Toast.makeText(context, "failed login", Toast.LENGTH_SHORT).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = false)
                }
            }

        } catch (e:Exception){
            loginUiState = loginUiState.copy(signUpError = e.localizedMessage)
            e.printStackTrace()
        } finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }

    // function to validate the login form
    fun loginUser(context: Context) = viewModelScope.launch {
        try {
            if (validateLoginForm()) {
                throw IllegalArgumentException("email and password cannot be empty")
            }


            loginUiState = loginUiState.copy(isLoading = true)


            loginUiState = loginUiState.copy(loginError = null)

            repository.login(
                loginUiState.username,
                loginUiState.password
            ) { isSuccessful ->
                if (isSuccessful) {
                    Toast.makeText(context, "success login", Toast.LENGTH_SHORT).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = true)
                    loginUiState.username = "";
                    loginUiState.password = "";

                }
                else {
                    Toast.makeText(context, "failed login", Toast.LENGTH_SHORT).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = false)
                }
            }

        } catch (e:Exception){
            loginUiState = loginUiState.copy(loginError = e.localizedMessage)
            e.printStackTrace()
        } finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }

    // state class for the login user int.
    data class LoginUiState(
        var username: String = "",
        var password: String = "",
        val usernameSignUp: String = "",
        val passwordSignUp: String = "",
        val confirmPasswordSignUp: String = "",
        val isLoading: Boolean = false,
        val isSuccessLogin: Boolean = false,
        val signUpError:String? = null,
        val loginError: String? = null,


        )

}
