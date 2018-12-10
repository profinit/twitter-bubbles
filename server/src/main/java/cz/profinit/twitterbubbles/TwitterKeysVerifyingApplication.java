package cz.profinit.twitterbubbles;

import org.springframework.social.twitter.api.AccountSettings;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

class TwitterKeysVerifyingApplication {

    private static final TwitterKeys[] TWITTER_KEYS = {
            new TwitterKeys(
                    "Profinit",
                    "mYV8n1IOnchjQ1HePUrSAKjWn",
                    "x9EMfbmJJRUMSEvVY7F9Vi4SalkxOGrGFgNB5AvjfnO3ER3s0v",
                    "130928324-RTo9wECv1ZCgz0Lmmf8njdEPcWmqjYjaxFqJo22V",
                    "vicIhfgQ5gZY2PFoZv23mas1Kxrdhysg1phCpb7tGEpWo"
            ),
            new TwitterKeys(
                    "Student 01",
                    "oXgMYrMQiNLSrApPRe8BN8NJH",
                    "eG2lGH2BskcunHRa1QlmdXmgrDmNR2rRdGF7Ye8e3q3aeuux6M",
                    "130928324-fPYwnwvWx0eaIDRFYX6NHC2k0r5srCs0ZbP4AEtu",
                    "shu3TvYoLM6QfmLofciX2tjzjbCvsSzSq40IB7wrUXuQs"
            ),
            new TwitterKeys(
                    "Student 02",
                    "00j3d8dVvLTIyDAgeDJEfjq6o",
                    "vZXFyonG2c5F4rabhun4CSKG5ggGAlH6yFDriwMpBhLnWVWZkY",
                    "130928324-NmwXQMAZlNFKXm3aThhFXaa2bQy6ykRIJ5UW8X8y",
                    "aOrpmhP5at2P0vO1bwcP5RiAeZnlsWqMXYjv6Tn2X7khk"
            ),
            new TwitterKeys(
                    "Student 03",
                    "Cm57w4ORtnnVRVeZf7NPmUF6i",
                    "3M5sXlDIuYzt5NARWJYWrJc2PUQCeK8XRcClJlRMLdH7I6Es4J",
                    "130928324-BkQpEViAAEEEsbriG9HXkhwQoSuQVIvRlPdpTGA4",
                    "RZ5noOf12M4Q8rhuw1wbOJGWrDLXYa7jnVchczBH3HnhJ"
            ),
            new TwitterKeys(
                    "Student 04",
                    "bO81DtzGak0n0p2iCwZMK5cKz",
                    "9OV4lWZCtnde8yMoqefk43bR8SwEmDQs5FX5vi0VpocwCRcYOh",
                    "130928324-2vvok1Ytoa6quej2lgdKtZH6weeZ3WJCb7rvDT3w",
                    "gw84wYZD1yuUP9RxX1FBUW1cSznvY3ZCOzBtlheMWnyq5"
            ),
            new TwitterKeys(
                    "Student 05",
                    "heIzTvftU5Pt4nUPDReiYYv0h",
                    "IOPM5F400qTfQuFO8jdNXZOTXJSwztEOhUrtdMHx4p8WH7IrnH",
                    "88727033-rVDBJz9XY8nviqEwDEhTJM1rH2SmLc9Q4a4V3aAL9",
                    "xFw5w7fTdsI4hx4YQ5uLxncHrLycMRZt2qrPd4RxCruzA"
            ),
            new TwitterKeys(
                    "Student 06",
                    "8S98ZfPW7VqVjPJdqF7r5kmfb",
                    "cN5E35mT4vNYRAcFkAuPI0iAW5Hiwy53ra3ezOpXCbUNnbHlkT",
                    "88727033-M3wNACBa6XXqkDyaXbqoNNQtsROFZ9zLejMib1oew",
                    "EONZPq0izYJ4FpYXWJ8yvV0wPvmswAYeWd3yrWkj2vnmT"
            ),
            new TwitterKeys(
                    "Student 07",
                    "xprOZaH1p9af5TFvNDp0aCOGk",
                    "ows4QYWybKvVawodsWKK7kvNKVA90h4XAobKSOGlSoPj3zoyQt",
                    "88727033-GoiOlVCZypMCkq9CAklqZ7GaKnYTnbWuwyp2gtNu4",
                    "KtoEBZ2lmswRmOsJHuqiYFCQKq2NQGhZSWlMLHVL8z3M7"
            ),
            new TwitterKeys(
                    "Student 08",
                    "LyCWAoUNnPE921mkFXn6DEr6U",
                    "tCqoiRC4DRB5msJUIdNEHrJEVz1NpRK3xVybG0LlHJEHmDQOhn",
                    "984358024780419072-QxmGBqDDPgdlgXyb5ae4PwXzNEIquUb",
                    "2wJ5BlMQ2TCmwfvwZEJPtJBZlisGb8J8SinJiKWBikiXk"
            ),
            new TwitterKeys(
                    "Student 09",
                    "6mq5jIkCbZUR7PUk1oZBlTt9M",
                    "aPIKcdVs4tYMPpbXjgQQRjBySYNn7uEOAeCflUAjwy59wfCfa6",
                    "984358024780419072-LJtT0jD8D9yy5L8fHAPT8MHXRJ0OeUe",
                    "IpTtS9SukEvMxdq5XnPUxSkp5FZ1rDlN5yLMyL4QZAdIJ"
            ),
            new TwitterKeys(
                    "Student 10",
                    "IRiUd3Ne4XjHEbRUpQ4mn0N5w",
                    "MBvueTTSBqdlHwCHjzy34lKD85EDjrcQao9YUlQHNWKs2yKVha",
                    "984358024780419072-prfjxzM0n34FILBw7mCHawWKoDryyXg",
                    "f1X4FQ1OfYYLv4iTmLAa8TFjsqWqsMa8ORZ8BoptsUhfa"
            ),
            new TwitterKeys(
                    "Student 11",
                    "Qhl2n8s5e7MNHiku8rx1yJFSc",
                    "wMHhqNM2wOyLSmYfmhIYb1V2tGrsvbd9Y0AR73XBhHucOjeLS0",
                    "130928324-4hNhJxuf3UpC25RUfxmcpNODjzy6lJFmocAT3Zj7",
                    "pW4uC253gPG0cmeVhDg18CDQwYRB1C9gKH7zeXn4kR6Db"
            ),
            new TwitterKeys(
                    "Student 12",
                    "dqrb9zr8PbwTOIEu4hrJgih3W",
                    "P9uV9CqF1zpZHUI5uIdGyMNL8RiMrsVu2s1Ydi3wCUPQmu2CBN",
                    "130928324-st2jwgJlkR5DKs2JhqDjGRQOlta3VShvXiZXlA6R",
                    "JSQuQSoa0FQ4BISftwfsAK8Wvj5cDVSaEIPYrMPib8RHV"
            ),
            new TwitterKeys(
                    "Student 13",
                    "fUUMg5IM10Dif0jUI9JWkqehy",
                    "pzIVDHT5Gc5Qv6pJNYb6brLFkFuTbLBbz6IQPus554BRborq5f",
                    "130928324-6tKwLrfXVv4pnU0ACP0hoNycJa5lLLlzDVAvPtL0",
                    "zMuapUgRBerRiGoUxNpMfeX1asbtTMTqwW458k8kaMPYn"
            ),
            new TwitterKeys(
                    "Student 14",
                    "6YudzCeJfcd2eFUxmqIg8NJkI",
                    "CwO3jyHL0MjG0ybrfPNns4jdHYNip2vMH6MxHe6NsNSxdFltRw",
                    "130928324-1JXo4e01WSrQWyJMg4UZ0j3dlPwtYd4n1zs9N0Sa",
                    "QS4YhBkiOT9NsCB009Zr9kvt8qGRQrIQNrpb6gvRcJfdG"
            ),
            new TwitterKeys(
                    "Student 15",
                    "GgoELMhDVrsEKlfKpQvOLyatr",
                    "bQ6v4Jo8CPxOagFUMoFFIcsswJvhIlHZVunJlqivCNTTKcAXTs",
                    "130928324-VBPUOBbSOtXCWmdKhgwHVBcP0FjLdJUDjiswtUYw",
                    "kwSwV5HdjZB42Y3CVd8v2PoD9PBc9vzEiB5QRb0rpP0tX"
            )
    };

    public static void main(String[] args) {
        new TwitterKeysVerifyingApplication().verifyKeys();
    }

    private void verifyKeys() {
        for (TwitterKeys keys : TWITTER_KEYS) {
            verifyKeys(keys);
        }
    }

    private void verifyKeys(TwitterKeys keys) {
        System.out.println("*** Verifying " + keys.profileName);

        TwitterTemplate twitterTemplate = new TwitterTemplate(keys.appId, keys.appSecret, keys.token, keys.tokenSecret);

        AccountSettings accountSettings = twitterTemplate.userOperations().getAccountSettings();

        System.out.println("Screen name: " + accountSettings.getScreenName());
        System.out.println("Language: " + accountSettings.getLanguage());
    }

    private static class TwitterKeys {

        private final String profileName;
        private final String appId;
        private final String appSecret;
        private final String token;
        private final String tokenSecret;

        private TwitterKeys(String profileName, String appId, String appSecret, String token, String tokenSecret) {
            this.profileName = profileName;
            this.appId = appId;
            this.appSecret = appSecret;
            this.token = token;
            this.tokenSecret = tokenSecret;
        }
    }
}
