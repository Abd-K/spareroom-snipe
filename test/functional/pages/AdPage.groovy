package pages

class AdPage extends GrailsPage {
    static at = {title.contains(" Flatmate from SpareRoom") }
    static content = {
        emailButton {$("li.emailadvertiser > a.button")}
        earlyBirdBlueTextButton{$("a.bluebuttontextlink")}
        secondFreeToContactCofirmation {$("div.block_header > h3")}
        backToSearchResultsButton {$("p#back_to_search_results > a")}
    }

}
