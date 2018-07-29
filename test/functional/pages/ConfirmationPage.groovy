package pages

class ConfirmationPage extends GrailsPage {

    static at = {title.contains("Your saved ads") || title.contains("Flatmate from SpareRoom") || title.contains("")}
    static content = {
        messageSuccessText {$("div.msg.success p")}
        interestSuccessText {$("p.msg.success")}
        goBackToSearchResultsFromInterest {$("#maincontent > ul > li:nth-child(1) > a")}
        goBackToSearchResultsFromMessage {$("#SUBPAGEdetails > header > div.base-header__wrap > nav.sub-nav.mySearch > ul > li:nth-child(1) > a")}
    }
}
