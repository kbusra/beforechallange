package com.example.beforechallange

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var moviesList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviesList = findViewById(R.id.movies_list)

        val retrofit: Retrofit=Retrofit.Builder()
               .baseUrl("https//raw.githubusercontent.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        val apiMovies= retrofit.create(ApiMovies::class.java)
        apiMovies.getMovies()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    var movieResponse : MovieResponse = it

                },{

                })
    }

    inner class MoviesAdapter:RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

        private val movies:List<Movie> = mutableListOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapter.MovieViewHolder {
             return MovieViewHolder(layoutInflater.inflate(R.layout.item_movie_layout,parent,false))
        }

        override fun getItemCount(): Int {
             return movies.size
        }

        override fun onBindViewHolder(holder: MoviesAdapter.MovieViewHolder, position: Int) {
             holder.bindModel(movies[position])
        }

        inner class MovieViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView){
            // En son burda kaldım.
             fun bindModel(movies:Movie){

             }
        }
    }
}
