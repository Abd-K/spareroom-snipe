package pages

class MyMessagesPage extends GrailsPage{
    static url = "https://www.spareroom.co.uk/flatshare/mythreads_beta.pl?offset=700&listing_restrict=&show_deleted_too=&max_per_page=&label_id=&only_unreplied=&folder=sent&sort="
    static content = {

        sentMessage { nthChild -> $("div#maincontent form:nth-child(3) div.msg_row:nth-child($nthChild) a") }
        manualReply{ $("div.message_options.reply_reject form:nth-child(1) button")}

        sendButton { $("div.form_input.form_button button.primary-standard")}
        textArea {$("textarea#reply")}
    }
}
