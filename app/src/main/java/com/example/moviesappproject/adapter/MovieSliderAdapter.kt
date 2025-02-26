import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesappproject.R
import com.example.moviesappproject.model.UpcomingResultList

class MovieSliderAdapter(
    private val context: Context,
    private val movies: List<UpcomingResultList>
) : RecyclerView.Adapter<MovieSliderAdapter.MovieViewHolder>() {

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_slider, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        Glide.with(holder.imageView.context)
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .into(holder.imageView)
    }

    override fun getItemCount() = movies.size
}
