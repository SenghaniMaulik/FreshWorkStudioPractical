package com.demo.freshworkstudiopractical.utils


class Constant {
    companion object {
        const val BASE_URL = "https://api.giphy.com/v1/gifs/"

        const val API_KEY = "AObpB2eMlMqSp4h5t41SsWnni4cmcQut" //"tqGSaO7f41ElmhFSwswbmQbXxOUoaQW3"
        const val PAGE_LIMIT = "25"



        // ROOM Database
        const val DATABASE_NAME = "giphy_db"
        const val FAVORITE_TABLE = "favorite_table"
    }

    interface API_RESPONSE_STATUS {
        companion object {
            const val OK = "200"
        }
    }
}