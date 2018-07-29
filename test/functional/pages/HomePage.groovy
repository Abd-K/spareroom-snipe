package pages

class HomePage extends GrailsPage {
    static at = { title == "SpareRoom for flatshare, house share, flat share & rooms for rent" }
    static content = {
        testTextSpan {$("#navBarTest > div > ul.nav-bar__menu.nav-bar__menu--left > li:nth-child(2) > a > span").text()}
        loginButton {$("#header > div > div.authentication-links > a.authentication-links__sign-in-link")}
        loginEmail(wait:true) {$("input#loginemail")}
        loginPassword(wait:true) {$("input#loginpass")}
        loginConfirmationButton(wait:true) {$("input#loginbutton_popUpBox")}
    }

}
