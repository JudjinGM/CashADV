package app.cashadvisor.authorization.presentation.viewmodel.models

sealed class EntryInteractionState {
    object SignUpTapped : EntryInteractionState()
    object SignInTapped : EntryInteractionState()
}