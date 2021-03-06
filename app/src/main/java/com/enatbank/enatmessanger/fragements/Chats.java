package com.enatbank.enatmessanger.fragements;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enatbank.enatmessanger.R;
import com.enatbank.enatmessanger.adapters.ChatRecyclerViewAdapter;
import com.enatbank.enatmessanger.models.Chat;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Chats.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Chats#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Chats extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView mRecyclerView;
    private ChatRecyclerViewAdapter mAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Chats() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Chats.
     */
    // TODO: Rename and change types and number of parameters
    public static Chats newInstance(String param1, String param2) {
        Chats fragment = new Chats();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.chat_recyclerview);
        mAdapter = new ChatRecyclerViewAdapter(populate(), getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setAdapter(mAdapter);
    }

    public List<Chat> populate() {
        List<Chat> chats = new ArrayList<>();
        int[] avatar = {R.drawable.avater, R.drawable.avater, R.drawable.avater, R.drawable.avater, R.drawable.avater};
        String[] username = {"Birhane", "Abel", "Aman", "Qidus","Birhane","Birhane", "Birhane"};
        String[] chatDate = {"2017-02-15", "2017-02-15", "2017-02-15", "2017-02-15","2017-02-15","2017-02-15", "2017-02-15"};
        String[] chatMessage = {"Lorem ipsum dolor sit amet, consectetur adipisicing elit,",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit,",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit,",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit,",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit,","Lorem ipsum dolor sit amet, consectetur adipisicing elit,",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit,"};
        for (int i = 0; i < username.length && i < avatar.length; i++) {
            chats.add(new Chat(avatar[i], username[i], chatMessage[i], chatDate[i]));
        }

        return chats;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
