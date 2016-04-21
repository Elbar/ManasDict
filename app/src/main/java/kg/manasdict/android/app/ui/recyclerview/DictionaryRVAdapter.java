package kg.manasdict.android.app.ui.recyclerview;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import kg.manasdict.android.R;
import kg.manasdict.android.app.db.HelperFactory;
import kg.manasdict.android.app.db.dao.WordDetailsDao;
import kg.manasdict.android.app.db.model.WordDetails;
import kg.manasdict.android.app.ui.fragment.dialog.EditWordDialogFragment;

/**
 * Created by fukuro on 4/3/16.
 */
public class DictionaryRVAdapter extends RecyclerView.Adapter<DictionaryRVAdapter.ViewHolder> {

    private List<WordDetails> mRecyclerItems;
    private Context mContext;
    private FragmentManager mManager;
    private EditWordDialogFragment mEditWordDialog;
    private WordDetailsDao mWordDetailsDao;

    public DictionaryRVAdapter(List<WordDetails> recyclerItems, Context context, FragmentManager manager) {
        mRecyclerItems = recyclerItems;
        mContext = context;
        mManager = manager;
        mEditWordDialog = new EditWordDialogFragment();

        try {
            mWordDetailsDao = HelperFactory.getHelper().getWordDetailsDao();
        } catch (SQLException e) {
            Log.e(DictionaryRVAdapter.class.getName(), e.getMessage());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.view_dictionary_rv_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mRecyclerItems.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        WordDetails wordDetails = mRecyclerItems.get(position);

        holder.kgWordTV.setText(wordDetails.getKgWord());
        holder.enWordTV.setText(wordDetails.getEnWord());
        holder.ruWordTV.setText(wordDetails.getRuWord());
        holder.trWordTV.setText(wordDetails.getTrWord());
        holder.popupActionsIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view, holder);
            }
        });
    }

    protected void showPopup(View v, final ViewHolder holder) {
        PopupMenu popupMenu = new PopupMenu(mContext, v);
        popupMenu.inflate(R.menu.dictionary_cv_popup);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.popupActionsEdit:
                        try {
                            mEditWordDialog.setWordDetails(mWordDetailsDao.findByKgWord(holder.kgWordTV.getText().toString()));
                        } catch (SQLException e) {
                            Log.e(ViewHolder.class.getName(), e.getMessage());
                        }
                        mEditWordDialog.show(mManager, "EDIT_WORD_DIALOG");
                        break;
                    case R.id.popupActionsDelete:
                        try {
                            mWordDetailsDao.delete(mWordDetailsDao.findByKgWord(holder.kgWordTV.getText().toString()));
                            animateDeleteAction(holder.cardView);

                            Toast.makeText(mContext, "Слово успешно удалено!", Toast.LENGTH_LONG).show();
                        } catch (SQLException e) {
                            Log.e(ViewHolder.class.getName(), e.getMessage());
                        }
                        break;
                }

                return false;
            }
        });

        popupMenu.show();
    }

    protected void animateDeleteAction(final View view) {
        Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public ImageView popupActionsIV;
        public TextView kgWordTV;
        public TextView ruWordTV;
        public TextView enWordTV;
        public TextView trWordTV;

        public ViewHolder(View rootView) {
            super(rootView);
            cardView = (CardView) rootView.findViewById(R.id.dictionaryCV);
            popupActionsIV = (ImageView) rootView.findViewById(R.id.actionsPopupIV);
            kgWordTV = (TextView) rootView.findViewById(R.id.kgWordTV);
            ruWordTV = (TextView) rootView.findViewById(R.id.ruWordTV);
            enWordTV = (TextView) rootView.findViewById(R.id.enWordTV);
            trWordTV = (TextView) rootView.findViewById(R.id.trWordTV);
        }
    }
}
