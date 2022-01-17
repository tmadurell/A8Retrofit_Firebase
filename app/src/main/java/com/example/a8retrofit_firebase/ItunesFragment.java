package com.example.a8retrofit_firebase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a8retrofit_firebase.databinding.FragmentItunesBinding;
import com.example.a8retrofit_firebase.databinding.ViewholderContenidoBinding;

import java.util.List;

public class ItunesFragment extends Fragment {
    private FragmentItunesBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentItunesBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        com.example.a8retrofit_firebase.ItunesViewModel itunesViewModel = new ViewModelProvider(this).get(com.example.a8retrofit_firebase.ItunesViewModel.class);

        binding.texto.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) { return false; }

            @Override
            public boolean onQueryTextChange(String s) {
                itunesViewModel.buscar(s);
                return false;
            }
        });


        //Aqui redirecciona el itunesViewModel.buscar per al carregar la app surtin tots els elements agregats al firebase
        itunesViewModel.buscar("");


        ContenidosAdapter contenidosAdapter = new ContenidosAdapter();
        binding.recyclerviewContenidos.setAdapter(contenidosAdapter);

        itunesViewModel.respuestaMutableLiveData.observe(getViewLifecycleOwner(), new Observer<Itunes.Result>() {
            @Override
            public void onChanged(Itunes.Result respuesta) {
                contenidosAdapter.establecerListaContenido(respuesta);
                // respuesta.results.forEach(r -> Log.e("ABCD", r.artistName + ", " + r.trackName + ", " + r.artworkUrl100));
            }
        });



    }

    static class ContenidoViewHolder extends RecyclerView.ViewHolder {
        ViewholderContenidoBinding binding;

        public ContenidoViewHolder(@NonNull ViewholderContenidoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class ContenidosAdapter extends RecyclerView.Adapter<ContenidoViewHolder>{
        Itunes.Result invizimalList;

        @NonNull
        @Override
        public ContenidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ContenidoViewHolder(ViewholderContenidoBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ContenidoViewHolder holder, int position) {
            Itunes.Invizimal invizimal = invizimalList.documents.get(position);

            holder.binding.nombre.setText(invizimal.fields.nombre.stringValue);
            holder.binding.elemento.setText(invizimal.fields.elemento.stringValue);
            holder.binding.fase.setText(invizimal.fields.fase.stringValue);
            holder.binding.generacion.setText(invizimal.fields.generacion.stringValue);
            Glide.with(requireActivity()).load(invizimal.fields.imagen.stringValue).into(holder.binding.artwork);
        }

        @Override
        public int getItemCount() {
            return invizimalList == null ? 0 : invizimalList.documents.size();
        }

        void establecerListaContenido(Itunes.Result pokemonList){
            this.invizimalList = pokemonList;
            notifyDataSetChanged();
        }
    }

}