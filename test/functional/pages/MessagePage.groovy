package pages

class MessagePage extends GrailsPage {

    static at = { title.contains( "Flatmate from SpareRoom")}
    static content = {
        messageInputArea {$("div#messagefieldinput > textarea")}
        submitMessageButton {$("div.contact_form_submit input.button")}
        personName{$("#contact_advertiser_form > div.formrow > div:nth-child(2)")}
    }

}
