package com.carlostorres.showofftest.login

import com.carlostorres.client.domain.constants.FACEBOOK_ID
import com.carlostorres.client.domain.constants.INSTAGRAM_ID
import com.carlostorres.client.domain.constants.INSTAGRAM_NAME
import com.carlostorres.client.domain.interactors.CleanParameters
import com.carlostorres.client.domain.interactors.GetParameter
import com.carlostorres.client.domain.interactors.Profile
import com.carlostorres.client.domain.interactors.SaveParameter
import com.carlostorres.showofftest.core.ObservableViewModel
import com.carlostorres.showofftest.core.SingleLiveEvent
import javax.inject.Inject


class LoginViewModel @Inject constructor(
    private val cleanParameters: CleanParameters,
    private val saveParameter: SaveParameter,
    private val profile: Profile
) : ObservableViewModel() {

    val command: SingleLiveEvent<Command> = SingleLiveEvent()

    fun cleanPreferences() {
        cleanParameters.execute { either ->
            either.fold({}, {})
        }
    }


    fun saveParam(key: String, value: Any) {
        saveParameter.execute(SaveParameter.Params(key, value)) { either ->
            either.fold(
                {
                    command.postValue(Command.Error())
                },
                {
                    when (key) {
                        FACEBOOK_ID -> {
                            getProfile(value as String)
                        }
                        INSTAGRAM_ID -> {
                            command.postValue(Command.Authenticated)
                        }
                    }
                })
        }
    }

    fun getProfile(idFacebook: String) {
        profile.execute(Profile.Params(idFacebook)) { either ->
            either.fold(
                { failure ->
                    command.postValue(Command.Error())
                    notifyChange()
                },
                {
                    it.id?.let { it1 -> saveParam(INSTAGRAM_ID, it1) }
                    it.name?.let { it1 -> saveParam(INSTAGRAM_NAME, it1) }
                })
        }
    }

    sealed class Command {

        object Authenticated : Command()
        object Back : Command()
        class Error(val message: String? = null) : Command()

    }

}
