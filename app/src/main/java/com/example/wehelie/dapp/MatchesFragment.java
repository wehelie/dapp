package com.example.wehelie.dapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.location.Location;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import static android.app.PendingIntent.getActivities;
import static android.app.PendingIntent.getActivity;
import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;


public class MatchesFragment extends Fragment {

    Button btnShowLocation;
    GPSTracker gpsTracker;

    private MatchesFragment tabLayout;
    double Latitude;
    double Longitude;
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
    public double startLat, startLong;
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

    public void setLatLong(Double latitude, Double longitude){
        startLat = latitude;
        startLong = longitude;
        notifyDataSetChanged();
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
        String TAG = "Inside the Viewholder";
        holder.mMatches = mValues.get(position);
        Picasso.get().load(mValues.get(position).imageUrl).into(holder.mImageView);
        holder.mTitleView.setText(mValues.get(position).name);
        holder.fave = mValues.get(position).liked;
        holder.lat = mValues.get(position).lat;

        // from phone
        double phoneLat = Double.parseDouble(String.valueOf(holder.Latitude));
        double phoneLong = Double.parseDouble(String.valueOf(holder.Longitude));

        // from firebase
        double fireBaseLat =  Double.parseDouble(holder.mMatches.lat);
        double fireBaseLong =  Double.parseDouble(holder.mMatches.longitude);

        float[] distanceMatch = new float[1];

        Location.distanceBetween(startLat, startLong,
                Double.parseDouble(holder.mMatches.lat), Double.parseDouble(holder.mMatches.longitude),
                distanceMatch);
        holder.matchDistance.setText(
                holder.matchDistance.getContext()
                        .getString(R.string.distancemsg,
                                String.format("%.01f", distanceMatch[0] / 1609.34)));
        //Log.d(TAG, "LAT FROM MAIN" + MatchesFragment)

        Log.d(TAG, " LAT -----> FROM PHONE " + holder.Longitude);




        if (!holder.mMatches.liked) {
           // holder.favoriteButton.setColorFilter(Color.RED);
            holder.favoriteButton.setColorFilter(Color.LTGRAY);
        } else {
            holder.favoriteButton.setColorFilter(Color.RED);
        }

        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    MatchesObject index =  mValues.get(position);
                    Toasty.info(holder.mView.getContext(), "You liked " + mValues.get(position).name, Toast.LENGTH_LONG).show();
                    //holder.favoriteButton.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
                    mListener.onListFragmentInteraction(holder.mMatches);
                }


            }
        });

        holder.mView.setOnClickListener(view -> {
            if (null != mListener) {
                mListener.onListFragmentInteraction(holder.mMatches);

            }

//                mValues.get(position).liked = true;

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
        public boolean fave;
        public double Latitude;
        public double Longitude;
        public TextView matchDistance;

        public ImageButton favoriteButton;
        public final ImageView mImageView;
        public final TextView mTitleView;
        public MatchesObject mMatches;
        public String lat;
        public MatchesObject lng;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = view.findViewById(R.id.card_image);
            mTitleView = view.findViewById(R.id.card_title);
            favoriteButton = view.findViewById(R.id.favorite_button);
            matchDistance = view.findViewById(R.id.matchDistance);

            ImageButton favoriteImageButton = itemView.findViewById(R.id.favorite_button);
            favoriteImageButton.setOnClickListener(view1 -> {
                TextView text;
                Toast toast = null;

                if (null != mListener) {
                    mListener.onListFragmentInteraction(mMatches);
                }

                //Toasty.info(view1.getContext(), "You liked " + mTitleView.getText(), Toast.LENGTH_LONG).show();
            });

            GPSTracker gpsTracker;

            gpsTracker = new GPSTracker(view.getContext());

            ActivityCompat.requestPermissions((Activity) view.getContext(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            if(gpsTracker.canGetLocation()) {

                 Latitude = gpsTracker.getLatitude();
                 Longitude = gpsTracker.getLongitude();

                // \n is for new line
                Toast.makeText(view.getContext(), "Your Location is - \nLat: " + Latitude + "\nLong: " + Longitude, Toast.LENGTH_LONG).show();

            } else {
                // Can't get location.
                // GPS or network is not enabled.
                // Ask user to enable GPS/network in settings.
                gpsTracker.showSettingsAlert();
            }
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