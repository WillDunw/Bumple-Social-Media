package com.example.emptyactivity.navigation

/**
 * sealed class representing different screens/routes in the application.
 * @param route the unique identifier for the route associated with each screen.
 */
sealed class Routes(val route: String) {
    /**
     * represents the create post screen.
     */
    object CreatePost: Routes("CreatePostScreen")
    /**
     * represents the home screen.
     */
    object Home : Routes("HomeScreen")
    /**
     * represents the account screen.
     */
    object Account : Routes("AccountScreen")
    /**
     * represents the signup screen.
     */
    object SignUp : Routes("SignUpScreen")
    /**
     * represents the login screen.
     */
    object Login : Routes("LoginRoutes")
    /**
     * represents the view other account screen.
     */
    object ViewOtherAccount : Routes("ViewOtherAccount")
    /**
     * represents the view following screen.
     */
    object ViewFollowing : Routes("ViewFollowing")
    /**
     * represents the view follower screen.
     */
    object ViewFollower : Routes("ViewFollower")
}