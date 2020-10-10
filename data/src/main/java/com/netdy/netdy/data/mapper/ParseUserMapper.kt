package com.netdy.netdy.data.mapper

import com.netdy.netdy.domain.entity.UserAccount
import com.parse.ParseUser

class ParseUserMapper {

    fun transform(parseUser: ParseUser): UserAccount {
        return UserAccount(
            id = parseUser.objectId,
            userName = parseUser.username,
            email = parseUser.email ?: "",
            firstName = parseUser.getString("firstName") ?: "",
            lastName = parseUser.getString("lastName") ?: ""
        )
    }
}