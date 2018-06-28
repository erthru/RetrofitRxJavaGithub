package erthru.retrofitrxjavagithub

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import erthru.retrofitrxjavagithub.adapter.RecyclerViewAdapterRepo
import erthru.retrofitrxjavagithub.network.ApiHandler
import erthru.retrofitrxjavagithub.network.model.UserResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(this)
        supportActionBar?.title = "RxJava2 Example"

        abl.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener{
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {

                if(Math.abs(verticalOffset) - appBarLayout?.totalScrollRange!! == 0){
                    supportActionBar?.title = "User detail"
                }else{
                    supportActionBar?.title = ""
                }

            }
        })

        btnSearch.setOnClickListener {

            loadUser(txUsername.text.toString())

        }

    }

    private fun loadUser(username:String?){

        btnSearch.visibility = View.GONE
        viewLoading.visibility = View.VISIBLE

        ApiHandler().instance(ApiHandler.GITHUB_BASE_URL)
                .user(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<UserResponse>> {

                    override fun onComplete() {

                        viewDataLoaded.visibility = View.VISIBLE
                        viewSearch.visibility = View.GONE
                        supportActionBar?.title = ""
                        supportActionBar?.elevation = 0f
                        supportActionBar?.setDisplayHomeAsUpEnabled(true)

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: List<UserResponse>) {

                        val adapter = RecyclerViewAdapterRepo(this@MainActivity,t)
                        adapter.notifyDataSetChanged()
                        rv.adapter = adapter
                        Glide.with(this@MainActivity).load(t.get(0).owner.avatar_url).into(imgUser)
                        lbUsername.text = t.get(0).owner.login

                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(applicationContext,e.message,Toast.LENGTH_SHORT).show()
                        btnSearch.visibility = View.VISIBLE
                        viewLoading.visibility = View.GONE
                    }

                })

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            android.R.id.home -> recreate()
        }

        return super.onOptionsItemSelected(item)
    }

}
