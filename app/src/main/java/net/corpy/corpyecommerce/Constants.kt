package net.corpy.corpyecommerce

object Constants {

    // splash delay time
    const val SPLASH_DISPLAY_LENGTH = 1000
    const val BASE_URL = "https://high-discounts.net/public/api/"
//    const val BASE_IMAGES_URL = "https://high-discounts.net/upload/products/"
    const val BASE_IMAGES_URL = "https://high-discounts.net/public/upload/products/"


    const val USERS_TABLE_FIREBASE = "Users"
    const val MAINCATEGORIES_TABLE_FIREBASE = "MainCategories"
    const val CATEGORIES_TABLE_FIREBASE = "Category"
    const val PRODUCTS_TABLE_FIREBASE = "Products"
    const val USER_NORMAL = "User"
    const val USER_VENDOR = "Vendor"

    enum class UsersTyps{
        USER_NORMAL,USER_VENDOR
    }

}
