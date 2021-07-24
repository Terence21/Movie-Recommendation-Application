package temple.edu.random.activities.account.authConfig

class AuthHelper {
    companion object {
        fun confirmHardPassword(password: String, password2: String) = password == password2
        fun countPasswordSize(password: String) = password.length
        fun isEmail(email: String) {
            // [anything num-letter]@[anything num-letter].com
            fun validEmailFormat(email: String) =
                email.matches(Regex("([a-zA-Z0-9])\\w+\\@([a-zA-Z0-9])\\w+.com"))

            // 5+ characters long
            // both numbers and characters
            fun validPasswordFormat(password: String) {

            }
        }
    }
}