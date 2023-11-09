package com.example.recyclerview;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ElementosViewModel extends AndroidViewModel {

    PiratasRepositorio piratasRepositorio;
    MutableLiveData<Pirata> elementoSeleccionado = new MutableLiveData<>();

    MutableLiveData<List<Pirata>> listElementosMutableLiveData = new MutableLiveData<>();

    public ElementosViewModel(@NonNull Application application) {
        super(application);

        piratasRepositorio = new PiratasRepositorio();

        listElementosMutableLiveData.setValue(piratasRepositorio.obtener());
    }

    MutableLiveData<List<Pirata>> obtener(){
        return listElementosMutableLiveData;
    }

    void insertar(Pirata pirata){
        piratasRepositorio.insertar(pirata, new PiratasRepositorio.Callback() {
            @Override
            public void cuandoFinalice(List<Pirata> piratas) {
                listElementosMutableLiveData.setValue(piratas);
            }
        });
    }

    void eliminar(Pirata pirata){
        piratasRepositorio.eliminar(pirata, new PiratasRepositorio.Callback() {
            @Override
            public void cuandoFinalice(List<Pirata> piratas) {
                listElementosMutableLiveData.setValue(piratas);
            }
        });
    }

    void actualizar(Pirata pirata, float valoracion){
        piratasRepositorio.actualizar(pirata, valoracion, new PiratasRepositorio.Callback() {
            @Override
            public void cuandoFinalice(List<Pirata> piratas) {
                listElementosMutableLiveData.setValue(piratas);
            }
        });
    }
    void seleccionar(Pirata pirata){
        elementoSeleccionado.setValue(pirata);
    }

    MutableLiveData<Pirata> seleccionado(){
        return elementoSeleccionado;
    }
}
