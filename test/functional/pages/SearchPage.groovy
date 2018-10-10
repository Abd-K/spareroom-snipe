package pages

class SearchPage extends GrailsPage {
    static url = "https://www.spareroom.co.uk/flatmate/flatmates.pl?search_id=723174148&"
    static content = {
        mainHeaderTitle {$("#mainheader")}
        adContactAvailabilityField (required: false) { nthChild -> $("ul.listing-results li:not([id]).listing-result:nth-child($nthChild) footer.status_container > span:nth-child(1) > span")}
        communicationStatus (required: false) { nthChild -> $("ul.listing-results li:not([id]).listing-result:nth-child($nthChild) footer.status_container > span.tooltip a > span")}
        moreInfoButton { nthChild -> $("#maincontent > ul > li:nth-child($nthChild) > article > header.desktop > a")}
        nextButton {$("ul.navnext > li > strong > a")}
    }
}
