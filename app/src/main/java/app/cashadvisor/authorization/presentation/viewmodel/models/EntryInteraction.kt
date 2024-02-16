package app.cashadvisor.authorization.presentation.viewmodel.models

sealed class EntryInteraction {
    object SignUpTapped : EntryInteraction()
    object SignInTapped : EntryInteraction()
}