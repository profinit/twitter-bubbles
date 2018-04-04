var openTweetStream = function () {

    var eventSource = new EventSource("/twitter-bubbles/tweets");

    eventSource.onmessage = function (e) {
        // console.log("Processing tweet: ", e.data);

        var tweet = JSON.parse(e.data);

        var fromUserDiv = "<div class='from-user'>" + tweet.fromUser + "</div>";
        var textDiv = "<div class='text'>" + tweet.text + "</div>";
        var tweetDiv = "<div class='tweet'>" + fromUserDiv + textDiv + "</div>";

        $(".tweets").prepend(tweetDiv);
    };

    eventSource.onerror = function (e) {
        console.log("Tweet event source failed.", e);
    };
};

$(document).ready(function () {
    openTweetStream();
});
