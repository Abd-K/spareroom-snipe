import geb.error.RequiredPageContentNotPresent
import geb.spock.GebSpec
import org.codehaus.groovy.grails.commons.DefaultGrailsApplication
import pages.AdPage
import pages.ConfirmationPage
import pages.HomePage
import pages.MessagePage
import pages.MyAccountPage
import pages.MyMessagesPage
import pages.SearchPage
import java.util.logging.Level
import java.util.logging.Logger

class MessageSpec extends GebSpec{
    def grailsApplication = new DefaultGrailsApplication()

    final String EARLY_BIRD_TEXT = "early bird"
    final String IM_INTERESTED_TEXT = "i'm interested"
    final String SAVE_TEXT = "save"
    final String FREE_TO_CONTACT_TEXT = "free to contact"
    final String CONTACTED_TEXT = "contacted"
    final int LIST_SIZE = 21

    String username
    String password

    Logger logger = Logger.getLogger("")

    final String MAIN_SEARCH_PAGE_HEADER_TITLE = "Flatmates in SE8 from £675 up to £1100"

    final String EARLY_BIRD_INTEREST_SUCCESS = "Your interest has been sent to the advertiser, and the advert saved to your 'saved ads'"
    final String MESSAGE_SUCCESS = "Thank you - your email has been sent. Please be patient when waiting for a response."

    def setup() {
        username = grailsApplication.config.username
        password = grailsApplication.config.password
    }

    def "remessage corect price"() {
        when:
            try {
                HomePage homePage = to HomePage
                doLogin(homePage)
                waitFor { at MyAccountPage}
            } catch(Exception e) {
                HomePage homePage = to HomePage
                doLogin(homePage)
                waitFor { at MyAccountPage}
            }
            MyMessagesPage myMessagesPage = to MyMessagesPage
//            loopThroughMessagesListAndSend(myMessagesPage)
        then:
            true
    }

    def loopThroughMessagesListAndSend(MyMessagesPage myMessagesPage) {
        1.upto(1) { it ->
            String message = "Hi again, " +
                    "\nSorry the room that's available is for £675 and not £800!\n" +
                    "\nAnna"
            myMessagesPage.sentMessage(it).click()
            waitFor { myMessagesPage.manualReply.isDisplayed()}
            try{myMessagesPage.manualReply.isDisplayed()
                myMessagesPage.manualReply.click()
                waitFor {myMessagesPage.sendButton.isDisplayed()}
                myMessagesPage.textArea.value(message)
                myMessagesPage.sendButton.isDisplayed()
                myMessagesPage.sendButton.click()
            } catch(RequiredPageContentNotPresent e) {}

            to myMessagesPage

        }
    }

    def "go to present search page" () {
        given:
            String searchId = ""
            String loginDetails
        when:
        try {
            HomePage homePage = to HomePage
            doLogin(homePage)
            waitFor { at MyAccountPage}
        } catch(Exception e) {
            HomePage homePage = to HomePage
            doLogin(homePage)
            waitFor { at MyAccountPage}
        }
            SearchPage searchPage = to SearchPage
        then:
            searchPage.mainHeaderTitle.text().trim() == MAIN_SEARCH_PAGE_HEADER_TITLE
        when:'step through each item in result and message or show interest if possible'
            loopThroughItemsInPage(searchPage)
        then:
            true
    }

    def loopThroughItemsInPage(int listSize = LIST_SIZE, SearchPage searchPage, int pageIndex = 1 ) {
        String adContactAvailabilityField, communicationStatus
        int nthChild
        try {
            listSize.times {
                nthChild = it + 1

                adContactAvailabilityField = searchPage.adContactAvailabilityField(nthChild).text()?.trim()?.toLowerCase()
                communicationStatus = searchPage.communicationStatus(nthChild).text()?.trim()?.toLowerCase()

                //is free to contact
                if (adContactAvailabilityField.equals(FREE_TO_CONTACT_TEXT)) {
                    //is not contacted
                    if (!communicationStatus.equals(CONTACTED_TEXT)) {
                        //message
                        searchPage.moreInfoButton(nthChild).click()

                        AdPage adPage = at AdPage
                        if (adPage.secondFreeToContactCofirmation.text().toLowerCase().contains(FREE_TO_CONTACT_TEXT)) {

                            waitFor { adPage.emailButton.isDisplayed() }
                            adPage.emailButton.click()

                            MessagePage messagePage = at MessagePage

                            String name = messagePage.personName.text().trim().split(" ")[0]
                            String messageText = "Hi $name," +
                                    "\nWe are a couple looking for someone to share with us our 2 bedroom flat, from the 1st of November\n" +
                                    "It’s ideal if you’re looking to move somewhere that’s not overcrowded, yet pay a low rent.\n" +
                                    "\n" +
                                    "The room is a spacious double room, it’s very close to Surrey quays + Canada Water station and Tesco/Lidl and shopping center.\n" +
                                    "\n" +
                                    "We have a small cat living with us, I hope you like cats, if you do, please get in touch!\n" +
                                    "\n" +
                                    "Please checkout my ad if you’re interested, and we can arrange a viewing soon.\n" +
                                    "\n" +
                                    "Thanks,\n" +
                                    "Anna "

                            messagePage.messageInputArea.value(messageText)

                            messagePage.submitMessageButton.click()

                            ConfirmationPage confirmationPage = at ConfirmationPage
                            waitFor { confirmationPage.messageSuccessText.isDisplayed() }
                            confirmationPage.messageSuccessText.text().equals(MESSAGE_SUCCESS)
                            confirmationPage.goBackToSearchResultsFromMessage.click()
                            logger.log(Level.INFO,"sent message for page index:$pageIndex, add number:$nthChild ")
                        } else {
                            adPage.backToSearchResultsButton.click()
                        }
                    }
                } else if (adContactAvailabilityField.equals(EARLY_BIRD_TEXT)) { //is early bird
                    //set interested is displayed
                    if (communicationStatus.equals(SAVE_TEXT)) {
                        //set to interested
                        searchPage.moreInfoButton(nthChild).click()
                        AdPage adPage = at AdPage
                        waitFor { adPage.earlyBirdBlueTextButton.isDisplayed() }
                        adPage.earlyBirdBlueTextButton.click()

                        //confirm message shown
                        ConfirmationPage confirmationPage = at ConfirmationPage
                        confirmationPage.interestSuccessText.text().trim() == EARLY_BIRD_INTEREST_SUCCESS
                        confirmationPage.goBackToSearchResultsFromInterest.click()
                        logger.log(Level.INFO, "show interest for page index:$pageIndex, add number:$nthChild")
                    }
                }
            }
        } catch (RequiredPageContentNotPresent e) {
                logger.log(Level.WARNING,"failed $e, page index:$pageIndex, add number:$nthChild")

                searchPage = to SearchPage
                (pageIndex - 1).times {
                    waitFor{searchPage.nextButton.isDisplayed()}

                    searchPage.nextButton.click()
                }
        }
        try {
            pageIndex++
            waitFor{searchPage.nextButton.isDisplayed()}
            searchPage.nextButton.click()
            loopThroughItemsInPage(LIST_SIZE, searchPage, pageIndex)
        } catch (RequiredPageContentNotPresent e) {
            logger.log(Level.WARNING,"failed $e, page index:$pageIndex, add number:$nthChild")
            searchPage = to SearchPage
            (pageIndex).times {
                waitFor{searchPage.nextButton.isDisplayed()}

                searchPage.nextButton.click()
                loopThroughItemsInPage(LIST_SIZE, searchPage, pageIndex)
            }
        }
    }

    def doLogin(HomePage homePage) {
        homePage.loginButton.click()
        waitFor { homePage.loginEmail.isDisplayed()}
        waitFor { homePage.loginPassword.isDisplayed()}
        waitFor { homePage.loginConfirmationButton.isDisplayed()}
        homePage.loginEmail.value(username)
        homePage.loginPassword.value(password)
        homePage.loginConfirmationButton.click()
    }
}