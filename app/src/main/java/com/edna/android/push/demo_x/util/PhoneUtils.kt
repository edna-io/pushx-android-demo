package com.edna.android.push.demo_x.util

import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

class PhoneUtils {
    companion object {
        const val PHONE_SLOTS = "+7 ___ ___-__-__"
        const val COUNTRY_CODE_ALT = "8"
        const val COUNTRY_CODE = "+7"

        fun getRuPhoneFormatWatcher() = MaskFormatWatcher(
            MaskImpl.createTerminated(UnderscoreDigitSlotsParser().parseSlots(PHONE_SLOTS))
        )
    }
}