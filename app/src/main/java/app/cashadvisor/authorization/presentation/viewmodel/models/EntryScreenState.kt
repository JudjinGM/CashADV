package app.cashadvisor.authorization.presentation.viewmodel.models

sealed class EntryScreenState {

    object Default : EntryScreenState()
    object SignIn : EntryScreenState()
    object SignUp : EntryScreenState()

}