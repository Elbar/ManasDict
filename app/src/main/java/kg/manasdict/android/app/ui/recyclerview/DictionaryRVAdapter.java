package kg.manasdict.android.app.ui.recyclerview;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import kg.manasdict.android.R;
import kg.manasdict.android.app.db.model.WordDetails;

/**
 * Created by fukuro on 4/3/16.
 */
public class DictionaryRVAdapter extends RecyclerView.Adapter<DictionaryRVAdapter.ViewHolder> {

    private List<WordDetails> mRecyclerItems;
    private Context mContext;

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_dictionary_rv_item, parent, false);

        return new ViewHolder(view, mContext);
    }

    @Override
    public int getItemCount() {
        return mRecyclerItems.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WordDetails wordDetails = mRecyclerItems.get(position);

        holder.trWord.setText(wordDetails.getKgWord());
        holder.enWord.setText(wordDetails.getEnWord());
        holder.ruWord.setText(wordDetails.getRuWord());
        holder.kgWord.setText(wordDetails.getKgWord());
    }

    public DictionaryRVAdapter(List<WordDetails> recyclerItems, Context context) {
        mRecyclerItems = recyclerItems;
        mContext = context;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public TextView kgWord;
        public TextView ruWord;
        public TextView enWord;
        public TextView trWord;

        public ViewHolder(View rootView, Context context) {
            super(rootView);
            cardView = (CardView) rootView.findViewById(R.id.dictionaryCV);
            kgWord = (TextView) rootView.findViewById(R.id.kgWordTV);
            ruWord = (TextView) rootView.findViewById(R.id.ruWordTV);
            enWord = (TextView) rootView.findViewById(R.id.enWordTV);
            trWord = (TextView) rootView.findViewById(R.id.trWordTV);
        }

    }
}
