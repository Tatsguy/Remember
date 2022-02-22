package com.myers.saveme;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Notas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Notas extends Fragment implements SearchView.OnQueryTextListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<ListElement> elements;
    SearchView searchView;
    ListAdapter listAdapter;

    public Notas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Notas.
     */
    // TODO: Rename and change types and number of parameters
    public static Notas newInstance(String param1, String param2) {
        Notas fragment = new Notas();
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

    public void init(ViewGroup viewGroup){
        elements = new ArrayList<>();
        elements.add(new ListElement("#775447","Mexico","IDK","12 de Enero"));
        elements.add(new ListElement("#775447","Colombia","IDK","12 de Enero"));
        elements.add(new ListElement("#775447","Uruguay","IDK","12 de Enero"));
        elements.add(new ListElement("#775447","Argentina","IDK","12 de Enero"));
        elements.add(new ListElement("#775447","Mexico","IDK","12 de Enero"));
        elements.add(new ListElement("#775447","Colombia","IDK","12 de Enero"));
        elements.add(new ListElement("#775447","Uruguay","IDK","12 de Enero"));
        elements.add(new ListElement("#775447","Argentina","IDK","12 de Enero"));


        searchView = viewGroup.findViewById(R.id.txtBuscar);
        listAdapter = new ListAdapter(elements,getContext());
        RecyclerView recyclerView = viewGroup.findViewById(R.id.notesRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);

        searchView.setOnQueryTextListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_notas,null);
        init(root);
        return root;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listAdapter.filtrado(newText);
        return false;
    }
}