//package com.ddr.ui.home;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.ddr.R;
//
//import java.util.ArrayList;
//import java.util.Objects;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link SearchPage#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class SearchPage extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//    ArrayList<PlaneModel> flights = new ArrayList<>();
//    int planeImage = R.drawable.baseline_airplanemode_active_24;
//
//
//    public SearchPage() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment SearchPage.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static SearchPage newInstance(String param1, String param2) {
//        SearchPage fragment = new SearchPage();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//
//
//
//
//    }

//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View rootView = inflater.inflate(R.layout.fragment_search_page, container, false);
//        RecyclerView recyclerView = rootView.findViewById(R.id.searchRecyclerView);
//        setUpPlanes();
//        RecycleViewAdapter adapter = new RecycleViewAdapter(this.getContext(), flights);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
//        return rootView;
//    }
//
//}