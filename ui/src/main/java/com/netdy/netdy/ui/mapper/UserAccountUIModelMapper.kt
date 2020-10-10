package com.netdy.netdy.ui.mapper

import com.netdy.netdy.domain.entity.UserAccount
import com.netdy.netdy.ui.model.UserAccountUIModel

class UserAccountUIModelMapper {

    fun transform(userAccount: UserAccount): UserAccountUIModel {

        return UserAccountUIModel(
            id = userAccount.id,
            userName = userAccount.userName,
            email = userAccount.email,
            firstName = userAccount.firstName,
            lastName = userAccount.lastName
        )
    }
}