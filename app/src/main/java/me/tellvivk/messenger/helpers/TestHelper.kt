package me.tellvivk.messenger.helpers

import me.tellvivk.messenger.app.model.sms.SMS

class TestHelper {

    companion object {

        const val DUMMY_DELAY = 2500L

        val DUMMY_SMS = listOf(
            SMS(),
            SMS(),
            SMS(),
            SMS(),
            SMS(),
            SMS()
        )

        const val TEST_STRING = "TEST_STRING"
        const val TEST_QUERY = "TEST_QUERY"
        const val TEST_PAGE = 1


        fun getDummySmes(): List<SMS>{
            return DUMMY_SMS
        }

    }

}