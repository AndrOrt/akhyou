package dulleh.akhyou.Search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.greenrobot.event.EventBus;
import dulleh.akhyou.Models.Anime;
import dulleh.akhyou.R;
import dulleh.akhyou.Utils.FragmentRequestEvent;
import dulleh.akhyou.Utils.OpenAnimeEvent;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<Anime> animes;
    private Context context;
    private float d;

    public SearchAdapter (List<Anime> animes) {
        this.animes = animes;
    }

    public void setAnimes (List<Anime> animes) {
        this.clear();
        this.animes = animes;
        this.notifyDataSetChanged();
    }

    public void addAnime (Anime anime) {
        animes.add(anime);
        this.notifyItemInserted(animes.size());
    }

    public List<Anime> getAnimes () {
        return this.animes;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleView;
        public TextView descView;
        public ImageView imageView;
        public RelativeLayout relativeLayout;

        public ViewHolder(View v) {
            super(v);
            titleView = (TextView) v.findViewById(R.id.title_view);
            descView = (TextView) v.findViewById(R.id.desc_view);
            imageView = (ImageView) v.findViewById(R.id.image_view);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.relativeLayout);
        }
    }

    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        context = parent.getContext();
        d = context.getResources().getDisplayMetrics().density;
        View v = LayoutInflater.from(context)
                .inflate(R.layout.search_card, parent, false);
        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        viewHolder.titleView.setText(animes.get(position).getTitle());
        viewHolder.descView.setText(animes.get(position).getDesc());

        Picasso.with(context)
                .load(animes.get(position).getImageUrl())
                .error(R.drawable.placeholder)
                .resize((int) (224 * d * 0.5), (int) (300 * d * 0.5))
                .centerCrop()
                .into(viewHolder.imageView);

        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Anime anime = animes.get(viewHolder.getAdapterPosition());
                EventBus.getDefault().post(new FragmentRequestEvent("EPI"));
                EventBus.getDefault().postSticky(new OpenAnimeEvent(anime));
            }
        });
    }

    @Override
    public int getItemCount() {
        return animes.size();
    }

    public void clear () {
        this.animes.clear();
        this.notifyDataSetChanged();
    }
}
