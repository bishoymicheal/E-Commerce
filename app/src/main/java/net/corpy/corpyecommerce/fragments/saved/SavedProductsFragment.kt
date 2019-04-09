package net.corpy.corpyecommerce.fragments.saved

import android.app.Activity
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.hazem.mvpbase.BaseFragment
import kotlinx.android.synthetic.main.fragment_saved.*
import net.corpy.corpyecommerce.App
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.Utils
import net.corpy.corpyecommerce.adapters.adapter.WishListListAdapter
import net.corpy.corpyecommerce.interfaces.ItemClickListener
import net.corpy.corpyecommerce.model.Wishlist
import net.corpy.corpyecommerce.network.EcommerceRepo
import net.corpy.corpyecommerce.network.requestModel.AddToCartRequest

class SavedProductsFragment : BaseFragment<SavedFragmentPresenter>(), SavedFragmentView {
    override fun onWishListLoaded(data: ArrayList<Wishlist>?) {
        saved_swipe.isRefreshing = false

        App.wishlistCountChanged = false
        no_item_layout?.visibility = View.GONE
        wishListAdapter?.submitList(data ?: ArrayList())
//        wishListAdapter?.notifyDataSetChanged()

        if (data?.isEmpty() == true) {
            no_item_layout?.visibility = View.VISIBLE
        }



        if (data == null) {
            error_loading_data.visibility = View.VISIBLE
        } else {
            error_loading_data.visibility = View.GONE
        }

    }

    override fun onLoadingFailed(msg: String) {

    }

    companion object {
        // TODO: Rename and change types and number of parameters
        fun newInstance(): SavedProductsFragment {
            return SavedProductsFragment()
        }
    }

    override fun setResourceLayout(): Int {
        return R.layout.fragment_saved
    }

    override fun initPresenter(): SavedFragmentPresenter {
        return SavedFragmentPresenter(this)
    }

    override fun initViews() {


        saved_swipe.setOnRefreshListener {
            id = App.instance.userResponse?.data?.user?.id?.toString().toString()
            presenter.loadWishList(id)
        }


        loadWishListData()

        error_loading_data.setOnClickListener {
            id = App.instance.userResponse?.data?.user?.id?.toString().toString()
            presenter.loadWishList(id)
        }

    }

    var id = "0"

    var wishListAdapter: WishListListAdapter? = null
    private fun loadWishListData() {

        id = App.instance.userResponse?.data?.user?.id?.toString().toString()

        wishListAdapter = WishListListAdapter(object : ItemClickListener {
            override fun addToCart(addToCartRequest: AddToCartRequest) {
                addToCartRequest.lang = App.getCurrentLanguageKey(getCurrentContext())
                showMessage(getString(R.string.add_to_cart))
                EcommerceRepo.addItemToCart(addToCartRequest) { status, msg ->
                    if (status) {
                        Utils.loadCartCount()
                        Utils.showSuccessDialog(getCurrentContext()
                                , getCurrentContext()
                                .getString(R.string.add_to_cart)
                                , msg, true) {}
                    } else {
                        Utils.showErrorDialog(getCurrentContext()
                                , getCurrentContext()
                                .getString(R.string.oops)
                                , msg, cancellable = true) {}
                    }
                }
            }

            override fun onClick(position: Int) {
                val item = wishListAdapter?.getItemAt(position)
                if (item != null) {
                    showMessage(getString(R.string.deleting_product))
                    EcommerceRepo.removeItemFromWishList(item.id.toString()) { status, msg ->
                        if (status) {
                            presenter.loadWishList(id)
                            Utils.showSuccessDialog(getCurrentContext()
                                    , getCurrentContext()
                                    .getString(R.string.item_removed)
                                    , msg, true) {}
                        } else {
                            Utils.showErrorDialog(getCurrentContext()
                                    , getCurrentContext()
                                    .getString(R.string.oops)
                                    , msg, cancellable = true) {}
                        }
                    }
                }
            }
        })
        wish_list_recycler?.setHasFixedSize(true)
        wish_list_recycler?.layoutManager = GridLayoutManager(getCurrentContext(), 2)
        wish_list_recycler?.adapter = wishListAdapter
    }

    override fun onResume() {
        super.onResume()
        presenter.loadWishList(id)
    }

    override fun onLoading(msg: String) {
        progress_bar?.visibility = View.VISIBLE
        no_item_layout?.visibility = View.GONE

    }

    override fun finishLoad() {
        progress_bar?.visibility = View.GONE

    }

    override fun showMessage(msg: String) {
        Snackbar.make(rootView!!, msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun getCurrentContext(): Context {
        return context!!
    }

    override fun getCurrentActivity(): Activity {
        return activity!!
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isResumed) {
            Utils.loadCartCount()
            if (!App.savedUpTodate) {
                id = App.instance.userResponse?.data?.user?.id?.toString().toString()
                presenter.loadWishList(id)
            }

            if (App.wishlistCountChanged) {
                id = App.instance.userResponse?.data?.user?.id?.toString().toString()
                presenter.loadWishList(id)
            }
            if (wishListAdapter?.itemCount == 0) {
                id = App.instance.userResponse?.data?.user?.id?.toString().toString()
                presenter.loadWishList(id)
            }
        }
    }
}