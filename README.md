# androidApp

Math program

-Has been compiled/run in Android studio

-Currently only a mobile app exists, no android tv app even though there's a folder for it.


Description:

Solve math programs for ten seconds (amount of time can be adjusted in the code)

Keeps track of questions solved correctly/incorrectly, current score, and also amount of time to answer each question

As time remaining decreases, the background fades from white to black, with the gameplay text turning to white (against a now-dark background) halfway through

High score list w/ user-entered name keeps track of top scores (on local machine only; not tracked globally on network)


Scoring:

+10 points for correct answer; if answered correctly in under 2.5 seconds, + additional (2.5 - time of answer) * 2 points

-10 points for incorrect answer


Files that I wrote:

Java files are in androidApp/mobile/src/main/java/com/mathprog/sgrauerg/mathprog/

Layout files are in androidApp/mobile/src/main/res/layout/

Most of the other stuff is auto-generated