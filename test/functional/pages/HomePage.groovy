package pages

class HomePage extends GrailsPage {
    static at = { title == "SpareRoom for flatshare, house share, flat share & rooms for rent" }
    static content = {
        testTextSpan {$("#navBarTest > div > ul.nav-bar__menu.nav-bar__menu--left > li:nth-child(2) > a > span").text()}
        loginButton {$("div.authentication-links.authentication-links--buttons > a#show-user-auth-popup")}
        loginEmail(wait:true) {$("div.sign-in__email-wrapper div.sign-in__input-wrapper:nth-child(1) input.sign-in__input")}
        loginPassword(wait:true) {$("div.sign-in__email-wrapper div.sign-in__input-wrapper:nth-child(2) input.sign-in__input")}
        loginConfirmationButton(wait:true) {$("div.sign-in__button > button#sign-in-button")}
    }

}
