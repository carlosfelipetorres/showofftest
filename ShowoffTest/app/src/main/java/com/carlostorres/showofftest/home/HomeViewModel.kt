package com.carlostorres.showofftest.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.carlostorres.client.domain.constants.INSTAGRAM_ID
import com.carlostorres.client.domain.constants.INSTAGRAM_NAME
import com.carlostorres.client.domain.interactors.CleanParameters
import com.carlostorres.client.domain.interactors.GetParameter
import com.carlostorres.client.domain.interactors.Posts
import com.carlostorres.client.domain.interactors.SaveParameter
import com.carlostorres.client.domain.models.Post
import com.carlostorres.showofftest.core.ObservableViewModel
import com.carlostorres.showofftest.core.SingleLiveEvent
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val getParameter: GetParameter,
    private val cleanParameters: CleanParameters,
    private val posts: Posts
) : ObservableViewModel() {

    val command: SingleLiveEvent<Command> = SingleLiveEvent()
    lateinit var postsList: MutableLiveData<List<Post>>
    var outputIsRefreshing = false
    var name = ""

    fun getParam(key: String, type: String? = null) {
        getParameter.execute(GetParameter.Params(key, type)) { either ->
            either.fold(
                { failure ->
                    command.postValue(Command.Error())
                    notifyChange()
                }, {
                    when (key) {
                        INSTAGRAM_NAME -> {
                            name = it as String
                            command.postValue(Command.NameObtained)
                        }
                    }
                })
        }
    }

    fun getPostsLists(): LiveData<List<Post>> {
        if (!::postsList.isInitialized) {
            postsList = MutableLiveData()
            getPosts()
        }
        return postsList
    }

    private fun getPosts() {
        outputIsRefreshing = true
        notifyChange()
        posts.execute(Posts.Params(null)) { either ->
            either.fold(
                { failure ->
                    command.postValue(Command.Error())
                    outputIsRefreshing = false
                    notifyChange()
                }, { result ->
                    outputIsRefreshing = false
                    postsList.postValue(result)
                    notifyChange()
                })
        }
    }

    fun closeSession() {
        cleanParameters.execute { either ->
            either.fold({}, {
                command.postValue(Command.SessionClosed)
            })
        }
    }

    sealed class Command {
        object SessionClosed : Command()
        object NameObtained : Command()
        class Error(val message: String? = null) : Command()

    }

}
