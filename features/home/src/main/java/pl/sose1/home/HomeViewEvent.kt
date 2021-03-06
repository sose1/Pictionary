package pl.sose1.home

sealed class HomeViewEvent {
    object OnClickCreateButton : HomeViewEvent()
    object onClickInfoAboutCodeButton : HomeViewEvent()
    object ShowUserNameError : HomeViewEvent()
    object ShowInputFieldError : HomeViewEvent()
    object ShowNotFoundException : HomeViewEvent()
    object ShowTimeoutException : HomeViewEvent()
    class OpenLobby(val gameId: String, val userName: String) : HomeViewEvent()
}