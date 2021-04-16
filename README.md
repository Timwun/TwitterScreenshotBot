# TwitterScreenshotBot

[![Build and Release](https://github.com/Timwun/TwitterScreenshotBot/actions/workflows/build-and-release.yml/badge.svg)](https://github.com/Timwun/TwitterScreenshotBot/actions/workflows/build-and-release.yml)

## Prerequisites

### Chromedriver
Download the latest Chromedriver for your OS from [Electron](https://github.com/electron/electron/releases/) or from the [Official Page](https://chromedriver.chromium.org/)
and place the executable in the folder with the [Jar file](https://github.com/Timwun/TwitterScreenshotBot/releases).

## Download
Download the latest Jar file from [GitHub Releases](https://github.com/Timwun/TwitterScreenshotBot/releases).

## Starting the bot
You need a [Twitter Developer Account](https://developer.twitter.com/en) with an application created.<br>
You need a file called `.TwitterScreenshotBotCredentials` with your Twitter API keys split up with a `;` in one line.<br>
You also need a file called `.TwitterScreenshotBotUserList` with the Twitter User handles (Without the @) split up with a `;` in one line.

Start the bot with `java -jar TwitterScreenshotBot.jar`
