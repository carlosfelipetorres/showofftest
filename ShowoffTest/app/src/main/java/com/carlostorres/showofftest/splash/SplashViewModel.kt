package com.carlostorres.showofftest.splash

import com.carlostorres.client.domain.constants.ACCESS_TOKEN
import com.carlostorres.showofftest.core.ObservableViewModel
import com.carlostorres.showofftest.core.SingleLiveEvent
import com.carlostorres.client.domain.interactors.GetParameter
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val getParameter: GetParameter
) : ObservableViewModel() {

    val command: SingleLiveEvent<Command> = SingleLiveEvent()
    var outputIsRefreshing = false
    var token = ""

    fun getParam(key: String, type: String? = null) {
        outputIsRefreshing = true
        getParameter.execute(GetParameter.Params(key, type)) { either ->
            either.fold(
                { failure ->
                    outputIsRefreshing = false
                    command.postValue(Command.Error())
                    notifyChange()
                },
                { result ->
                    outputIsRefreshing = false
                    when(key) {
                        ACCESS_TOKEN -> {
                            token = result as String
                            command.postValue(Command.UserToken)
                        }
                    }
                })
        }
    }

    sealed class Command {

        object UserToken : Command()
        class Error(val message: String? = null) : Command()

    }

}
