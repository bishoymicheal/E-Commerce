package net.corpy.corpyecommerce.network

import BaseResponse
import android.util.Log
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.corpy.corpyecommerce.App
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.Utils
import net.corpy.corpyecommerce.model.*
import net.corpy.corpyecommerce.network.requestModel.*

object EcommerceRepo {

    fun login(email: String, password: String, lang: String, onComplete: (Boolean, String) -> Unit) {
        RestClient().getApiService().loginUser(email, password, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                App.instance.userResponse = t

                                App.cartCount = t.data?.cartCount ?: 0
                                App.cartUpTodate = true
                                Utils.updateCartTabNumber(App.cartCount)

                                onComplete(true, t.messages?.success?.get(0)
                                        ?: App.instance.getString(R.string.login_successfully))
                            }
                            false -> {
                                val emailSize = t.messages?.email?.size ?: -1

                                if (emailSize > 0) {
                                    val emailerror = t.messages?.email?.get(0) ?: App.instance.getString(R.string.email_not_correct)
                                    onComplete(false, emailerror)
                                }

                                val passwordSize = t.messages?.password?.size ?: -1
                                if (passwordSize > 0) {
                                    val passworderror = t.messages?.password?.get(0)
                                            ?: App.instance.getString(R.string.password_not_correct)
                                    onComplete(false, passworderror)
                                }
                                val error = t.messages?.error?.size ?: -1
                                if (error > 0) {
                                    val errorS = t.messages?.error?.get(0) ?: App.instance.getString(R.string.password_not_correct)
                                    onComplete(false, errorS)
                                }
                            }
                            else -> {
                                onComplete(false, "خطأ الاتصال بالخادم")
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, "خطأ الاتصال بالخادم")
                    }

                })
    }

    fun socialLogin(socialRequest: SocialRequest, onComplete: (Boolean, String) -> Unit) {
        RestClient().getApiService().socialLogin(socialRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {

                        when (t.status) {
                            true -> {
                                App.instance.userResponse = t

                                App.cartCount = t.data?.cartCount ?: 0
                                App.cartUpTodate = true
                                Utils.updateCartTabNumber(App.cartCount)

                                onComplete(true, t.messages?.success?.get(0)
                                        ?:App.instance.getString(R.string.login_successfully) )
                            }
                            false -> {
                                Utils.fbSignOut()
                                val nameSize = t.messages?.username?.size ?: -1
                                if (nameSize > 0) {
                                    val nameerror = t.messages?.username?.get(0)
                                            ?: App.instance.getString(R.string.user_not_correct)
                                    onComplete(false, nameerror)
                                }

                                val emailSize = t.messages?.email?.size ?: -1
                                if (emailSize > 0) {
                                    val emailerror = t.messages?.email?.get(0) ?: App.instance.getString(R.string.email_not_correct)
                                    onComplete(false, emailerror)
                                }

                                val passwordSize = t.messages?.provider?.size ?: -1
                                if (passwordSize > 0) {
                                    val passworderror = t.messages?.provider?.get(0)
                                            ?: App.instance.getString(R.string.password_not_correct)
                                    onComplete(false, passworderror)
                                }
                            }
                            else -> {
                                Utils.fbSignOut()
                                onComplete(false, "خطأ الاتصال بالخادم")
                            }
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, "خطأ الاتصال بالخادم")
                    }

                })

    }

    fun signUp(registerRequest: RegisterRequest, onComplete: (Boolean, String) -> Unit) {
        RestClient().getApiService().register(registerRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.messages?.success?.get(0)
                                        ?: App.instance.getString(R.string.account_created))
                            }
                            false -> {
                                val nameSize = t.messages?.name?.size ?: -1
                                if (nameSize > 0) {
                                    val nameerror = t.messages?.name?.get(0) ?:  App.instance.getString(R.string.user_not_correct)
                                    onComplete(false, nameerror)
                                }

                                val emailSize = t.messages?.email?.size ?: -1
                                if (emailSize > 0) {
                                    val emailerror = t.messages?.email?.get(0) ?: App.instance.getString(R.string.email_not_correct)
                                    onComplete(false, emailerror)
                                }

                                val passwordSize = t.messages?.password?.size ?: -1
                                if (passwordSize > 0) {
                                    val passworderror = t.messages?.password?.get(0)
                                            ?: App.instance.getString(R.string.password_not_correct)
                                    onComplete(false, passworderror)
                                }
                            }
                            else -> {
                                onComplete(false, "خطأ الاتصال بالخادم")
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.d("loading", "started")
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, "خطأ الاتصال بالخادم")
                    }

                })
    }

    fun logoutUser(id: Int, onComplete: (Boolean, String) -> Unit) {
        RestClient().getApiService().logout(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, App.instance.getString(R.string.looged_out))
                            }
                            else -> {
                                onComplete(false, "خطأ الاتصال بالخادم")
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, "خطأ الاتصال بالخادم")
                    }

                })
    }

    fun getParentCategories(onComplete: (Boolean, List<ParentCategories>) -> Unit) {
        RestClient().getApiService().getParentCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.data?.ParentCategories ?: ArrayList())
                            }
                            else -> {
                                onComplete(false, ArrayList())
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, ArrayList())
                    }

                })
    }

    fun loadProductCategory(id: String, onComplete: (Boolean, ParentCategories?) -> Unit) {
        RestClient().getApiService().loadProductCategory(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.data?.product_categories)
                            }
                            else -> {
                                onComplete(false, null)
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, null)
                    }

                })
    }

    fun getSubCategories(id: String,
                         arParentName: String,
                         enParentName: String,
                         onComplete: (Boolean, List<SubCategories>) -> Unit) {
        RestClient().getApiService().getSubCategories(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                t.data?.subCategories?.forEach {
                                    it.ar_parentName = arParentName
                                    it.en_parentName = enParentName
                                }
                                onComplete(true, t.data?.subCategories ?: ArrayList())
                            }
                            else -> {
                                onComplete(false, ArrayList())
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, ArrayList())
                    }

                })
    }

    fun getBrand(onComplete: (Boolean, List<SubCategories>) -> Unit) {
        RestClient().getApiService().getBrands()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.data?.subCategories ?: ArrayList())
                            }
                            else -> {
                                onComplete(false, ArrayList())
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, ArrayList())
                    }

                })
    }

    fun products(id: String,
                 arParentTitle: String,
                 enParentTitle: String,
                 onComplete: (Boolean, List<Product>) -> Unit) {
        RestClient().getApiService().getProducts(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                t.data?.products?.forEach {
                                    it.arParentTitle = arParentTitle
                                    it.enParentTitle = enParentTitle
                                }
                                onComplete(true, t.data?.products ?: ArrayList())
                            }
                            else -> {
                                onComplete(false, ArrayList())
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, ArrayList())
                    }

                })
    }

    fun loadSingleProduct(id: String, onComplete: (Boolean, Data?) -> Unit) {
        RestClient().getApiService().getSingleProducts(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.data)
                            }
                            else -> {
                                onComplete(false, null)
                            }
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, null)
                    }

                })
    }

    fun bestSeller(onComplete: (Boolean, List<Product>) -> Unit) {
        RestClient().getApiService().getBestSeller()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.data?.products ?: ArrayList())
                            }
                            else -> {
                                onComplete(false, ArrayList())
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, ArrayList())
                    }

                })
    }

    fun topRated(onComplete: (Boolean, List<Product>) -> Unit) {
        RestClient().getApiService().getTopRated()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.data?.products ?: ArrayList())
                            }
                            else -> {
                                onComplete(false, ArrayList())
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, ArrayList())
                    }

                })
    }

    fun mostLiked(onComplete: (Boolean, List<Product>) -> Unit) {
        RestClient().getApiService().getMostLiked()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.data?.products ?: ArrayList())
                            }
                            else -> {
                                onComplete(false, ArrayList())
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, ArrayList())
                    }

                })
    }

    fun recommendedProduct(onComplete: (Boolean, List<Product>) -> Unit) {
        RestClient().getApiService().getRecommendedProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.data?.products ?: ArrayList())
                            }
                            else -> {
                                onComplete(false, ArrayList())
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, ArrayList())
                    }

                })
    }

    fun featuredProduct(onComplete: (Boolean, List<Product>) -> Unit) {
        RestClient().getApiService().getFeaturedProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.data?.products ?: ArrayList())
                            }
                            else -> {
                                onComplete(false, ArrayList())
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, ArrayList())
                    }

                })
    }

    fun onSale(onComplete: (Boolean, List<Product>) -> Unit) {
        RestClient().getApiService().getOnSale()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.data?.products ?: ArrayList())
                            }
                            else -> {
                                onComplete(false, ArrayList())
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, ArrayList())
                    }

                })
    }

    fun latestProduct(onComplete: (Boolean, List<Product>) -> Unit) {
        RestClient().getApiService().getLatestProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.data?.products ?: ArrayList())
                            }
                            else -> {
                                onComplete(false, ArrayList())
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, ArrayList())
                    }

                })
    }

    fun getSlider(onComplete: (Boolean, List<Slider>) -> Unit) {
        RestClient().getApiService().getSlider()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.data?.slider ?: ArrayList())
                            }
                            else -> {
                                onComplete(false, ArrayList())
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, ArrayList())
                    }

                })
    }

    fun updateUser(id: String, updateUserRequest: UpdateUserRequest, onComplete: (Boolean, String) -> Unit) {
        RestClient().getApiService().updateUser(id, updateUserRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.messages?.success?.get(0)
                                        ?: App.instance.getString(R.string.login_successfully))
                            }
                            false -> {
                                val nameSize = t.messages?.error?.size ?: -1
                                if (nameSize > 0) {
                                    val nameerror = t.messages?.error?.get(0)
                                    onComplete(false, nameerror ?: App.instance.getString(R.string.error_happend))
                                }
                                onComplete(false, App.instance.getString(R.string.error_happend))

                            }
                            else -> {
                                onComplete(false, "خطأ الاتصال بالخادم")
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.d("loading", "started")
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, "خطأ الاتصال بالخادم")
                    }

                })
    }

    fun loadCart(id: String, onComplete: (Boolean, Data?) -> Unit) {
        RestClient().getApiService().getCart(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.data)
                                App.cartCount = t.data?.cartCount ?: 0
                                App.cartUpTodate = true
                                Utils.updateCartTabNumber(App.cartCount)
                            }
                            else -> {
                                App.cartUpTodate = false
                                onComplete(false, null)
                            }
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, null)
                    }

                })
    }
    fun submitError(errorSubmitRequest: ErrorSubmitRequest, onComplete: (Boolean, String) -> Unit) {
        RestClient().getApiService().submitError(errorSubmitRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.messages?.success?.get(0)
                                        ?: "Item Added successfully ")                            }
                            else -> {
                                onComplete(false, "")
                            }
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, "")
                    }

                })
    }

    fun addItemToCart(addToCartRequest: AddToCartRequest, onComplete: (Boolean, String) -> Unit) {
        RestClient().getApiService().addItemToCart(addToCartRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.messages?.success?.get(0)
                                        ?: App.instance.getString(R.string.item_added))
                                App.cartCount = t.data?.cartCount ?: 0
                                App.cartUpTodate = true
                                App.cartCountChanged = true
                                Utils.updateCartTabNumber(App.cartCount)
                            }
                            false -> {
                                App.cartUpTodate = false
                                onComplete(false, App.instance.getString(R.string.error_adding))
                            }
                            else -> {
                                App.cartUpTodate = false
                                onComplete(false, "خطأ الاتصال بالخادم")
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.d("loading", "started")
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, "خطأ الاتصال بالخادم")
                    }

                })
    }

    fun removeItemFromCart(id: String, onComplete: (Boolean, String) -> Unit) {
        RestClient().getApiService().destroyitemcart(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.messages?.success?.get(0)
                                        ?: App.instance.getString(R.string.item_added))
                                App.cartCount = t.data?.cartCount ?: 0
                                App.cartUpTodate = true
                                App.cartCountChanged = true
                                Utils.updateCartTabNumber(App.cartCount)
                            }
                            false -> {
                                App.cartUpTodate = false
                                onComplete(false, App.instance.getString(R.string.error_deleting))
                            }
                            else -> {
                                App.cartUpTodate = false
                                onComplete(false, "خطأ الاتصال بالخادم")
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.d("loading", "started")
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, "خطأ الاتصال بالخادم")
                    }

                })
    }

    fun loadWishList(id: String, onComplete: (Boolean, ArrayList<Wishlist>?) -> Unit) {
        RestClient().getApiService().getWishlist(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.data?.wishlist)
                                App.savedUpTodate = true
                            }
                            else -> {
                                onComplete(false, null)
                            }
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, null)
                    }

                })
    }

    fun addItemToWishList(addToCartRequest: AddToCartRequest, onComplete: (Boolean, String) -> Unit) {
        RestClient().getApiService().addItemTowishlist(addToCartRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.messages?.success?.get(0)
                                        ?: App.instance.getString(R.string.item_added))
                                App.wishlistCountChanged = true
                                App.savedUpTodate = false
                            }
                            false -> {
                                onComplete(false, App.instance.getString(R.string.error_adding))
                            }
                            else -> {
                                onComplete(false, "خطأ الاتصال بالخادم")
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.d("loading", "started")
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, "خطأ الاتصال بالخادم")
                    }

                })
    }

    fun removeItemFromWishList(id: String, onComplete: (Boolean, String) -> Unit) {
        RestClient().getApiService().destroyitemwishlist(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.messages?.success?.get(0)
                                        ?: App.instance.getString(R.string.item_added))
                                App.wishlistCountChanged = true
                                App.savedUpTodate = false
                            }
                            false -> {
                                onComplete(false, App.instance.getString(R.string.error_deleting))
                            }
                            else -> {
                                onComplete(false, "خطأ الاتصال بالخادم")
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.d("loading", "started")
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, "خطأ الاتصال بالخادم")
                    }

                })
    }

    fun placeOrder(placeOrderRequest: PlaceOrderRequest, onComplete: (Boolean, String) -> Unit) {
        RestClient().getApiService().placeOrder(placeOrderRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.messages?.success?.get(0)
                                        ?: App.instance.getString(R.string.order_placed_successfully))
                            }
                            false -> {

                                val msg = t.messages?.error?.get(0) ?: ""
                                onComplete(false, App.instance.getString(R.string.error_palcing_order)+"\n" +
                                        " $msg")
                            }
                            else -> {
                                onComplete(false, "خطأ الاتصال بالخادم")
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.d("loading", "started")
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, "خطأ الاتصال بالخادم")
                    }

                })
    }

    fun searchProducts(nameSearch: String, onComplete: (Boolean, List<Product>) -> Unit) {
        RestClient().getApiService().search(nameSearch)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.data?.products ?: ArrayList())
                            }
                            else -> {
                                onComplete(false, ArrayList())
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, ArrayList())
                    }

                })
    }

    fun getCities(onComplete: (Boolean, List<Cities>) -> Unit) {
        RestClient().getApiService().getCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.data?.cities ?: ArrayList())
                            }
                            else -> {
                                onComplete(false, ArrayList())
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, ArrayList())
                    }

                })
    }

    fun loadOrders(id: String, onComplete: (Boolean, List<Order>) -> Unit) {
        RestClient().getApiService().getOrders(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.data?.order ?: ArrayList())
                            }
                            else -> {
                                onComplete(false, ArrayList())
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, ArrayList())
                    }

                })
    }

    fun loadOrderItems(id: String, onComplete: (Boolean, List<OrdersItem>) -> Unit) {
        RestClient().getApiService().getOrderItem(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<BaseResponse.BaseResponse> {
                    override fun onSuccess(t: BaseResponse.BaseResponse) {
                        when (t.status) {
                            true -> {
                                onComplete(true, t.data?.orders_item ?: ArrayList())
                            }
                            else -> {
                                onComplete(false, ArrayList())
                            }
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        onComplete(false, ArrayList())
                    }

                })
    }
}

