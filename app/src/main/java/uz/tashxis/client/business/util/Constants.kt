package uz.tashxis.client.business.util

object Constants {
    val language = "language"
    val LANGUAGE_UZBEK: String = "uzbek"

    const val BASE_URL = "https://tashxis.online/"
    const val REG = 1
    const val LOG = 2


    const val REQUEST_CODE_LOCATION_PERMISSION = 0

    const val SUCCESS: Int = 200
    const val SMS_ALREADY_SENT = 477
    const val UNKNOWN_ERROR = 520
    const val VALIDATION_ERROR = 409
    const val INVALID_UPLOAD_TYPE = 415
    const val DONT_HAVE_PERMISSION = 407
    const val NOT_FOUND = 404
    const val ALREADY_EXISTS = 450
    const val SMS_SEND_FAILED = 460
    const val TOO_MANY_ATTEMPTS = 429
    const val OTP_EXPIRED = 461
    const val INVALID_OTP = 462
    const val FILE_DOES_NOT_EXIST = 463
    const val CLIENT_NOT_FOUND = 4040
    const val CLIENT_ALREADY_EXIST = 4010

    /*gps*/
    const val LOCATION_REQUEST: Int = 1000
    const val GPS_REQUEST = 1001

    //firebase realtime database name
    const val FIRESTORE_TABLE_NAME = "queues"

}