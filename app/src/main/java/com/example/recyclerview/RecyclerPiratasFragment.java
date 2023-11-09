package com.example.recyclerview;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.example.recyclerview.databinding.FragmentRecyclerElementosBinding;
import com.example.recyclerview.databinding.ViewholderElementoBinding;

import java.util.List;

public class RecyclerPiratasFragment extends Fragment {

    private FragmentRecyclerElementosBinding binding;
    private ElementosViewModel elementosViewModel;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentRecyclerElementosBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        elementosViewModel = new ViewModelProvider(requireActivity()).get(ElementosViewModel.class);
        navController = Navigation.findNavController(view);

        ElementosAdapter elementosAdapter = new ElementosAdapter();
        binding.recyclerView.setAdapter(elementosAdapter);
        elementosViewModel.obtener().observe(getViewLifecycleOwner(), new Observer<List<Pirata>>() {
            @Override
            public void onChanged(List<Pirata> piratas) {
                elementosAdapter.establecerLista(piratas);
            }
        });



        binding.irANuevoElemento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_recyclerElementosFragment_to_nuevoElementoFragment);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT  | ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int posicion = viewHolder.getAdapterPosition();
                Pirata pirata = elementosAdapter.obtenerElemento(posicion);
                elementosViewModel.eliminar(pirata);

            }
        }).attachToRecyclerView(binding.recyclerView);
    }
    class ElementoViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderElementoBinding binding;

        public ElementoViewHolder(ViewholderElementoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    class ElementosAdapter extends RecyclerView.Adapter<ElementoViewHolder> {

        List<Pirata> piratas;

        @NonNull
        @Override
        public ElementoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ElementoViewHolder(ViewholderElementoBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ElementoViewHolder holder, int position) {

            Pirata pirata = piratas.get(position);

            holder.binding.nombre.setText(pirata.nombre);
            holder.binding.valoracion.setRating(pirata.peligrosidad);
            holder.binding.valoracion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if(fromUser) {
                        elementosViewModel.actualizar(pirata, rating);
                    }
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    elementosViewModel.seleccionar(pirata);
                    navController.navigate(R.id.action_recyclerElementosFragment_to_mostrarElementoFragment);
                }
            });

        }

        @Override
        public int getItemCount() {
            return piratas != null ? piratas.size() : 0;
        }

        public void establecerLista(List<Pirata> piratas){
            this.piratas = piratas;
            notifyDataSetChanged();
        }
        public Pirata obtenerElemento(int posicion){
            return piratas.get(posicion);
        }
    }
}