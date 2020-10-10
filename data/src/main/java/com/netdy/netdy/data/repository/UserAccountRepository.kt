package com.netdy.netdy.data.repository

import android.content.Context
import com.netdy.netdy.data.ErrorValue
import com.netdy.netdy.data.R
import com.netdy.netdy.data.TAG
import com.netdy.netdy.data.mapper.ParseUserMapper
import com.netdy.netdy.domain.entity.UserAccount
import com.netdy.netdy.domain.repository.UserAccountRepository
import com.netdy.netdy.domain.usecase.base.SynchronousUseCase
import com.parse.ParseUser
import io.reactivex.Completable
import io.reactivex.Single

class UserAccountRepository(
    private val context: Context,
    private val parseUserMapper: ParseUserMapper
) : UserAccountRepository {

    override fun getCurrentUserAccountSync(callback: SynchronousUseCase.Callback<UserAccount>) {
        val currentUser = ParseUser.getCurrentUser()
        if (currentUser != null) {
            callback.onSuccess(parseUserMapper.transform(currentUser))
        } else {
            callback.onError(
                ErrorValue.CurrentUserNotFound(
                    TAG,
                    context.getString(R.string.current_user_not_found)
                )
            )
        }
    }

    override fun signIn(userName: String, password: String): Single<UserAccount> {
        return Single.create { emitter ->
            ParseUser.logInInBackground(userName, password) { user, e ->
                if (user != null) emitter.onSuccess(parseUserMapper.transform(user))
                else emitter.onError(
                    ErrorValue.CanNotSignIn(
                        TAG,
                        context.getString(R.string.can_not_sign_in),
                        e
                    )
                )
            }
        }
    }

    override fun signUp(
        userName: String,
        password: String,
        firstName: String,
        lastName: String
    ): Single<UserAccount> {
        return Single.create { emitter ->
            val parseUser = ParseUser()
            parseUser.username = userName
            parseUser.email = userName
            parseUser.setPassword(password)
            parseUser.put("firstName", firstName)
            parseUser.put("lastName", lastName)

            parseUser.signUpInBackground { e ->
                if (e == null) emitter.onSuccess(parseUserMapper.transform(parseUser))
                else emitter.onError(
                    ErrorValue.CanNotSignUp(
                        TAG,
                        context.getString(R.string.can_not_sign_up),
                        e
                    )
                )
            }
        }
    }

    override fun signOut(): Completable {
        return Completable.create { emitter ->
            ParseUser.logOutInBackground { e ->
                if (e == null) emitter.onComplete()
                else emitter.onError(
                    ErrorValue.CanNotSignOut(
                        TAG,
                        context.getString(R.string.can_not_sign_out),
                        e
                    )
                )
            }
        }
    }
}