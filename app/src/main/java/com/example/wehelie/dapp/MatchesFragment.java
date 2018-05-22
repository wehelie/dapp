package com.example.wehelie.dapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.ImageButton;
import android.support.design.widget.Snackbar;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class MatchesFragment extends Fragment {
    private List<MatchesObject> mDataSet;
    private OnListFragmentInteractionListener mListener;

    private FirebaseMatchViewModel viewModel;
    private RecyclerViewAdapter matchesRecyclerViewAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);

        viewModel = new FirebaseMatchViewModel();

        matchesRecyclerViewAdapter = new RecyclerViewAdapter(mDataSet, mListener);

        recyclerView.setAdapter(matchesRecyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        viewModel.findMatches(
                (ArrayList<MatchesObject> matchesList) -> {
                    matchesRecyclerViewAdapter.updateMatchListItems(matchesList);
                }
        );

        return recyclerView;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mDataSet = getArguments().getParcelableArrayList(Constants.OKAY200);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(MatchesObject matches);
    }
}


class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<MatchesObject> mValues;
    private final MatchesFragment.OnListFragmentInteractionListener mListener;

    public RecyclerViewAdapter(List<MatchesObject> matches, MatchesFragment.OnListFragmentInteractionListener listener) {
        mValues = matches;
        mListener = listener;
    }



    public void updateMatchListItems(List<MatchesObject> matches) {
        if (mValues == null) {
            mValues = new ArrayList<>();
        }

        final MediaItemDiffCallback diffCallback = new MediaItemDiffCallback(mValues, matches);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        mValues.clear();
        mValues.addAll(matches);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_matches, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mMatches = mValues.get(position);
        Picasso.get().load(mValues.get(position).imageUrl).into(holder.mImageView);
        holder.mTitleView.setText(mValues.get(position).name);

        holder.mView.setOnClickListener(view -> {
            if (null != mListener) {
                mListener.onListFragmentInteraction(holder.mMatches);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mValues != null) {
            return mValues.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public final ImageView mImageView;
        public final TextView mTitleView;
        public MatchesObject mMatches;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = view.findViewById(R.id.card_image);
            mTitleView = view.findViewById(R.id.card_title);

            ImageButton favoriteImageButton = itemView.findViewById(R.id.favorite_button);
            favoriteImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView text;
                    Toast toast = null;

                    if (null != mListener) {
                        mListener.onListFragmentInteraction(mMatches);
                    }

                    Toasty.info(view.getContext(), "You liked " + mTitleView.getText(), Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }


}


/**
 * A Callback class used by DiffUtil while calculating the diff between two lists
 */
class MediaItemDiffCallback extends DiffUtil.Callback  {
    private final List<MatchesObject> oldList;
    private final List<MatchesObject> newList;


    /**
     * @return
     */
    @Override
    public int getNewListSize() {
        return newList.size();
    }


    /**
     *
     * @param firstMatchList
     * @param lastMatchList
     */
    public MediaItemDiffCallback(List<MatchesObject> firstMatchList, List<MatchesObject> lastMatchList) {
        this.oldList = firstMatchList;
        this.newList = lastMatchList;
    }

    /**
     *
     * @return
     * Returns the size of the old list.
     */
    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    /**
     *
     * @param oldItemPosition
     * @param newItemPosition
     * @return
     */
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).uid == newList.get(
                newItemPosition).uid;
    }

    /**
     *
     * @param oldItemPosition
     * @param newItemPosition
     * @return
     */
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final MatchesObject firstMatches = oldList.get(oldItemPosition);
        final MatchesObject lastMatches = newList.get(newItemPosition);

        return firstMatches.name.equals(lastMatches.name);
    }
}