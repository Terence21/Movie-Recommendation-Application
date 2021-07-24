package temple.edu.random.config

class AuthConfig {

    object ACCOUNT {
        enum class LOGIN_TYPE {
            EMAIL, GOOGLE
        }

        enum class VERIFICATION_TYPE {
            REGISTER, LOGIN
        }

        enum class AUTH {
            PASSED, FAILED
        }
    }
}