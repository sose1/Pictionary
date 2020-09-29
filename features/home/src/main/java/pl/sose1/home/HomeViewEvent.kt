package pl.sose1.home

sealed class HomeViewEvent {
    object OnClickCreateButton : HomeViewEvent()
    object OnClickPositiveButton : HomeViewEvent()
    object ShowUserNameError : HomeViewEvent()
    object OnClickConnectButton : HomeViewEvent()
    object ShowInputFieldError : HomeViewEvent()
}